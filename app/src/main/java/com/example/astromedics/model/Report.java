package com.example.astromedics.model;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
    private int reportId;
    private Date creationDate;
    private String content;

    public Report(int reportId, Date creationDate, String content) {
        this.reportId = reportId;
        this.creationDate = creationDate;
        this.content = content;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
