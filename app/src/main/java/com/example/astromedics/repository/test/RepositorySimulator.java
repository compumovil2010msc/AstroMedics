package com.example.astromedics.repository.test;

import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.EducationalFormation;
import com.example.astromedics.model.EvaluationQuestion;
import com.example.astromedics.model.Localization;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.MedicalRecord;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.Person;
import com.example.astromedics.model.Report;
import com.example.astromedics.model.Therapist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepositorySimulator {
    public int medicalConsultationId = 0, educationalFormationId = 0, appointmentId = 0, evaluationQuestionId = 0, medicalRecordId = 0, reportId = 0;

    private static RepositorySimulator instance;
    public List<Person> persons;

    public static RepositorySimulator getInstance() {
        if (instance == null) {
            instance = new RepositorySimulator();
            instance.init();
        }

        return instance;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return persons;
    }

    private void init() {
        persons = new ArrayList<>();
        Therapist therapist1 = getTestTherapist1();
        Therapist therapist2 = getTestTherapist2();
        Pacient pacient1 = getPacient1();
        Pacient pacient2 = getPacient2();

        MedicalConsultation medicalConsultation1 = new MedicalConsultation(medicalConsultationId++,
                                                                           null,
                                                                           null,
                                                                           new Localization(4.629,
                                                                                            -74.0642,
                                                                                            "Pontificia Universidad Javeriana"),
                                                                           0.0,
                                                                           Therapist.Emphasis.speech_therapy);
        therapist1.getAppointments()
                  .get(0)
                  .setMedicalConsultation(medicalConsultation1);
        medicalConsultation1.setAppointment(therapist1.getAppointments()
                                                      .get(0));
        pacient1.addMedicalHistory(medicalConsultation1);

        MedicalConsultation medicalConsultation2 = new MedicalConsultation(medicalConsultationId++,
                                                                           null,
                                                                           null,
                                                                           new Localization(4.629,
                                                                                            -74.0642,
                                                                                            "Pontificia Universidad Javeriana"),
                                                                           0.0,
                                                                           Therapist.Emphasis.speech_therapy);
        therapist2.getAppointments()
                  .get(0)
                  .setMedicalConsultation(medicalConsultation2);
        medicalConsultation2.setAppointment(therapist2.getAppointments()
                                                      .get(0));
        medicalConsultation2.setReport(new Report(reportId++,
                                                  new Date(),
                                                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam quis turpis non tellus scelerisque congue nec sed felis. Morbi orci ipsum, consequat sit amet dignissim ultricies, lacinia id ante. Nulla lorem sem, auctor fringilla nibh sed, egestas semper enim. Praesent aliquet pretium ex vitae fermentum. Sed malesuada scelerisque varius. Suspendisse semper felis sem, at convallis nisl feugiat non. Cras a erat id turpis faucibus finibus at ac massa. Praesent ultrices augue id fermentum pretium. Nullam dignissim, dui eget sagittis malesuada, dui erat cursus nisl, vel sollicitudin tortor ligula pretium arcu. In ultrices ultrices justo sit amet semper. Donec iaculis ex et lacus ullamcorper efficitur.\n" +
                                                          "\n" +
                                                          "Aliquam cursus nulla eget ante auctor, vel dignissim felis venenatis. Donec congue, dolor eu ornare consectetur, orci arcu consectetur orci, ac imperdiet nisl urna at felis. Duis vestibulum risus sed pretium vehicula. Donec tortor mauris, accumsan ornare pulvinar quis, aliquet ac justo. Ut libero felis, congue vitae porttitor et, ultrices eu turpis. Donec quis ornare ipsum. Nulla facilisi. Phasellus varius condimentum sapien nec tristique. Nullam consectetur enim sed leo cursus imperdiet. Mauris lectus ante, semper eget lacus vitae, tempor placerat eros. Aliquam rhoncus ex ut elit pharetra molestie. Nulla et semper eros, non tristique diam. Cras tincidunt laoreet mi, non elementum nibh interdum non."));
        pacient1.addMedicalHistory(medicalConsultation2);

        persons.add(therapist1);
        persons.add(therapist2);
        persons.add(pacient1);
        persons.add(pacient2);
    }

    private Therapist getTestTherapist1() {
        List<Therapist.Emphasis> emphasis = new ArrayList<>();
        emphasis.add(Therapist.Emphasis.speech_therapy);
        List<EducationalFormation> educationalFormation = new ArrayList<>();
        educationalFormation.add(new EducationalFormation(educationalFormationId++,
                                                          "Fonoaudiologo",
                                                          "Pontificia Universidad Javeriana",
                                                          new ApplicationDateFormat().createDate(2014,
                                                                                                 1,
                                                                                                 1),
                                                          new ApplicationDateFormat().createDate(2018,
                                                                                                 12,
                                                                                                 1),
                                                          true));
        educationalFormation.add(new EducationalFormation(educationalFormationId++,
                                                          "Fisioterapeuta",
                                                          "Pontificia Universidad Javeriana",
                                                          new ApplicationDateFormat().createDate(2014,
                                                                                                 1,
                                                                                                 1),
                                                          new ApplicationDateFormat().createDate(2018,
                                                                                                 12,
                                                                                                 1),
                                                          true));
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(appointmentId++,
                                         new ApplicationDateFormat().createDate(2020,
                                                                                05,
                                                                                10,
                                                                                14,
                                                                                0),
                                         new ApplicationDateFormat().createDate(2020,
                                                                                05,
                                                                                10,
                                                                                16,
                                                                                0),
                                         null));
        appointments.add(new Appointment(appointmentId++,
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                16,
                                                                                0),
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                18,
                                                                                0),
                                         null));
        Therapist therapist = new Therapist(1014276873,
                                            "Carlos Jaime Cobaleda Cruz",
                                            "https://image.shutterstock.com/z/stock-photo-male-doctor-standing-with-folder-isolated-on-white-background-1535685956.jpg",
                                            4101365,
                                            3003619443L,
                                            "Carrera 71D # 55-54",
                                            "terapeuta",
                                            "terapeuta",
                                            new Date(),
                                            35000,
                                            emphasis,
                                            educationalFormation,
                                            appointments);

        return therapist;
    }

    private Therapist getTestTherapist2() {
        List<Therapist.Emphasis> emphasis = new ArrayList<>();
        emphasis.add(Therapist.Emphasis.speech_therapy);
        emphasis.add(Therapist.Emphasis.psychology);
        List<EducationalFormation> educationalFormation = new ArrayList<>();
        educationalFormation.add(new EducationalFormation(educationalFormationId++,
                                                          "Fonoaudiologo",
                                                          "Pontificia Universidad Javeriana",
                                                          new ApplicationDateFormat().createDate(2014,
                                                                                                 1,
                                                                                                 1),
                                                          new ApplicationDateFormat().createDate(2018,
                                                                                                 12,
                                                                                                 1),
                                                          true));
        educationalFormation.add(new EducationalFormation(educationalFormationId++,
                                                          "Fisioterapeuta",
                                                          "Pontificia Universidad Javeriana",
                                                          new ApplicationDateFormat().createDate(2014,
                                                                                                 1,
                                                                                                 1),
                                                          new ApplicationDateFormat().createDate(2018,
                                                                                                 12,
                                                                                                 1),
                                                          true));
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(appointmentId++,
                                         new ApplicationDateFormat().createDate(2020,
                                                                                03,
                                                                                10,
                                                                                10,
                                                                                0),
                                         new ApplicationDateFormat().createDate(2020,
                                                                                03,
                                                                                10,
                                                                                12,
                                                                                0),
                                         null));
        appointments.add(new Appointment(appointmentId++,
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                12,
                                                                                0),
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                14,
                                                                                0),
                                         null));
        appointments.add(new Appointment(appointmentId++,
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                14,
                                                                                0),
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                16,
                                                                                0),
                                         null));
        appointments.add(new Appointment(appointmentId++,
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                11,
                                                                                16,
                                                                                0),
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                11,
                                                                                18,
                                                                                0),
                                         null));
        appointments.add(new Appointment(appointmentId++,
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                12,
                                                                                16,
                                                                                0),
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                12,
                                                                                18,
                                                                                0),
                                         null));
        Therapist therapist = new Therapist(123456789,
                                            "Paula Alejandra Jaime Londoño",
                                            "https://image.shutterstock.com/z/stock-photo-confident-female-doctor-sitting-at-office-desk-and-smiling-at-camera-health-care-and-prevention-292534811.jpg",
                                            4101365,
                                            3003619443L,
                                            "Carrera 71D # 55-54",
                                            "pjaime@gmail.com",
                                            "test1",
                                            new Date(),
                                            40000,
                                            emphasis,
                                            educationalFormation,
                                            appointments);

        return therapist;
    }

    private Pacient getPacient1() {
        List<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
        evaluationQuestions.add(new EvaluationQuestion(evaluationQuestionId++,
                                                       "¿Pregunta 1?",
                                                       "Respuesta 1"));
        evaluationQuestions.add(new EvaluationQuestion(evaluationQuestionId++,
                                                       "¿Pregunta 2?",
                                                       "Respuesta 2"));
        evaluationQuestions.add(new EvaluationQuestion(evaluationQuestionId++,
                                                       "¿Pregunta 3?",
                                                       "Respuesta 4"));
        MedicalRecord medicalRecord = new MedicalRecord(medicalRecordId++,
                                                        "AB+",
                                                        65,
                                                        1.65,
                                                        evaluationQuestions);
        List<MedicalConsultation> medicalHistory = new ArrayList<>();
        Pacient pacient = new Pacient(928374059,
                                      "Juan Camilo Cardenas Cruz",
                                      "https://image.shutterstock.com/z/stock-photo-friendly-looking-positive-afro-american-stylish-man-with-funky-hair-and-bristle-smiling-broadly-640011877.jpg",
                                      3457698,
                                      3003746587L,
                                      "Carrera 1 # 2-3",
                                      "paciente",
                                      "paciente",
                                      new Date(),
                                      medicalRecord,
                                      medicalHistory);
        return pacient;
    }

    private Pacient getPacient2() {
        List<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
        evaluationQuestions.add(new EvaluationQuestion(evaluationQuestionId++,
                                                       "¿Pregunta 1?",
                                                       "Respuesta 1"));
        evaluationQuestions.add(new EvaluationQuestion(evaluationQuestionId++,
                                                       "¿Pregunta 2?",
                                                       "Respuesta 2"));
        evaluationQuestions.add(new EvaluationQuestion(evaluationQuestionId++,
                                                       "¿Pregunta 3?",
                                                       "Respuesta 4"));
        MedicalRecord medicalRecord = new MedicalRecord(medicalRecordId++,
                                                        "AB+",
                                                        65,
                                                        1.65,
                                                        evaluationQuestions);
        List<MedicalConsultation> medicalHistory = new ArrayList<>();
        Pacient pacient = new Pacient(928374059,
                                      "Sara Esperanza Rodriguez Bravo",
                                      "https://image.shutterstock.com/image-photo/portrait-young-beautiful-cute-cheerful-600w-666258808.jpg",
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
