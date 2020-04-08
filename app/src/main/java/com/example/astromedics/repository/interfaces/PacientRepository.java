package com.example.astromedics.repository.interfaces;

import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.Localization;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.Therapist;

public interface PacientRepository {
    Pacient getPacient(String email) throws Exception;

    MedicalConsultation createMedicalConsultation(Pacient pacient, Therapist therapist, Therapist.Emphasis emphasis, Localization localization, Appointment appointment) throws Exception;
}
