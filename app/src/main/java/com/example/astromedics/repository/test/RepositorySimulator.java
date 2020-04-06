package com.example.astromedics.repository.test;

import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.EducationalFormation;
import com.example.astromedics.model.EvaluationQuestion;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.MedicalRecord;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.Person;
import com.example.astromedics.model.Therapist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepositorySimulator {
    private static RepositorySimulator instance;
    private List<Person> persons;

    public static RepositorySimulator getInstance() {
        if (instance == null) {
            instance = new RepositorySimulator();
            instance.init();
        }

        return instance;
    }

    public List<Person> getPersons() {
        return persons;
    }

    private void init() {
        persons = new ArrayList<>();
        persons.add(getTestTherapist1());
        persons.add(getTestTherapist2());
        persons.add(getPacient1());
        persons.add(getPacient2());
    }

    private Therapist getTestTherapist1() {
        List<String> enphasis = new ArrayList<>();
        enphasis.add("Fonoaudiologia");
        enphasis.add("Terapia Ocupacional");
        List<EducationalFormation> educationalFormation = new ArrayList<>();
        educationalFormation.add(new EducationalFormation(1,
                                                          "Fonoaudiologo",
                                                          "Pontificia Universidad Javeriana",
                                                          new Date(2014,
                                                                   1,
                                                                   1),
                                                          new Date(2018,
                                                                   12,
                                                                   1),
                                                          true));
        educationalFormation.add(new EducationalFormation(2,
                                                          "Fisioterapeuta",
                                                          "Pontificia Universidad Javeriana",
                                                          new Date(2014,
                                                                   1,
                                                                   1),
                                                          new Date(2018,
                                                                   12,
                                                                   1),
                                                          true));
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(1,
                                         new Date(2020,
                                                  04,
                                                  10,
                                                  14,
                                                  0),
                                         new Date(2020,
                                                  04,
                                                  10,
                                                  16,
                                                  0),
                                         null));
        appointments.add(new Appointment(2,
                                         new Date(2020,
                                                  04,
                                                  10,
                                                  16,
                                                  0),
                                         new Date(2020,
                                                  04,
                                                  10,
                                                  18,
                                                  0),
                                         null));
        Therapist therapist = new Therapist(1,
                                            1014276873,
                                            "Carlos Jaime Cobaleda Cruz",
                                            4101365,
                                            3003619443L,
                                            "Carrera 71D # 55-54",
                                            "charlie2634@gmail.com",
                                            "test1",
                                            new Date(),
                                            enphasis,
                                            educationalFormation,
                                            appointments);

        return therapist;
    }

    private Therapist getTestTherapist2() {
        List<String> enphasis = new ArrayList<>();
        enphasis.add("Fonoaudiologia");
        enphasis.add("Terapia Ocupacional");
        List<EducationalFormation> educationalFormation = new ArrayList<>();
        educationalFormation.add(new EducationalFormation(3,
                                                          "Fonoaudiologo",
                                                          "Pontificia Universidad Javeriana",
                                                          new Date(2014,
                                                                   1,
                                                                   1),
                                                          new Date(2018,
                                                                   12,
                                                                   1),
                                                          true));
        educationalFormation.add(new EducationalFormation(4,
                                                          "Fisioterapeuta",
                                                          "Pontificia Universidad Javeriana",
                                                          new Date(2014,
                                                                   1,
                                                                   1),
                                                          new Date(2018,
                                                                   12,
                                                                   1),
                                                          true));
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(3,
                                         new Date(2020,
                                                  04,
                                                  10,
                                                  14,
                                                  0),
                                         new Date(2020,
                                                  04,
                                                  10,
                                                  16,
                                                  0),
                                         null));
        appointments.add(new Appointment(4,
                                         new Date(2020,
                                                  04,
                                                  10,
                                                  16,
                                                  0),
                                         new Date(2020,
                                                  04,
                                                  10,
                                                  18,
                                                  0),
                                         null));
        Therapist therapist = new Therapist(2,
                                            123456789,
                                            "Paula Alejandra Jaime Londoño",
                                            4101365,
                                            3003619443L,
                                            "Carrera 71D # 55-54",
                                            "pjaime@gmail.com",
                                            "test1",
                                            new Date(),
                                            enphasis,
                                            educationalFormation,
                                            appointments);

        return therapist;
    }

    private Pacient getPacient1() {
        List<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
        evaluationQuestions.add(new EvaluationQuestion(1,
                                                       "¿Pregunta 1?",
                                                       "Respuesta 1"));
        evaluationQuestions.add(new EvaluationQuestion(2,
                                                       "¿Pregunta 2?",
                                                       "Respuesta 2"));
        evaluationQuestions.add(new EvaluationQuestion(3,
                                                       "¿Pregunta 3?",
                                                       "Respuesta 4"));
        MedicalRecord medicalRecord = new MedicalRecord(1,
                                                        "AB+",
                                                        65,
                                                        1.65,
                                                        evaluationQuestions);
        List<MedicalConsultation> medicalHistory = new ArrayList<>();
        Pacient pacient = new Pacient(3,
                                      928374059,
                                      "Juan Camilo Cardenas Cruz",
                                      3457698,
                                      3003746587L,
                                      "Carrera 1 # 2-3",
                                      "camilocardenas@gmail.com",
                                      "test1",
                                      new Date(),
                                      medicalRecord,
                                      medicalHistory);
        return pacient;
    }

    private Pacient getPacient2() {
        List<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
        evaluationQuestions.add(new EvaluationQuestion(3,
                                                       "¿Pregunta 1?",
                                                       "Respuesta 1"));
        evaluationQuestions.add(new EvaluationQuestion(4,
                                                       "¿Pregunta 2?",
                                                       "Respuesta 2"));
        evaluationQuestions.add(new EvaluationQuestion(5,
                                                       "¿Pregunta 3?",
                                                       "Respuesta 4"));
        MedicalRecord medicalRecord = new MedicalRecord(2,
                                                        "AB+",
                                                        65,
                                                        1.65,
                                                        evaluationQuestions);
        List<MedicalConsultation> medicalHistory = new ArrayList<>();
        Pacient pacient = new Pacient(4,
                                      928374059,
                                      "Sara Esperanza Rodriguez Bravo",
                                      4658293,
                                      3004529384L,
                                      "Carrera 2 # 4-5",
                                      "srodriguez@gmail.com",
                                      "test1",
                                      new Date(),
                                      medicalRecord,
                                      medicalHistory);
        return pacient;
    }
}
