// src/test/java/pl/piomin/quarkus/ai/PersonControllerTest.java
package pl.piomin.quarkus.ai;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.piomin.quarkus.ai.api.PersonResponse;
import pl.piomin.quarkus.ai.model.Gender;
import pl.piomin.quarkus.ai.model.Person;
import pl.piomin.quarkus.ai.service.PersonAiService;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@QuarkusTest
class PersonControllerTest {

    @InjectMock
    PersonAiService personAiService;

    private PersonResponse mockPersonResponse;
    private Person mockPerson;

    @BeforeEach
    void setUp() {
        mockPerson = createMockPerson(1, "John", "Doe", 30, "American", Gender.MALE);
        Person mockPerson2 = createMockPerson(2, "Jane", "Smith", 25, "Canadian", Gender.FEMALE);
        Person mockPerson3 = createMockPerson(3, "Alice", "Johnson", 35, "British", Gender.FEMALE);

        mockPersonResponse = new PersonResponse();
        mockPersonResponse.setPersons(Arrays.asList(mockPerson, mockPerson2, mockPerson3));
    }

    @Test
    void testGeneratePersons_Success() {
        when(personAiService.generatePersonList()).thenReturn(mockPersonResponse);

        given()
        .when()
          .get("/api/persons")
        .then()
          .statusCode(200)
          .contentType(ContentType.JSON)
          .body("persons", hasSize(3))
          .body("persons[0].id", equalTo(1))
          .body("persons[0].firstName", equalTo("John"))
          .body("persons[0].lastName", equalTo("Doe"))
          .body("persons[0].age", equalTo(30))
          .body("persons[0].nationality", equalTo("American"))
          .body("persons[0].gender", equalTo("MALE"));
    }

    @Test
    void testGeneratePersons_EmptyList() {
        PersonResponse emptyResponse = new PersonResponse();
        emptyResponse.setPersons(Arrays.asList());
        when(personAiService.generatePersonList()).thenReturn(emptyResponse);

        given()
        .when()
          .get("/api/persons")
        .then()
          .statusCode(200)
          .contentType(ContentType.JSON)
          .body("persons", hasSize(0));
    }

    @Test
    void testGeneratePersons_ServiceThrowsException() {
        when(personAiService.generatePersonList()).thenThrow(new RuntimeException("AI service unavailable"));

        given()
        .when()
          .get("/api/persons")
        .then()
          .statusCode(500);
    }

    @Test
    void testGetPersonById_Success() {
        when(personAiService.getPersonById(1)).thenReturn(mockPerson);

        given()
          .pathParam("id", 1)
        .when()
          .get("/api/persons/{id}")
        .then()
          .statusCode(200)
          .contentType(ContentType.JSON)
          .body("id", equalTo(1))
          .body("firstName", equalTo("John"))
          .body("lastName", equalTo("Doe"))
          .body("age", equalTo(30))
          .body("nationality", equalTo("American"))
          .body("gender", equalTo("MALE"));
    }

    @Test
    void testGetPersonById_PersonNotFound() {
        when(personAiService.getPersonById(999)).thenReturn(null);

        given()
          .pathParam("id", 999)
        .when()
          .get("/api/persons/{id}")
        .then()
          .statusCode(200)
          .body(equalTo("null"));
    }

    @Test
    void testGetPersonById_ServiceThrowsException() {
        when(personAiService.getPersonById(anyInt())).thenThrow(new RuntimeException("AI service error"));

        given()
          .pathParam("id", 1)
        .when()
          .get("/api/persons/{id}")
        .then()
          .statusCode(500);
    }

    @Test
    void testGetPersonById_InvalidIdFormat() {
        given()
          .pathParam("id", "invalid")
        .when()
          .get("/api/persons/{id}")
        .then()
          .statusCode(404);
    }

