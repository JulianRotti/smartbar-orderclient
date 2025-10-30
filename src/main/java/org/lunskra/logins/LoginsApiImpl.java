package org.lunskra.logins;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.lunskra.menu.MenuApiClient;
import org.lunskra.menu.MenuMapper;
import org.lunskra.smartbar.orderclient.api.LoginsApi;
import org.lunskra.smartbar.orderclient.model.LoginResponse;

import java.util.concurrent.CompletionStage;

public class LoginsApiImpl implements LoginsApi {

    private final MenuApiClient menuApiClient;

    private final MenuMapper menuMapper;

    private final LoginsService loginsService;

    @Inject
    public LoginsApiImpl(@RestClient MenuApiClient menuApiClient, MenuMapper menuMapper, LoginsService loginsService) {
        this.menuApiClient = menuApiClient;
        this.menuMapper = menuMapper;
        this.loginsService = loginsService;
    }

    @Override
    public CompletionStage<Response> postLoginFromTable(Long tableId) {
        final var chain = Uni.createFrom()
                .completionStage(
                        menuApiClient.getMenu()
                        .thenApply(menuMapper::toOrderClient)
                        .thenApply(items -> {
                            LoginResponse loginResponse = new LoginResponse();
                            loginResponse.setMenu(items);
                            return loginResponse;
                        })
                )
                .flatMap(
                        loginResponse -> {
                            return this.loginsService.createNewLogin(tableId)
                                    .onItem()
                                    .invoke(loginResponse::setLoginToken)
                                    .replaceWith(Response.ok(loginResponse).build());
                        }
                );

        return this.loginsService
                .hasLogin(tableId)
                .chain(hasLogin -> hasLogin ? getLoginAlreadyExists() : chain)
                .subscribeAsCompletionStage();
    }

    private Uni<Response> getLoginAlreadyExists() {
        return Uni.createFrom().item(Response.status(Response.Status.CONFLICT).build());
    }
}



