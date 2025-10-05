package org.lunskra.orders;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.lunskra.smartbar.orderclient.api.OrdersApi;
import org.lunskra.smartbar.orderclient.model.Order;
import org.lunskra.smartbar.orderclient.model.OrderItem;
import org.lunskra.smartbar.orderclient.model.OrderStatus;
import org.lunskra.smartbar.orderclient.model.UpdateOrderRequest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

public class OrdersApiImpl implements OrdersApi {

    private final OrdersService ordersService;

    @Inject
    public OrdersApiImpl(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Override
    public Response getAllOrders(OrderStatus status) {
        return Response.ok(ordersService.getAllOrders()).build();
    }

    @Override
    public Response getAllOrdersForLogin(UUID loginToken) {
        return Response.ok().build();
    }

    @Override
    public Response getOrder(Long orderId) {
        return Response.ok().build();
    }

    @Override
    public Response getOrderForLogin(UUID loginToken, Long orderId) {
        return Response.ok().build();
    }

    @Override
    public Response postOrderForLogin(UUID loginToken, List<OrderItem> orderItem) {
        return Response.created(URI.create("http://done.it")).build();
    }

    @Override
    public Response updateOrder(Long orderId, UpdateOrderRequest updateOrderRequest) {
        return Response.created(URI.create("http://done.it")).build();
    }
}
