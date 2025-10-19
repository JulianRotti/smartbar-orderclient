package org.lunskra.orders;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.lunskra.logins.LoginsService;
import org.lunskra.smartbar.orderclient.api.OrdersApi;
import org.lunskra.smartbar.orderclient.model.OrderItem;
import org.lunskra.smartbar.orderclient.model.OrderStatus;
import org.lunskra.smartbar.orderclient.model.UpdateOrderRequest;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrdersApiImpl implements OrdersApi {

    private final OrdersService ordersService;
    private final LoginsService loginsService;

    @Inject
    public OrdersApiImpl(OrdersService ordersService, LoginsService loginsService) {
        this.ordersService = ordersService;
        this.loginsService = loginsService;
    }

    @Override
    public CompletionStage<Response> getAllOrders(OrderStatus status) {
        return Uni.createFrom()
                .item(ordersService.getAllOrders())
                .map(orders -> Response.ok(orders).build())
                .subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Response> getAllOrdersForLogin(UUID loginToken) {
        return Uni.createFrom()
                .item(ordersService.getAllOrders())
                .map(orders -> Response.ok(orders).build())
                .subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Response> getOrder(Long orderId) {
        return null;
    }

    @Override
    public CompletionStage<Response> getOrderForLogin(UUID loginToken, Long orderId) {
        return null;
    }

    @Override
    public CompletionStage<Response> postOrderForLogin(UUID loginToken, List<OrderItem> orderItem) {
        return this.loginsService
                .findLoginByToken(loginToken)
                .chain(login -> {
                    if (login == null) return getTokenNotValid();
                    return ordersService
                            .createOrderForLogin(login, orderItem)
                            .map(orderId -> Response.created(URI.create("/orders/" + orderId.toString())).build());
                })
                .subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Response> updateOrder(Long orderId, UpdateOrderRequest updateOrderRequest) {
        return null;
    }

    private Uni<Response> getTokenNotValid() {
        return Uni.createFrom().item(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
