package org.lunskra.orders;

import jakarta.enterprise.context.ApplicationScoped;
import org.lunskra.smartbar.orderclient.model.Order;
import org.lunskra.smartbar.orderclient.model.OrderItem;
import org.lunskra.smartbar.orderclient.model.OrderStatus;

import java.util.List;

@ApplicationScoped
public class OrdersService {
    public List<Order> getAllOrders() {
        return List.of(
                new Order(
                        "Table1",
                        OrderStatus.READY,
                        List.of(new OrderItem(1L, 10))
                )
        );
    }
}
