package com.example.astromedics.model;

import java.io.Serializable;

public class Report implements Serializable {
    private int reportId;

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }
}
