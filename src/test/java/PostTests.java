import models.*;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostTests {
    @Test
    void successfulCreateTest() {
        CreateBody body = new CreateBody();
        body.setName("morpheus");
        body.setJob("master");

        ResponseUserInfo response = given()
                .spec(Specs.request)
                .body(body)
                .when()
                .post("/users")
                .then()
                .spec(Specs.responseCreateUser)
                .extract().as(ResponseUserInfo.class);
        assertThat(response.getName()).isEqualTo(body.getName());
        assertThat(response.getJob()).isEqualTo(body.getJob());
    }

    @Test
    void successfulRegisterTest() {
        Integer expectedId = 4;
        String expectedToken = "QpwL5tke4Pnpja7X4";

        CredentialsBody body = new CredentialsBody();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        ResponseSuccessfulRegistration response =
                given()
                .spec(Specs.request)
                .body(body)
                .when()
                .post("/register")
                .then()
                .spec(Specs.responseSpec)
                .extract().as(ResponseSuccessfulRegistration.class);
        assertThat(response.getId()).isEqualTo(expectedId);
        assertThat(response.getToken()).isEqualTo(expectedToken);
    }

    @Test
    void unsuccessfulRegisterTest() {
        String expectedResponse = "Missing password";

        PartialCredentialsBody body = new PartialCredentialsBody();
        body.setEmail("sydney@fife");

        ResponseBadRequest response = given()
                .spec(Specs.request)
                .body(body)
                .when()
                .post("/register")
                .then()
                .spec(Specs.responseError)
                .extract().as(ResponseBadRequest.class);
        assertThat(response.getError()).isEqualTo(expectedResponse);
    }

    @Test
    void successfulLoginTest() {
        CredentialsBody body = new CredentialsBody();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("123");

        ResponseSuccessfuLogin response = given()
                .spec(Specs.request)
                .body(body)
                .when()
                .post("/login")
                .then()
                .spec(Specs.responseSpec)
                .extract().as(ResponseSuccessfuLogin.class);
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    void unsuccessfulLoginTest() {
        PartialCredentialsBody body = new PartialCredentialsBody();
        body.setEmail("morpheus@matr.ix");
        String expectedResponse = "Missing password";

        ResponseBadRequest response = given()
                .spec(Specs.request)
                .body(body)
                .when()
                .post("/login")
                .then()
                .spec(Specs.responseError)
                .extract().as(ResponseBadRequest.class);
        assertThat(response.getError()).isEqualTo(expectedResponse);
        assertEquals(expectedResponse, response.getError());
    }
}
