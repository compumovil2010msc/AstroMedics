package com.example.astromedics.model;

import java.io.Serializable;

public class MedicalConsultation implements Serializable {
    private int medicalConsultationId;
    private Appointment appointment;
    private Evolution evolution;
    private Report report;
    private Localization localization;
    private double calification;
    private String emphasis;

    public MedicalConsultation(int medicalConsultationId, Evolution evolution, Report report,
                               Localization localization, double calification, String emphasis) {
        this.medicalConsultationId = medicalConsultationId;
        this.evolution = evolution;
        this.report = report;
        this.localization = localization;
        this.calification = calification;
        this.emphasis = emphasis;
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

    public String getEmphasis() {
        return emphasis;
    }

    public void setEmphasis(String emphasis) {
        this.emphasis = emphasis;
    }
}
