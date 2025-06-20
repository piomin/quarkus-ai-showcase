package pl.piomin.quarkus.ai.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.piomin.quarkus.ai.api.PersonResponse;
import pl.piomin.quarkus.ai.model.Person;
import pl.piomin.quarkus.ai.service.PersonAiService;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonController {

    PersonAiService personAiService;

    public PersonController(PersonAiService personAiService) {
        this.personAiService = personAiService;
    }

    @GET
    @Path("/{userId}/persons")
    public PersonResponse generatePersons(@PathParam("userId") int userId) {
        return personAiService.generatePersonList(userId);
    }

    @GET
    @Path("/{userId}/persons/{id}")
    public Person getPersonById(@PathParam("userId") int userId, @PathParam("id") int id) {
        return personAiService.getPersonById(userId, id);
    }

}