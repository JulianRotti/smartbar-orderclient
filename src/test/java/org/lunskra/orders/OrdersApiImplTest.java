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

import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class OrdersApiImplTest {

    @InjectMock
    OrdersService ordersServiceMock;

    @BeforeEach
    void setUp() {
        Mockito.when(ordersServiceMock.getAllOrders())
            .thenReturn(List.of(
                new Order(
                        "MockTable",
                        OrderStatus.ACCEPTED,
                        List.of(new OrderItem(1L, 5))
                )
            ));
    }

    @Test
    void shouldGetTableNameFromOrder() {
        final Response response = given()
            .when().get("/orders")
            .then()
            .statusCode(200)
            .extract().response();

        final JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals("MockTable", jsonPath.getString("[0].tableName"));
    }
}

