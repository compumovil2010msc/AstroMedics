package com.example.astromedics.model;

import java.util.Date;

public class EducationalFormation {
    private int EducationalFormationId;
    private String title;
    private String institution;
    private Date startDate;
    private Date enDate;
    private boolean graduated;

    public EducationalFormation(int educationalFormationId, String title, String institution, Date startDate, Date enDate, boolean graduated) {
        EducationalFormationId = educationalFormationId;
        this.title = title;
        this.institution = institution;
        this.startDate = startDate;
        this.enDate = enDate;
        this.graduated = graduated;
    }

    public int getEducationalFormationId() {
        return EducationalFormationId;
    }

    public void setEducationalFormationId(int educationalFormationId) {
        EducationalFormationId = educationalFormationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEnDate() {
        return enDate;
    }

    public void setEnDate(Date enDate) {
        this.enDate = enDate;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public void setGraduated(boolean graduated) {
        this.graduated = graduated;
    }

}
