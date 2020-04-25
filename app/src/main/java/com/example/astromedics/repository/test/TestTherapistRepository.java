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

        throw new Exception("No se encontró la persona solicitada");
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

        throw new Exception("No se encontró la persona solicitada");
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
                                                                                                     endDate) && appointment.getStartDate()
                                                                                                                            .after(new Date())) {
                        returnable.add((Therapist) person);
                        break;
                    }
                }
            }
        }

        return returnable;
    }

    @Override
    public void createAppointment(Therapist therapist, Date startDate, Date endDate) throws Exception {
        RepositorySimulator repository = RepositorySimulator.getInstance();
        MedicalConsultation returnable = null;
        List<Person> persons = repository.getPersons();

        if (startDate.before(new Date())) {
            throw new Exception("La fecha seleccionada debe ser superior a la fecha actual");
        }

        if (startDate.after(endDate)) {
            throw new Exception("Las fechas ingresadas no son validas");
        }

        for (Person person : persons) {
            if (person.getEmail()
                      .equals(therapist.getEmail()) && person instanceof Therapist) {
                List<Appointment> appointments = ((Therapist) person).getAppointments();
                appointments.add(new Appointment(RepositorySimulator.appointmentId++,
                                                 startDate,
                                                 endDate,
                                                 null));
                ((Therapist) person).setAppointments(appointments);
            }
        }

        repository.setPersons(persons);
    }
}
