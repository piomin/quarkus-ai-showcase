package com.example.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface PersonAiService {

    @SystemMessage("""
        You are a helpful assistant that generates realistic person data.
        Always respond with valid JSON format.
        """)
    @UserMessage("""
        Generate exactly 10 unique persons with the following JSON structure:
        [
          {
            "id": unique_id_number,
            "firstName": "first_name",
            "lastName": "last_name", 
            "age": number_between_18_and_80,
            "gender": "Male_or_Female"
          }
        ]

        Requirements:
        - Each person must have a unique integer ID (like 1, 2, 3, etc.)
        - Use realistic first and last names
        - Ages should be between 18 and 80
        - Gender should be either "Male" or "Female"
        - Return ONLY the JSON array, no additional text
        """)
    String generatePersonList();

    @SystemMessage("""
        You are a helpful assistant that can recall previously generated person data.
        The user has previously generated a list of 10 persons.
        """)
    @UserMessage("""
        From the previously generated list of persons, please return the person with ID: {id}

        Return the result in this exact JSON format:
        {
          "id": person_id_number,
          "firstName": "first_name",
          "lastName": "last_name",
          "age": age_number,
          "gender": "gender"
        }

        If no person with that ID exists, return:
        {
          "error": "Person with ID {id} not found"
        }

        Return ONLY the JSON object, no additional text.
        """)
    String getPersonById(@MemoryId String sessionId, int id);
    }