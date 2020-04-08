package com.example.astromedics.repository.test;

import com.example.astromedics.model.Person;
import com.example.astromedics.repository.interfaces.PersonRepository;

public class TestPersonRepository implements PersonRepository {
    @Override
    public Person login(String email, String password) throws Exception {
        for (Person person : RepositorySimulator.getInstance()
                                                .getPersons()) {
            if (person.getEmail()
                      .equals(email) && person.getPassword()
                                              .equals(password)) {
                return person;
            }
        }
        return null;
    }
}
