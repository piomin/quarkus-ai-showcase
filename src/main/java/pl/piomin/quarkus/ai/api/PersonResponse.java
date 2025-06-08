package pl.piomin.quarkus.ai.api;

import pl.piomin.quarkus.ai.model.Person;

import java.util.List;

public class PersonResponse {

    private List<Person> persons;

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
