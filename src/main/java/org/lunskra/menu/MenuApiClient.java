package org.lunskra.menu;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.lunskra.smartbar.backoffice.model.MenuItemApi;

import java.util.List;

@Path("/menu")
@RegisterRestClient
public interface MenuApiClient {

    @GET
    List<MenuItemApi> getMenu();
}
