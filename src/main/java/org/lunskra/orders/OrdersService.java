package org.lunskra.orders;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.lunskra.logins.mongopanache.Logins;
import org.lunskra.logins.mongopanache.LoginsRepository;
import org.lunskra.smartbar.orderclient.model.Order;
import org.lunskra.smartbar.orderclient.model.OrderItem;
import org.lunskra.smartbar.orderclient.model.OrderStatus;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrdersService {

    private final OrdersRepository ordersRepository;

    @Inject
    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<Order> getAllOrders() {
        return List.of(
                new Order(OrderStatus.PENDING, List.of(new OrderItem(1L, 2)))
        );
    }

    public Uni<ObjectId> createOrderForLogin(Logins login, List<OrderItem> orderItems) {
        Date placedAt = Date.from(Instant.now());
        OrderEntity order = new OrderEntity();
        order.setOrderedArticles(orderItems);
        order.setLoginToken(UUID.fromString(login.getToken()));
        order.setStatus(OrderStatus.PENDING);
        order.setPlacedAt(placedAt);
        order.setTableId(login.getTableId());

        return this.ordersRepository.persist(order).map(OrderEntity::getId);
    }
}
