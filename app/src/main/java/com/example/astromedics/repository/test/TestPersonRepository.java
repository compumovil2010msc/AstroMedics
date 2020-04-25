package com.example.astromedics.repository.test;

import com.example.astromedics.model.Person;
import com.example.astromedics.repository.interfaces.PersonRepository;

import java.util.List;

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

        throw new Exception("Credenciales incorrectas");
    }

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

    @Override
    public void changePassword(Person person, String currentPassword, String newPassword, String repeatNewPassword) throws Exception {
        RepositorySimulator repository = RepositorySimulator.getInstance();
        List<Person> persons = repository.getPersons();

        if (!newPassword.equals(repeatNewPassword)) {
            throw new Exception("Las contraseñas no coinciden");
        }

        for (Person currentPerson : persons) {
            if (currentPerson.getEmail()
                             .equals(person.getEmail())) {
                if (!currentPerson.getPassword()
                                  .equals(currentPassword)) {
                    throw new Exception("La contraseña actual es incorrecta");
                } else {
                    person.setPassword(newPassword);
                }
            }
        }

        repository.setPersons(persons);
    }
}
