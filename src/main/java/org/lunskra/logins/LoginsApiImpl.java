package org.lunskra.logins;

import io.smallrye.common.annotation.NonBlocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.lunskra.menu.MenuApiClient;
import org.lunskra.menu.MenuMapper;
import org.lunskra.smartbar.orderclient.api.LoginsApi;
import org.lunskra.smartbar.orderclient.model.MenuItem;

import java.util.List;

public class LoginsApiImpl implements LoginsApi {

    private final MenuApiClient menuApiClient;

    private final MenuMapper menuMapper;

    @Inject
    public LoginsApiImpl(MenuApiClient menuApiClient, MenuMapper menuMapper) {
        this.menuApiClient = menuApiClient;
        this.menuMapper = menuMapper;
    }

    @Override
    public Response postLoginFromTable(Long tableId) {
        List<MenuItem> menu = menuMapper.toOrderClient(menuApiClient.getMenu());
        return Response.ok(menu).build();
    }
}
