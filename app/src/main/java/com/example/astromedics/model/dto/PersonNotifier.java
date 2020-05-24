package com.example.astromedics.model.dto;

public class PersonNotifier {
    private String email;

    private int medicalConsultationId;

    private boolean alreadyNotified;

    public PersonNotifier() {
    }

    public PersonNotifier(String email, int medicalConsultationId) {
        this.email = email;
        this.medicalConsultationId = medicalConsultationId;
        alreadyNotified = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMedicalConsultationId() {
        return medicalConsultationId;
    }

    public void setMedicalConsultationId(int medicalConsultationId) {
        this.medicalConsultationId = medicalConsultationId;
    }
}
