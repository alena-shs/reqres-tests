package tests;

import org.junit.jupiter.api.Test;
import specs.Specs;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class GroovyGetTests {
    @Test
    void successfulCreateTest() {
        step("Send a GET request", () ->
                given()
                        .filter(withCustomTemplates())
                        .spec(Specs.requestSpec)
                        .when()
                        .get("/unknown")
                        .then()
                        .spec(Specs.successResponseSpec)
                        .body("page", is(1))
                        .body("data.id.flatten()", hasItems(1, 2, 3, 4, 5, 6))
                        .body("data.name.flatten()", hasItem("fuchsia rose")));

    }
}
