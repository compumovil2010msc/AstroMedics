package com.example.astromedics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
    private int AppointmentId;
    private Date startDate;
    private Date endDate;
    private MedicalConsultation medicalConsultation;

    public Appointment(int appointmentId, Date startDate, Date endDate, MedicalConsultation medicalConsultation) {
        AppointmentId = appointmentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.medicalConsultation = medicalConsultation;
    }

    public int getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        AppointmentId = appointmentId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public MedicalConsultation getMedicalConsultation() {
        return medicalConsultation;
    }

    public void setMedicalConsultation(MedicalConsultation medicalConsultation) {
        this.medicalConsultation = medicalConsultation;
    }
}
