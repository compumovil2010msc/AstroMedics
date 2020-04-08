package com.example.astromedics.repository.test;

import com.example.astromedics.helpers.DateComparator;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Person;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.interfaces.TherapistRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestTherapistRepository implements TherapistRepository {
    @Override
    public Therapist getTherapist(String email) throws Exception {
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
    public Therapist getTherapist(MedicalConsultation medicalConsultation) throws Exception {
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

    @Override
    public List<Therapist> finAvailableTherapists(Therapist.Emphasis emphasis, Date startDate, Date endDate) throws Exception {
        List<Therapist> returnable = new ArrayList<>();

        for (Person person : RepositorySimulator.getInstance()
                                                .getPersons()) {
            if (person instanceof Therapist && ((Therapist) person).getEmphasis()
                                                                   .contains(emphasis)) {
                for (Appointment appointment : ((Therapist) person).getAppointments()) {
                    if (appointment.getMedicalConsultation() == null && new DateComparator().between(appointment.getStartDate(),
                                                                                                     startDate,
                                                                                                     endDate)) {
                        returnable.add((Therapist) person);
                        break;
                    }
                }
            }
        }

        return returnable;
    }
}