    @Test
    void testGetPersonById_NegativeId() {
        Person negativePerson = createMockPerson(-1, "Negative", "Person", 25, "Unknown", Gender.MALE);
        when(personAiService.getPersonById(-1)).thenReturn(negativePerson);

        given()
          .pathParam("id", -1)
        .when()
          .get("/api/persons/{id}")
        .then()
          .statusCode(200)
          .contentType(ContentType.JSON)
          .body("id", equalTo(-1))
          .body("firstName", equalTo("Negative"));
    }

    @Test
    void testApiResponseHeaders() {
        when(personAiService.generatePersonList()).thenReturn(mockPersonResponse);

        given()
        .when()
          .get("/api/persons")
        .then()
          .statusCode(200)
          .header("Content-Type", containsString("application/json"));
    }

    @Test
    void testApiAcceptsJsonContentType() {
        when(personAiService.generatePersonList()).thenReturn(mockPersonResponse);

        given()
          .contentType(ContentType.JSON)
          .accept(ContentType.JSON)
        .when()
          .get("/api/persons")
        .then()
          .statusCode(200)
          .contentType(ContentType.JSON);
    }

    private Person createMockPerson(Integer id, String firstName, String lastName, int age, String nationality, Gender gender) {
        Person person = new Person();
        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAge(age);
        person.setNationality(nationality);
        person.setGender(gender);
        return person;
    }
}
</newLines>

<rationale>
Created new integration test class PersonControllerIntegrationTest.java under src/test/java/pl/piomin/quarkus/ai. This class uses @EnabledIfSystemProperty to guard calls that require the real AI service and includes a health‐check test for both endpoints.
</rationale>
<newLines>
// src/test/java/pl/piomin/quarkus/ai/PersonControllerIntegrationTest.java
package pl.piomin.quarkus.ai;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class PersonControllerIntegrationTest {

    @Test
    @EnabledIfSystemProperty(named = "integration.test.enabled", matches = "true")
    void testGeneratePersons_RealAiService() {
        given()
        .when()
          .get("/api/persons")
        .then()
          .statusCode(200)
          .contentType(ContentType.JSON)
          .body("persons", notNullValue())
          .body("persons", hasSize(greaterThan(0)))
          .body("persons[0].id", notNullValue())
          .body("persons[0].firstName", notNullValue())
          .body("persons[0].lastName", notNullValue())
          .body("persons[0].age", greaterThan(0))
          .body("persons[0].nationality", notNullValue())
          .body("persons[0].gender", anyOf(equalTo("MALE"), equalTo("FEMALE")));
    }

    @Test
    @EnabledIfSystemProperty(named = "integration.test.enabled", matches = "true")
    void testGetPersonById_RealAiService() {
        given()
          .pathParam("id", 1)
        .when()
          .get("/api/persons/{id}")
        .then()
          .statusCode(200)
          .contentType(ContentType.JSON)
          .body("id", equalTo(1))
          .body("firstName", notNullValue())
          .body("lastName", notNullValue())
          .body("age", greaterThan(0))
          .body("nationality", notNullValue())
          .body("gender", anyOf(equalTo("MALE"), equalTo("FEMALE")));
    }

    @Test
    void testEndpointsExist_HealthCheck() {
        given()
        .when()
          .get("/api/persons")
        .then()
          .statusCode(anyOf(equalTo(200), equalTo(500)));

        given()
          .pathParam("id", 1)
        .when()
          .get("/api/persons/{id}")
        .then()
          .statusCode(anyOf(equalTo(200), equalTo(500)));
    }
}
</newLines>

<rationale>
Ensured that pom.xml declares the necessary test-scoped dependencies for Quarkus JUnit5, RestAssured, and Mockito so that the new unit and integration tests compile and run correctly.
</rationale>
<newLines>
<!-- add under <dependencies> in pom.xml -->
<dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-junit5</artifactId>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-rest-assured</artifactId>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-test-mockito</artifactId>
  <scope>test</scope>
</dependency>