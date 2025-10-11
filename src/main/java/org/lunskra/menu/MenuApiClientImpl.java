package org.lunskra.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.lunskra.smartbar.backoffice.model.MenuItemApi;

import java.util.List;

@ApplicationScoped
public class MenuApiClientImpl implements MenuApiClient {

    private final String apiUrl;

    private final ObjectMapper mapper;

    @Inject
    public MenuApiClientImpl(@ConfigProperty(name = "backoffice.menuapi.url") String apiUrl, ObjectMapper mapper) {
        this.apiUrl = apiUrl;
        this.mapper = mapper;
    }

    @Override
    public List<MenuItemApi> getMenu() {
        try (var client = ClientBuilder.newClient()) {
            return client.target(apiUrl).request().get(new GenericType<List<MenuItemApi>>() {});
        }
    }
}
