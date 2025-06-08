package pl.piomin.quarkus.ai.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import pl.piomin.quarkus.ai.api.PersonResponse;
import pl.piomin.quarkus.ai.model.Person;

@RegisterAiService
public interface PersonAiService {

    @SystemMessage("""
        You are a helpful assistant that generates realistic person data.
        Always respond with valid JSON format.
        """)
    @UserMessage("""
        Generate exactly 10 unique persons

        Requirements:
        - Each person must have a unique integer ID (like 1, 2, 3, etc.)
        - Use realistic first and last names per each nationality
        - Ages should be between 18 and 80
        - Gender should be either "Male" or "Female"
        - Return ONLY the JSON array, no additional text
        """)
    PersonResponse generatePersonList();

    @SystemMessage("""
        You are a helpful assistant that can recall previously generated person data.
        The user has previously generated a list of 10 persons.
        """)
    @UserMessage("""
        From the previously generated list of persons, please return the person with ID: {id}
        
        Return ONLY the JSON object, no additional text.
        """)
    Person getPersonById(Integer id);
    }