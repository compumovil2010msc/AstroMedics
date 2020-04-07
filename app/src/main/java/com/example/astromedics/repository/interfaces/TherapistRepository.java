package com.example.astromedics.repository.interfaces;

import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.EducationalFormation;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;

public interface TherapistRepository {
    Therapist getTherapist(String email);
    Therapist getTherapist(MedicalConsultation medicalConsultation);
}
