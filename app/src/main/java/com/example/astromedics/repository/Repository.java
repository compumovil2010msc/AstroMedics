package com.example.astromedics.repository;

import com.example.astromedics.repository.interfaces.PacientRepository;
import com.example.astromedics.repository.interfaces.PersonRepository;
import com.example.astromedics.repository.interfaces.TherapistRepository;
import com.example.astromedics.repository.test.TestPacientRepository;
import com.example.astromedics.repository.test.TestPersonRepository;
import com.example.astromedics.repository.test.TestTherapistRepository;

public class Repository {
    private static Repository instance;
    private PersonRepository personRepository;
    private PacientRepository pacientRepository;
    private TherapistRepository therapistRepository;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
            instance.personRepository = new TestPersonRepository();
            instance.pacientRepository = new TestPacientRepository();
            instance.therapistRepository = new TestTherapistRepository();
        }

        return instance;
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    public PacientRepository getPacientRepository() {
        return pacientRepository;
    }

    public TherapistRepository getTherapistRepository() {
        return therapistRepository;
    }
}
