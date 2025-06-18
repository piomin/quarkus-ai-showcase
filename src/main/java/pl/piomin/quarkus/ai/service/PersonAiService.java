package pl.piomin.quarkus.ai.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;
import pl.piomin.quarkus.ai.api.PersonResponse;
import pl.piomin.quarkus.ai.model.Person;

@RegisterAiService
@ApplicationScoped
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
        - Return ONLY the JSON array, no additional text
        """)
    PersonResponse generatePersonList(@MemoryId int userId);

    @SystemMessage("""
        You are a helpful assistant that can recall generated person data from chat memory.
        """)
    @UserMessage("""
        In the previously generated list of persons for user {userId}, find and return the person with id {id}.
        
        Return ONLY the JSON object, no additional text.
        """)
    Person getPersonById(@MemoryId int userId, int id);

}