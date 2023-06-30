package com.example.order;
import com.example.Client;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    static final String ORDER_API = "/api/v1/orders/";

    /** Создание заказа **/
    public ValidatableResponse createOrder(OrderCreate order) {

        return given()
                .spec(spec())
                .body(order)
                .when()
                .post(ORDER_API)
                .then().log().all();
    }

    /** Список заказов **/
    public ValidatableResponse orderList() {

        return given()
                .spec(spec())
                .when()
                .get(ORDER_API)
                .then().log().all();
    }

    /** Отмена заказа **/
    public ValidatableResponse deleteOrder(int id) {

        return given()
                .spec(spec())
                .when()
                .put(ORDER_API + "cancel?track=" + id)
                .then().log().all();
    }
}
