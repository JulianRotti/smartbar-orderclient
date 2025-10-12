package org.lunskra.menu;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.lunskra.smartbar.backoffice.model.MenuItemApi;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletionStage;

@Path("/menu")
@RegisterRestClient
public interface MenuApiClient {

    @GET
    @Retry
    @Timeout(1000)
    @Fallback(fallbackMethod = "getFallbackMenu")
    @CircuitBreaker(delay = 1, delayUnit = ChronoUnit.MINUTES)
    @CacheResult(cacheName = "menu-cache")
    CompletionStage<List<MenuItemApi>> getMenu();

    default CompletionStage<List<MenuItemApi>> getFallbackMenu() {
        MenuItemApi fallbackMenuItem = new MenuItemApi();
        fallbackMenuItem.setCategory("Fallback Category");
        return Uni.createFrom().item(List.of(fallbackMenuItem)).subscribeAsCompletionStage();
    }


}
