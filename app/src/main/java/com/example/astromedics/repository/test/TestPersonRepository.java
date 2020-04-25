package com.example.astromedics.repository.test;

import com.example.astromedics.model.Person;
import com.example.astromedics.repository.interfaces.PersonRepository;

import java.util.List;

public class TestPersonRepository implements PersonRepository {
    @Override
    public Person get(String email) throws Exception {
        for (Person person : RepositorySimulator.getInstance()
                                                .getPersons()) {
            if (person.getEmail()
                      .equals(email)) {
                return person;
            }
        }

        throw new Exception("No se encontró la persona solicitada");
    }
}
