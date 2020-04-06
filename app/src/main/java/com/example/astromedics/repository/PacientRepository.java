package com.example.astromedics.repository;

import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.MedicalRecord;
import com.example.astromedics.model.Pacient;

public interface PacientRepository {
    MedicalRecord getMedicalRecord(Pacient pacient);
    MedicalConsultation getMedicalConsultation(Pacient pacient);
}
