package com.example.courier;

import com.example.Client;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    static final String COURIER_API = "/api/v1/courier/";

    /** Создаем курьера **/
    public ValidatableResponse create(CourierCreate courier) {

        return given()
                .spec(spec())
                .body(courier)
                .when()
                .post(COURIER_API)
                .then().log().all();
    }

    /**  Логинимся курьером **/
    public ValidatableResponse login(CourierLogin login) {
        return given()
                .spec(spec())
                .body(login)
                .when()
                .post(COURIER_API + "login")
                .then().log().all();
    }

     /** Удаляем курьера **/
    public ValidatableResponse delete(int id) {
        return given()
                .spec(spec())
                .when()
                .delete(COURIER_API + id)
                .then().log().all();
    }
}
