package com.example.astromedics.repository.test;

import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.Localization;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.Person;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.interfaces.PacientRepository;

import java.util.List;

public class TestPacientRepository implements PacientRepository {

    @Override
    public Pacient getPacient(String email) throws Exception {
        for (Person person : RepositorySimulator.getInstance()
                                                .getPersons()) {
            if (person.getEmail()
                      .equals(email) && person instanceof Pacient) {
                return (Pacient) person;
            }
        }

        throw new Exception("No se encontró la persona solicitada");
    }

    @Override
    public MedicalConsultation createMedicalConsultation(Pacient pacient, Therapist therapist, Therapist.Emphasis emphasis, Localization localization,
                                                         Appointment appointment) throws Exception {
        RepositorySimulator repository = RepositorySimulator.getInstance();
        boolean settedPacient = false, settedTherapist = false;
        MedicalConsultation medicalConsultation = new MedicalConsultation(repository.medicalConsultationId++,
                                                                          null,
                                                                          null,
                                                                          localization,
                                                                          0.0,
                                                                          emphasis);

        List<Person> persons = repository.getPersons();

        for (Person person : persons) {
            if (person.getEmail()
                      .equals(pacient.getEmail()) && person instanceof Pacient) {
                settedPacient = true;
                ((Pacient) person).addMedicalHistory(medicalConsultation);
            }

            if (person.getEmail()
                      .equals(therapist.getEmail()) && person instanceof Therapist) {
                for (Appointment currentAppointment : ((Therapist) person).getAppointments()) {
                    if (currentAppointment.getAppointmentId() == appointment.getAppointmentId() && currentAppointment.getMedicalConsultation() == null) {
                        settedTherapist = true;
                        currentAppointment.setMedicalConsultation(medicalConsultation);
                        medicalConsultation.setAppointment(appointment);
                    }
                }
            }
        }

        if (!settedPacient) {
            throw new Exception("No se encontro el paciente");
        }

        if (!settedTherapist) {
            throw new Exception("No se encontro el terapeuta");
        }

        repository.setPersons(persons);

        return medicalConsultation;
    }
}
