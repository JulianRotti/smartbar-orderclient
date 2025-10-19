package org.lunskra.orders;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lunskra.smartbar.orderclient.model.Order;
import org.lunskra.smartbar.orderclient.model.OrderItem;
import org.lunskra.smartbar.orderclient.model.OrderStatus;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class OrdersApiImplTest {

    @InjectMock
    OrdersService ordersServiceMock;

    @BeforeEach
    void setUp() {
        Order order = new Order();
        order.setOrderedArticles(List.of(new OrderItem(1L, 2)));
        order.setLoginToken(UUID.randomUUID());
        order.setStatus(OrderStatus.PENDING);
        order.setTableId(1000L);
        order.setPlacedAt(Date.from(Instant.now()));
        Mockito.when(ordersServiceMock.getAllOrders())
            .thenReturn(List.of(order));
    }

    @Test
    void shouldGetTableNameFromOrder() {
        final Response response = given()
            .when().get("/orders")
            .then()
            .statusCode(200)
            .extract().response();

        final JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals(1000L, jsonPath.getLong("[0].tableId"));
    }
}

