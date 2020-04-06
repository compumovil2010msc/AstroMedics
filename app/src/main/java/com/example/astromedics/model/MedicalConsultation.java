package com.example.astromedics.model;

public class MedicalConsultation {
    private int medicalConsultationId;
    private Appointment appointment;
    private Evolution evolution;
    private Report report;
    private Localization localization;
    private double calification;

    public MedicalConsultation(int medicalConsultationId, Appointment appointment, Evolution evolution, Report report, Localization localization,
                               double calification) {
        this.medicalConsultationId = medicalConsultationId;
        this.appointment = appointment;
        this.evolution = evolution;
        this.report = report;
        this.localization = localization;
        this.calification = calification;
    }

    public int getMedicalConsultationId() {
        return medicalConsultationId;
    }

    public void setMedicalConsultationId(int medicalConsultationId) {
        this.medicalConsultationId = medicalConsultationId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Evolution getEvolution() {
        return evolution;
    }

    public void setEvolution(Evolution evolution) {
        this.evolution = evolution;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public double getCalification() {
        return calification;
    }

    public void setCalification(double calification) {
        this.calification = calification;
    }
}
