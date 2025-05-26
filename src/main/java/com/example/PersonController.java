package com.example.controller;

import com.example.model.Person;
import com.example.service.PersonAiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/api/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonController {

    private static final Logger LOG = Logger.getLogger(PersonController.class);

    @Inject
    PersonAiService personAiService;

    @Inject
    ObjectMapper objectMapper;

    @GET
    @Path("/generate")
    public Response generatePersons() {
        try {
            LOG.info("Generating list of 10 persons using AI");
            
            String aiResponse = personAiService.generatePersonList();
            LOG.info("AI Response: " + aiResponse);
            
            // Parse the JSON response from AI
            List<Person> persons = objectMapper.readValue(aiResponse, new TypeReference<List<Person>>() {});
            
            if (persons.size() != 10) {
                LOG.warn("Expected 10 persons but got " + persons.size());
            }
            
            return Response.ok(persons).build();
            
        } catch (JsonProcessingException e) {
            LOG.error("Failed to parse AI response as JSON", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to parse AI response: " + e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOG.error("Failed to generate persons", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to generate persons: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getPersonById(@PathParam("id") String id) {
        try {
            LOG.info("Getting person with ID: " + id);
            
            // Use a session ID for chat memory - in real app this could be user-specific
            String sessionId = "session-" + UUID.randomUUID().toString();
            
            String aiResponse = personAiService.getPersonById(sessionId, id);
            LOG.info("AI Response: " + aiResponse);
            
            // Parse the response - could be a Person or an error message
            Object response = objectMapper.readValue(aiResponse, Object.class);
            
            return Response.ok(response).build();
            
        } catch (JsonProcessingException e) {
            LOG.error("Failed to parse AI response as JSON", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to parse AI response: " + e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOG.error("Failed to get person by ID", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to get person: " + e.getMessage()))
                    .build();
        }
    }
}