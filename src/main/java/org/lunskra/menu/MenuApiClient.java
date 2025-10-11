package org.lunskra.menu;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.lunskra.smartbar.backoffice.model.MenuItemApi;

import java.util.List;
import java.util.concurrent.CompletionStage;

@Path("/menu")
@RegisterRestClient
public interface MenuApiClient {

    @GET
    CompletionStage<List<MenuItemApi>> getMenu();
}
