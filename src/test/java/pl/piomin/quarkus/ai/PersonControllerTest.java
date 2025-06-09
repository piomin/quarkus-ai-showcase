// File: src/test/java/pl/piomin/quarkus/ai/PersonControllerTest.java
package pl.piomin.quarkus.ai;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.piomin.quarkus.ai.api.PersonResponse;
import pl.piomin.quarkus.ai.model.Person;
import pl.piomin.quarkus.ai.model.Gender;
import pl.piomin.quarkus.ai.service.PersonAiService;
import java.util.Arrays;
import java.util.Collections;
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
        Person p2 = createMockPerson(2, "Jane", "Smith", 25, "Canadian", Gender.FEMALE);
        Person p3 = createMockPerson(3, "Alice", "Johnson", 35, "British", Gender.FEMALE);
        mockPersonResponse = new PersonResponse();
        mockPersonResponse.setPersons(Arrays.asList(mockPerson, p2, p3));
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

    @Test
    void testGeneratePersons_Success() {
        when(personAiService.generatePersonList()).thenReturn(mockPersonResponse);
        given()
          .when().get("/api/persons")
          .then().statusCode(200)
                 .contentType(ContentType.JSON)
                 .body("persons", hasSize(3))
                 .body("persons[0].firstName", equalTo("John"))
                 .body("persons[1].gender", equalTo("FEMALE"))
                 .body("persons[2].firstName", equalTo("Alice"));
    }

    @Test
    void testGeneratePersons_EmptyList() {
        PersonResponse empty = new PersonResponse();
        empty.setPersons(Collections.emptyList());
        when(personAiService.generatePersonList()).thenReturn(empty);
        given()
          .when().get("/api/persons")
          .then().statusCode(200)
                 .body("persons", hasSize(0));
    }

    @Test
    void testGeneratePersons_ServiceThrowsException() {
        when(personAiService.generatePersonList()).thenThrow(new RuntimeException("AI service unavailable"));
        given()
          .when().get("/api/persons")
          .then().statusCode(500);
    }

    @Test
    void testGetPersonById_Success() {
        when(personAiService.getPersonById(1)).thenReturn(mockPerson);
        given().pathParam("id", 1)
          .when().get("/api/persons/{id}")
          .then().statusCode(200)
                 .contentType(ContentType.JSON)
                 .body("id", equalTo(1))
                 .body("firstName", equalTo("John"));
    }

    @Test
    void testGetPersonById_PersonNotFound() {
        when(personAiService.getPersonById(999)).thenReturn(null);
        given().pathParam("id", 999)
          .when().get("/api/persons/{id}")
          .then().statusCode(200)
                 .body(equalTo("null"));
    }

    @Test
    void testGetPersonById_ServiceThrowsException() {
        when(personAiService.getPersonById(anyInt())).thenThrow(new RuntimeException("error"));
        given().pathParam("id", 1)
          .when().get("/api/persons/{id}")
          .then().statusCode(500);
    }

    @Test
    void testGetPersonById_InvalidIdFormat() {
        given()
          .when().get("/api/persons/invalid")
          .then().statusCode(404);
    }

    @Test
    void testGetPersonById_NegativeId() {
        Person neg = createMockPerson(-1, "Neg", "Pers", 20, "X", Gender.MALE);
        when(personAiService.getPersonById(-1)).thenReturn(neg);
        given().pathParam("id", -1)
          .when().get("/api/persons/{id}")
          .then().statusCode(200)
                 .body("id", equalTo(-1));
    }

    @Test
    void testApiResponseHeaders() {
        when(personAiService.generatePersonList()).thenReturn(mockPersonResponse);
        given()
          .when().get("/api/persons")
          .then().statusCode(200)
                 .header("Content-Type", containsString("application/json"));
    }

    @Test
    void testApiAcceptsJsonContentType() {
        when(personAiService.generatePersonList()).thenReturn(mockPersonResponse);
        given().contentType(ContentType.JSON).accept(ContentType.JSON)
          .when().get("/api/persons")
          .then().statusCode(200)
                 .contentType(ContentType.JSON);
    }
}

// File: src/test/java/pl/piomin/quarkus/ai/PersonControllerIntegrationTest.java
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
          .when().get("/api/persons")
          .then().statusCode(200)
                 .contentType(ContentType.JSON)
                 .body("persons", notNullValue())
                 .body("persons", hasSize(greaterThan(0)));
    }

    @Test
    @EnabledIfSystemProperty(named = "integration.test.enabled", matches = "true")
    void testGetPersonById_RealAiService() {
        given().pathParam("id", 1)
          .when().get("/api/persons/{id}")
          .then().statusCode(200)
                 .contentType(ContentType.JSON)
                 .body("id", equalTo(1))
                 .body("firstName", notNullValue());
    }

    @Test
    void testEndpointsExist_HealthCheck() {
        given()
          .when().get("/api/persons")
          .then().statusCode(anyOf(equalTo(200), equalTo(500)));
        given().pathParam("id", 1)
          .when().get("/api/persons/{id}")
          .then().statusCode(anyOf(equalTo(200), equalTo(500)));
    }
}