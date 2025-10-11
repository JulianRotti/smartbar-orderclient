package org.lunskra.menu;

import org.lunskra.smartbar.backoffice.model.ArticleApi;
import org.lunskra.smartbar.backoffice.model.MenuItemApi;
import org.lunskra.smartbar.orderclient.model.Article;
import org.lunskra.smartbar.orderclient.model.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR)
public interface MenuMapper {

    @Mapping(target = "removeArticlesItem", ignore = true)
    MenuItem toOrderClient(MenuItemApi s);

    @Mapping(target = "removeArticlesItem", ignore = true)
    MenuItemApi toBackoffice(MenuItem s);

    Article toOrderClient(ArticleApi s);

    @Mapping(target = "categoryId", ignore = true)
    ArticleApi toBackoffice(Article s);

    List<MenuItem> toOrderClient(List<MenuItemApi> menu);

    List<MenuItemApi> toBackoffice(List<MenuItem> menu);
}
