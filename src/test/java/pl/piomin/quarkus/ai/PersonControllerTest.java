package pl.piomin.quarkus.ai;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest {

    @Test
    @Order(1)
    void testGeneratePersons() {
        given()
          .when().get("/api/1/persons")
          .then().statusCode(200)
                 .contentType(ContentType.JSON)
                 .body("persons", hasSize(10))
                 .body("persons[0].firstName", any(String.class))
                 .body("persons[1].gender", in(List.of("FEMALE", "MALE")))
                 .body("persons[2].age", greaterThanOrEqualTo(18));
    }


    @Test
    @Order(2)
    void testGetPersonById() {
        given()
          .when().get("/api/1/persons/1")
          .then().statusCode(200)
                 .contentType(ContentType.JSON)
                 .body("id", equalTo(1))
                 .body("firstName", any(String.class));
    }

}