package com.example.astromedics.repository;

import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.repository.test.TestPersonRepository;

public class Repository {
    private static Repository instance;
    private PersonRepository personRepository;
    private PacientRepository pacientRepository;
    private TherapistRepository therapistRepository;
    private MedicalConsultation medicalConsultation;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
            instance.personRepository = new TestPersonRepository();
            instance.pacientRepository = null;
            instance.therapistRepository = null;
            instance.medicalConsultation = null;
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

    public MedicalConsultation getMedicalConsultation() {
        return medicalConsultation;
    }
}
