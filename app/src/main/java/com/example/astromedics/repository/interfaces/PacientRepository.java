package com.example.astromedics.repository.interfaces;

import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.MedicalRecord;
import com.example.astromedics.model.Pacient;

import java.util.List;

public interface PacientRepository {
    Pacient getPacient(String email);
}
