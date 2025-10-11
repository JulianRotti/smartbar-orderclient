package org.lunskra.menu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
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
        final var request = new HttpGet(apiUrl);
        try(
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(request);
        ) {
            return mapper.readValue(response.getEntity().getContent(), new TypeReference<List<MenuItemApi>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
