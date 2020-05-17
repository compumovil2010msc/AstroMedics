package com.example.astromedics.repository.interfaces;

import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.EducationalFormation;
import com.example.astromedics.model.Evolution;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Report;
import com.example.astromedics.model.Therapist;

import java.util.Date;
import java.util.List;

public interface TherapistRepository {
    Therapist getTherapist(String email) throws Exception;
    Therapist getTherapist(MedicalConsultation medicalConsultation) throws Exception;
    List<Therapist> finAvailableTherapists(Therapist.Emphasis emphasis, Date startDate, Date endDate) throws Exception;
    void createAppointment(Therapist therapist, Date startDate, Date endDate) throws Exception;
    MedicalConsultation setEvolution(Therapist therapist, MedicalConsultation medicalConsultation, String evolutionContent) throws Exception;
    MedicalConsultation setReport(Therapist therapist, MedicalConsultation medicalConsultation, String reportContent) throws Exception;
}
