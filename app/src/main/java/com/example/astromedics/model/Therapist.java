package com.example.astromedics.model;

import java.util.Date;
import java.util.List;

public class Therapist extends Person {
    private List<String> enphasis;
    private List<EducationalFormation> educationalFormation;
    private List<Appointment> appointments;

    public Therapist(int personId, int identificationNumber, String name, long houseNumber, long phoneNumber, String address, String email,
                     String password, Date admissionDate, List<String> enphasis,
                     List<EducationalFormation> educationalFormation, List<Appointment> appointments) {
        super(personId,
              identificationNumber,
              name,
              houseNumber,
              phoneNumber,
              address,
              email,
              password,
              admissionDate);
        this.enphasis = enphasis;
        this.educationalFormation = educationalFormation;
        this.appointments = appointments;
    }

    public List<String> getEnphasis() {
        return enphasis;
    }

    public void setEnphasis(List<String> enphasis) {
        this.enphasis = enphasis;
    }

    public List<EducationalFormation> getEducationalFormation() {
        return educationalFormation;
    }

    public void setEducationalFormation(List<EducationalFormation> educationalFormation) {
        this.educationalFormation = educationalFormation;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
