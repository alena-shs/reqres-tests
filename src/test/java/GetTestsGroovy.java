import models.CreateBody;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class GetTestsGroovy {
    @Test
    void successfulCreateTest() {
        CreateBody body = new CreateBody();
        body.setName("morpheus");
        body.setJob("master");
        given()
                .spec(Specs.request)
                .body(body)
                .when()
                .get("/unknown")
                .then()
                .spec(Specs.responseSpec)
                .body("page", is(1))
                .body("data.id.flatten()", hasItems(1, 2, 3, 4, 5, 6))
                .body("data.name.flatten()", hasItem("fuchsia rose"));

        }
}
