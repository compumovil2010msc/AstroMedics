package com.example.astromedics.repository;

import com.example.astromedics.model.Evolution;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Report;

public interface MedicalConsultationRepository {
    Report getReport(MedicalConsultation medicalConsultation);
    Evolution getEvolution(MedicalConsultation medicalConsultation);
}
