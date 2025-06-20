package pl.piomin.quarkus.ai;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletControllerTest {

    @Test
    @Order(1)
    void testCalculateWalletValueWithTools() {
        given()
          .when().get("/wallet/with-tools")
          .then().statusCode(200)
                 .contentType(ContentType.TEXT)
                 .body(notNullValue())
                 .body(not(emptyString()));
    }

    @Test
    @Order(2)
    void testCalculateHighestWalletValue() {
        given()
          .pathParam("days", 7)
          .when().get("/wallet/highest-day/{days}")
          .then().statusCode(200)
                 .contentType(ContentType.TEXT)
                 .body(notNullValue())
                 .body(not(emptyString()));
    }

}