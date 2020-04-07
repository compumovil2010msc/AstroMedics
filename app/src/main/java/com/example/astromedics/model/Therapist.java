package com.example.astromedics.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Therapist extends Person implements Serializable {
    private List<String> emphasis;
    private List<EducationalFormation> educationalFormation;
    private List<Appointment> appointments;

    public Therapist(int identificationNumber, String name, String photoURL, long houseNumber, long phoneNumber, String address, String email,
                     String password, Date admissionDate, List<String> emphasis,
                     List<EducationalFormation> educationalFormation, List<Appointment> appointments) {
        super(identificationNumber,
              name,
              photoURL,
              houseNumber,
              phoneNumber,
              address,
              email,
              password,
              admissionDate);
        this.emphasis = emphasis;
        this.educationalFormation = educationalFormation;
        this.appointments = appointments;
    }

    public List<String> getEmphasis() {
        return emphasis;
    }

    public void setEmphasis(List<String> emphasis) {
        this.emphasis = emphasis;
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
