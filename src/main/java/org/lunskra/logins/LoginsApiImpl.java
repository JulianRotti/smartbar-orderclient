package org.lunskra.logins;

import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.lunskra.menu.MenuApiClient;
import org.lunskra.menu.MenuMapper;
import org.lunskra.smartbar.orderclient.api.LoginsApi;
import org.lunskra.smartbar.orderclient.model.LoginResponse;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public class LoginsApiImpl implements LoginsApi {

    private final MenuApiClient menuApiClient;

    private final MenuMapper menuMapper;

    @Inject
    public LoginsApiImpl(@RestClient MenuApiClient menuApiClient, MenuMapper menuMapper) {
        this.menuApiClient = menuApiClient;
        this.menuMapper = menuMapper;
    }

    @Override
    public CompletionStage<LoginResponse> postLoginFromTable(Long tableId) {
        return menuApiClient.getMenu()
                .thenApply(menuMapper::toOrderClient)
                .thenApply(items -> {
                    LoginResponse loginResponse = new LoginResponse();
                    loginResponse.setLoginToken(UUID.randomUUID());
                    loginResponse.setMenu(items);
                    return loginResponse;
                });
    }
}
