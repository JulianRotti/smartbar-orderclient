package org.lunskra.menu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.lunskra.smartbar.backoffice.model.MenuItemApi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
        try {
            final var request = HttpRequest.newBuilder(new URI(apiUrl)).GET().build();
            final var httpClient = HttpClient.newBuilder().build();
            final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), new TypeReference<List<MenuItemApi>>() {});
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
