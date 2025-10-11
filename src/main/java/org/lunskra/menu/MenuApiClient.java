package org.lunskra.menu;

import org.lunskra.smartbar.backoffice.model.MenuItemApi;

import java.util.List;

public interface MenuApiClient {
    List<MenuItemApi> getMenu();
}
