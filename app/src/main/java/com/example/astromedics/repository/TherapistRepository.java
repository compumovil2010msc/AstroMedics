package com.example.astromedics.repository;

import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.EducationalFormation;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;

public interface TherapistRepository {
    EducationalFormation getEducationalFormation(Therapist therapist);
    Appointment getAppointments(Therapist therapist);
    MedicalConsultation getMedicalConsultation(Appointment appointment);
}
