package com.example.astromedics.repository.test;

import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Person;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.interfaces.TherapistRepository;

public class TestTherapistRepository implements TherapistRepository {
    @Override
    public Therapist getTherapist(String email) {
        for (Person person : RepositorySimulator.getInstance()
                                                .getPersons()) {
            if (person.getEmail()
                      .equals(email) && person instanceof Therapist) {
                return (Therapist) person;
            }
        }
        return null;
    }

    @Override
    public Therapist getTherapist(MedicalConsultation medicalConsultation) {
        for (Person person : RepositorySimulator.getInstance()
                                                .getPersons()) {
            if (person instanceof Therapist) {
                for (Appointment appointment : ((Therapist) person).getAppointments()) {
                    if (appointment.getMedicalConsultation() != null && appointment.getMedicalConsultation()
                                                                                   .getMedicalConsultationId() == medicalConsultation.getMedicalConsultationId()) {
                        return (Therapist) person;
                    }
                }
            }
        }
        return null;
    }
}
