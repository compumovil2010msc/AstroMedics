package com.example.astromedics.repository.test;

import android.util.Log;

import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.helpers.FileHandler;
import com.example.astromedics.helpers.JsonHandler;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.EducationalFormation;
import com.example.astromedics.model.EvaluationQuestion;
import com.example.astromedics.model.Evolution;
import com.example.astromedics.model.Localization;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.MedicalRecord;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.Person;
import com.example.astromedics.model.Report;
import com.example.astromedics.model.Therapist;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RepositorySimulator {
    /*
    Credentials on firebase:

    pjaime@test.com -> test1234
    ccobaleda@test.com -> test1234
    jcardenas@test.com -> test1234
    rodriguez@test.com -> test1234
     */
    public static IdHelper idHelper;

    public static String pacientFilePath = "/sdcard/repositoryPacient.json", therapistFilePath = "/sdcard/repositoryTherapist.json", idFilePath = "/sdcard/repositoryId.json";
    private static RepositorySimulator instance;
    private List<Person> persons;

    public static RepositorySimulator getInstance() {
        if (instance == null) {
            instance = new RepositorySimulator();
            instance.init();
        }

        return instance;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
        saveCurrentUsers(persons);
    }

    public List<Person> getPersons() {
        if (this.persons == null || this.persons.size() == 0) {
            this.persons = getPersonsFromStorage();
        }

        return this.persons;
    }

    private ArrayList<Person> getPersonsFromStorage() {
        ArrayList<Person> returnable = new ArrayList<>();
        try {
            ArrayList<Pacient> pacients = new ArrayList<>(Arrays.asList(new ObjectMapper().readValue(FileHandler.getInstance(null)
                                                                                                                .readFile(pacientFilePath),
                                                                                                     Pacient[].class)));
            ArrayList<Therapist> therapists = new ArrayList<>(Arrays.asList(new ObjectMapper().readValue(FileHandler.getInstance(null)
                                                                                                                    .readFile(therapistFilePath),
                                                                                                         Therapist[].class)));
            IdHelper currentIdHelper = new ObjectMapper().readValue(FileHandler.getInstance(null)
                                                                               .readFile(idFilePath),
                                                                    IdHelper.class);
            idHelper = currentIdHelper;
            for (Pacient pacient : pacients) {
                for (MedicalConsultation medicalConsultation : pacient.getMedicalHistory()) {
                    for (Therapist therapist : therapists) {
                        for (Appointment appointment : therapist.getAppointments()) {
                            if (medicalConsultation.getAppointmentId() == appointment.getAppointmentId()) {
                                medicalConsultation.setAppointment(appointment);
                                appointment.setMedicalConsultation(medicalConsultation);
                            }
                        }
                    }
                }
            }

            returnable.addAll(pacients);
            returnable.addAll(therapists);
        } catch (Exception ex) {
            Log.e("Error",
                  "No se pudo obtener usuarios");
        }
        return returnable == null ? new ArrayList<Person>() : returnable;
    }

    private void saveCurrentUsers(List<Person> persons) {
        ArrayList<Person> pacients = new ArrayList<Person>(persons);
        pacients.removeIf(s -> !(s instanceof Pacient));
        ArrayList<Person> therapists = new ArrayList<Person>(persons);
        therapists.removeIf(s -> !(s instanceof Therapist));

        if (FileHandler.getInstance(null)
                       .writeFile(pacientFilePath,
                                  JsonHandler.toJson(pacients))) {
            Log.i("Info",
                  "Se guardaron los pacientes");
        }

        if (FileHandler.getInstance(null)
                       .writeFile(therapistFilePath,
                                  JsonHandler.toJson(therapists))) {
            Log.i("Info",
                  "Se guardaron los terapeutas");
        }

        if (FileHandler.getInstance(null)
                       .writeFile(idFilePath,
                                  JsonHandler.toJson(idHelper))) {
            Log.i("Info",
                  "Se guardaron los ids");
        }
    }

    private void init() {
        if (RepositorySimulator.getInstance()
                               .getPersons()
                               .size() > 0) {
            return;
        }

        idHelper = new IdHelper();
        List<Person> initialPersons = new ArrayList<>();
        Therapist therapist1 = getTestTherapist1();
        Therapist therapist2 = getTestTherapist2();
        Pacient pacient1 = getPacient1();
        Pacient pacient2 = getPacient2();

        MedicalConsultation medicalConsultation1 = new MedicalConsultation(idHelper.medicalConsultationId++,
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
        medicalConsultation1.setAppointmentId(therapist1.getAppointments()
                                                        .get(0)
                                                        .getAppointmentId());
        pacient1.addMedicalHistory(medicalConsultation1);

        MedicalConsultation medicalConsultation2 = new MedicalConsultation(idHelper.medicalConsultationId++,
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
        medicalConsultation2.setAppointmentId(therapist2.getAppointments()
                                                        .get(0)
                                                        .getAppointmentId());

        medicalConsultation2.setReport(new Report(idHelper.reportId++,
                                                  new Date(),
                                                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam quis turpis non tellus scelerisque congue nec sed felis. Morbi orci ipsum, consequat sit amet dignissim ultricies, lacinia id ante. Nulla lorem sem, auctor fringilla nibh sed, egestas semper enim. Praesent aliquet pretium ex vitae fermentum. Sed malesuada scelerisque varius. Suspendisse semper felis sem, at convallis nisl feugiat non. Cras a erat id turpis faucibus finibus at ac massa. Praesent ultrices augue id fermentum pretium. Nullam dignissim, dui eget sagittis malesuada, dui erat cursus nisl, vel sollicitudin tortor ligula pretium arcu. In ultrices ultrices justo sit amet semper. Donec iaculis ex et lacus ullamcorper efficitur.\n" +
                                                          "\n" +
                                                          "Aliquam cursus nulla eget ante auctor, vel dignissim felis venenatis. Donec congue, dolor eu ornare consectetur, orci arcu consectetur orci, ac imperdiet nisl urna at felis. Duis vestibulum risus sed pretium vehicula. Donec tortor mauris, accumsan ornare pulvinar quis, aliquet ac justo. Ut libero felis, congue vitae porttitor et, ultrices eu turpis. Donec quis ornare ipsum. Nulla facilisi. Phasellus varius condimentum sapien nec tristique. Nullam consectetur enim sed leo cursus imperdiet. Mauris lectus ante, semper eget lacus vitae, tempor placerat eros. Aliquam rhoncus ex ut elit pharetra molestie. Nulla et semper eros, non tristique diam. Cras tincidunt laoreet mi, non elementum nibh interdum non."));
        medicalConsultation2.setEvolution(new Evolution(idHelper.evolutionId++,
                                                        new Date(),
                                                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam quis turpis non tellus scelerisque congue nec sed felis. Morbi orci ipsum, consequat sit amet dignissim ultricies, lacinia id ante. Nulla lorem sem, auctor fringilla nibh sed, egestas semper enim. Praesent aliquet pretium ex vitae fermentum. Sed malesuada scelerisque varius. Suspendisse semper felis sem, at convallis nisl feugiat non. Cras a erat id turpis faucibus finibus at ac massa. Praesent ultrices augue id fermentum pretium. Nullam dignissim, dui eget sagittis malesuada, dui erat cursus nisl, vel sollicitudin tortor ligula pretium arcu. In ultrices ultrices justo sit amet semper. Donec iaculis ex et lacus ullamcorper efficitur.\n" +
                                                                "\n" +
                                                                "Aliquam cursus nulla eget ante auctor, vel dignissim felis venenatis. Donec congue, dolor eu ornare consectetur, orci arcu consectetur orci, ac imperdiet nisl urna at felis. Duis vestibulum risus sed pretium vehicula. Donec tortor mauris, accumsan ornare pulvinar quis, aliquet ac justo. Ut libero felis, congue vitae porttitor et, ultrices eu turpis. Donec quis ornare ipsum. Nulla facilisi. Phasellus varius condimentum sapien nec tristique. Nullam consectetur enim sed leo cursus imperdiet. Mauris lectus ante, semper eget lacus vitae, tempor placerat eros. Aliquam rhoncus ex ut elit pharetra molestie. Nulla et semper eros, non tristique diam. Cras tincidunt laoreet mi, non elementum nibh interdum non."));

        pacient1.addMedicalHistory(medicalConsultation2);

        initialPersons.add(therapist1);
        initialPersons.add(therapist2);
        initialPersons.add(pacient1);
        initialPersons.add(pacient2);

        this.setPersons(initialPersons);
    }

    private Therapist getTestTherapist1() {
        List<Therapist.Emphasis> emphasis = new ArrayList<>();
        emphasis.add(Therapist.Emphasis.speech_therapy);
        List<EducationalFormation> educationalFormation = new ArrayList<>();
        educationalFormation.add(new EducationalFormation(idHelper.educationalFormationId++,
                                                          "Fonoaudiologo",
                                                          "Pontificia Universidad Javeriana",
                                                          new ApplicationDateFormat().createDate(2014,
                                                                                                 1,
                                                                                                 1),
                                                          new ApplicationDateFormat().createDate(2018,
                                                                                                 12,
                                                                                                 1),
                                                          true));
        educationalFormation.add(new EducationalFormation(idHelper.educationalFormationId++,
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
        appointments.add(new Appointment(idHelper.appointmentId++,
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
        appointments.add(new Appointment(idHelper.appointmentId++,
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
                                            "ccobaleda@test.com",
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
        educationalFormation.add(new EducationalFormation(idHelper.educationalFormationId++,
                                                          "Fonoaudiologo",
                                                          "Pontificia Universidad Javeriana",
                                                          new ApplicationDateFormat().createDate(2014,
                                                                                                 1,
                                                                                                 1),
                                                          new ApplicationDateFormat().createDate(2018,
                                                                                                 12,
                                                                                                 1),
                                                          true));
        educationalFormation.add(new EducationalFormation(idHelper.educationalFormationId++,
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
        appointments.add(new Appointment(idHelper.appointmentId++,
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
        appointments.add(new Appointment(idHelper.appointmentId++,
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
        appointments.add(new Appointment(idHelper.appointmentId++,
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
        appointments.add(new Appointment(idHelper.appointmentId++,
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
        appointments.add(new Appointment(idHelper.appointmentId++,
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
                                            "pjaime@test.com",
                                            new Date(),
                                            40000,
                                            emphasis,
                                            educationalFormation,
                                            appointments);

        return therapist;
    }

    private Pacient getPacient1() {
        List<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
        evaluationQuestions.add(new EvaluationQuestion(idHelper.evaluationQuestionId++,
                                                       "¿Pregunta 1?",
                                                       "Respuesta 1"));
        evaluationQuestions.add(new EvaluationQuestion(idHelper.evaluationQuestionId++,
                                                       "¿Pregunta 2?",
                                                       "Respuesta 2"));
        evaluationQuestions.add(new EvaluationQuestion(idHelper.evaluationQuestionId++,
                                                       "¿Pregunta 3?",
                                                       "Respuesta 4"));
        MedicalRecord medicalRecord = new MedicalRecord(idHelper.medicalRecordId++,
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
                                      "jcardenas@test.com",
                                      new Date(),
                                      medicalRecord,
                                      medicalHistory);
        return pacient;
    }

    private Pacient getPacient2() {
        List<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
        evaluationQuestions.add(new EvaluationQuestion(idHelper.evaluationQuestionId++,
                                                       "¿Pregunta 1?",
                                                       "Respuesta 1"));
        evaluationQuestions.add(new EvaluationQuestion(idHelper.evaluationQuestionId++,
                                                       "¿Pregunta 2?",
                                                       "Respuesta 2"));
        evaluationQuestions.add(new EvaluationQuestion(idHelper.evaluationQuestionId++,
                                                       "¿Pregunta 3?",
                                                       "Respuesta 4"));
        MedicalRecord medicalRecord = new MedicalRecord(idHelper.medicalRecordId++,
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
                                      "rodriguez@test.com",
                                      new Date(),
                                      medicalRecord,
                                      medicalHistory);
        return pacient;
    }

    public static class IdHelper {
        public int medicalConsultationId, educationalFormationId, appointmentId, evaluationQuestionId, medicalRecordId, reportId, evolutionId;

        public IdHelper() {
            this.medicalConsultationId = 0;
            this.educationalFormationId = 0;
            this.appointmentId = 0;
            this.evaluationQuestionId = 0;
            this.medicalRecordId = 0;
            this.reportId = 0;
            this.evolutionId = 0;
        }

        public int getMedicalConsultationId() {
            return medicalConsultationId;
        }

        public void setMedicalConsultationId(int medicalConsultationId) {
            this.medicalConsultationId = medicalConsultationId;
        }

        public int getEducationalFormationId() {
            return educationalFormationId;
        }

        public void setEducationalFormationId(int educationalFormationId) {
            this.educationalFormationId = educationalFormationId;
        }

        public int getAppointmentId() {
            return appointmentId;
        }

        public void setAppointmentId(int appointmentId) {
            this.appointmentId = appointmentId;
        }

        public int getEvaluationQuestionId() {
            return evaluationQuestionId;
        }

        public void setEvaluationQuestionId(int evaluationQuestionId) {
            this.evaluationQuestionId = evaluationQuestionId;
        }

        public int getMedicalRecordId() {
            return medicalRecordId;
        }

        public void setMedicalRecordId(int medicalRecordId) {
            this.medicalRecordId = medicalRecordId;
        }

        public int getReportId() {
            return reportId;
        }

        public void setReportId(int reportId) {
            this.reportId = reportId;
        }

        public int getEvolutionId() {
            return evolutionId;
        }

        public void setEvolutionId(int evolutionId) {
            this.evolutionId = evolutionId;
        }
    }
}
