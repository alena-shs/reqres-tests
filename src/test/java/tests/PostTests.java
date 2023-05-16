package tests;

import models.*;
import org.junit.jupiter.api.Test;
import specs.Specs;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostTests {
    @Test
    void successfulCreateTest() {
        CreateBody body = new CreateBody();
        body.setName("morpheus");
        body.setJob("master");

        ResponseUserInfo response = step("Make a request", () ->
                given()
                        .filter(withCustomTemplates())
                        .spec(Specs.requestSpec)
                        .body(body)
                        .when()
                        .post("/users")
                        .then()
                        .spec(Specs.responseCreateUser)
                        .extract().as(ResponseUserInfo.class));
        step("Verify name in the response", () ->
                assertThat(response.getName()).isEqualTo(body.getName()));
        step("Verify job in the response", () ->
                assertThat(response.getJob()).isEqualTo(body.getJob()));
    }

    @Test
    void successfulRegisterTest() {
        Integer expectedId = 4;
        String expectedToken = "QpwL5tke4Pnpja7X4";

        CredentialsBody body = new CredentialsBody();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");


        ResponseSuccessfulRegistration response = step("Make a request", () ->
                given()
                        .filter(withCustomTemplates())
                        .spec(Specs.requestSpec)
                        .body(body)
                        .when()
                        .post("/register")
                        .then()
                        .spec(Specs.successResponseSpec)
                        .extract().as(ResponseSuccessfulRegistration.class));
        step("Verify ID in the response", () ->
                assertThat(response.getId()).isEqualTo(expectedId));
        step("Verify token in the response", () ->
                assertThat(response.getToken()).isEqualTo(expectedToken));
    }

    @Test
    void unsuccessfulRegisterTest() {
        String expectedResponse = "Missing password";

        PartialCredentialsBody body = new PartialCredentialsBody();
        body.setEmail("sydney@fife");

        ResponseBadRequest response = step("Make a request", () ->
                given()
                        .filter(withCustomTemplates())
                        .spec(Specs.requestSpec)
                        .body(body)
                        .when()
                        .post("/register")
                        .then()
                        .spec(Specs.responseError)
                        .extract().as(ResponseBadRequest.class));
        step("Verify error in the response", () ->
                assertThat(response.getError()).isEqualTo(expectedResponse));
    }

    @Test
    void successfulLoginTest() {
        CredentialsBody body = new CredentialsBody();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("123");

        ResponseSuccessfuLogin response = step("Make a request", () ->
                given()
                        .filter(withCustomTemplates())
                        .spec(Specs.requestSpec)
                        .body(body)
                        .when()
                        .post("/login")
                        .then()
                        .spec(Specs.successResponseSpec)
                        .extract().as(ResponseSuccessfuLogin.class));
        step("Verify token in the response", () ->
                assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulLoginTest() {
        PartialCredentialsBody body = new PartialCredentialsBody();
        body.setEmail("morpheus@matr.ix");
        String expectedResponse = "Missing password";

        ResponseBadRequest response = step("Make a request", () ->
                given()
                        .filter(withCustomTemplates())
                        .spec(Specs.requestSpec)
                        .body(body)
                        .when()
                        .post("/login")
                        .then()
                        .spec(Specs.responseError)
                        .extract().as(ResponseBadRequest.class));
        step("Verify error in the response", () ->
                assertThat(response.getError()).isEqualTo(expectedResponse));
    }
}
