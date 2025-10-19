package org.lunskra.orders;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.lunskra.smartbar.orderclient.model.OrderItem;
import org.lunskra.smartbar.orderclient.model.OrderStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@MongoEntity(database = "orders", collection = "orders-collection")
public class OrderEntity {

    private ObjectId id;
    private Date placedAt;
    private Long tableId;
    private UUID loginToken;
    private OrderStatus status;
    private @Valid List<@Valid OrderItem> orderedArticles;

    public OrderEntity(ObjectId id, Date placedAt, Long tableId, UUID loginToken, OrderStatus status, List<@Valid OrderItem> orderedArticles) {
        this.id = id;
        this.placedAt = placedAt;
        this.tableId = tableId;
        this.loginToken = loginToken;
        this.status = status;
        this.orderedArticles = orderedArticles;
    }

    public OrderEntity() {}

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public UUID getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(UUID loginToken) {
        this.loginToken = loginToken;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getOrderedArticles() {
        return orderedArticles;
    }

    public void setOrderedArticles(List<OrderItem> orderedArticles) {
        this.orderedArticles = orderedArticles;
    }
}
