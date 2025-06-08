package pl.piomin.quarkus.ai;

import pl.piomin.quarkus.ai.api.PersonResponse;
import pl.piomin.quarkus.ai.model.Person;
import pl.piomin.quarkus.ai.service.PersonAiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

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
    public PersonResponse generatePersons() {
        return personAiService.generatePersonList();
    }

    @GET
    @Path("/{id}")
    public Person getPersonById(@PathParam("id") int id) {
        return personAiService.getPersonById(id);
    }

}