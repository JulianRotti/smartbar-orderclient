package org.lunskra.menu;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.lunskra.smartbar.backoffice.model.ArticleApi;
import org.lunskra.smartbar.backoffice.model.MenuApi;
import org.lunskra.smartbar.backoffice.model.MenuItemApi;

import java.util.List;

@ApplicationScoped
public class MenuApiClientImpl implements MenuApiClient {

    private final String apiUrl;

    @Inject
    public MenuApiClientImpl(@ConfigProperty(name = "backoffice.menuapi.url") String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public MenuApi getMenu() {
        MenuApi menuApi = new MenuApi();
        MenuItemApi menuItemApi = new MenuItemApi();
        menuItemApi.addArticlesItem(new ArticleApi(apiUrl, 1.99F));
        menuApi.addItemsItem(menuItemApi);
        return menuApi;
    }
}
