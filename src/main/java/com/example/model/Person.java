// File: src/main/java/com/example/model/Person.java
package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Person(
    @JsonProperty("id") String id,
    @JsonProperty("firstName") String firstName,
    @JsonProperty("lastName") String lastName,
    @JsonProperty("age") int age,
    @JsonProperty("gender") String gender
) {
    public Person {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150");
        }
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be null or empty");
        }
    }
}

// File: src/main/java/com/example/controller/PersonController.java (import update)
-import com.example.Person;
+import com.example.model.Person;