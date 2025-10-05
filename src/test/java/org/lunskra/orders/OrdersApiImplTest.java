package org.lunskra.orders;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class OrdersApiImplTest {

    @Test
    void shouldGetAllArticles() {
        final Response response = given()
            .when().get("/orders")
            .then()
            .statusCode(200)
            .extract().response();

        final JsonPath jsonPath = response.jsonPath();

        Assertions.assertEquals("Table1", jsonPath.getString("[0].tableName"));
    }
}

