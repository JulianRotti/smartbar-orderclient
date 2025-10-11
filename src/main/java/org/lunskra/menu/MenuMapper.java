package org.lunskra.menu;

import org.lunskra.smartbar.backoffice.model.ArticleApi;
import org.lunskra.smartbar.backoffice.model.MenuApi;
import org.lunskra.smartbar.backoffice.model.MenuItemApi;
import org.lunskra.smartbar.orderclient.model.Article;
import org.lunskra.smartbar.orderclient.model.Menu;
import org.lunskra.smartbar.orderclient.model.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR)
public interface MenuMapper {

    @Mapping(target = "removeArticlesItem", ignore = true)
    MenuItem toOrderClient(MenuItemApi s);

    @Mapping(target = "removeArticlesItem", ignore = true)
    MenuItemApi toBackoffice(MenuItem s);

    Article toOrderClient(ArticleApi s);

    @Mapping(target = "categoryId", ignore = true)
    ArticleApi toBackoffice(Article s);

    @Mapping(target = "removeItemsItem", ignore = true)
    Menu toOrderClient(MenuApi s);

    @Mapping(target = "removeItemsItem", ignore = true)
    MenuApi toBackoffice(Menu s);
}
