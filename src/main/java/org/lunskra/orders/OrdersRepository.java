package org.lunskra.orders;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class OrdersRepository implements ReactivePanacheMongoRepository<OrderEntity> {
    public Uni<OrderEntity> findByLoginToken(UUID loginToken) {
        return find("token", loginToken.toString()).firstResult();
    }
}
