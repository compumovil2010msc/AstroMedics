package com.example.astromedics.repository.test;

import com.example.astromedics.model.MedicalRecord;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.Person;
import com.example.astromedics.repository.interfaces.PacientRepository;

public class TestPacientRepository implements PacientRepository {

    @Override
    public Pacient getPacient(String email) {
        for (Person person : RepositorySimulator.getInstance()
                                                .getPersons()) {
            if (person.getEmail()
                      .equals(email) && person instanceof Pacient) {
                return (Pacient) person;
            }
        }
        return null;
    }
}
