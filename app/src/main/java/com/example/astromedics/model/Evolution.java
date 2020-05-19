package com.example.astromedics.model;

import java.io.Serializable;
import java.util.Date;

public class Evolution implements Serializable {
    private int evolutionId;
    private Date creationDate;
    private String content;

    public Evolution(){

    }

    public Evolution(int evolutionId, Date creationDate, String content) {
        this.evolutionId = evolutionId;
        this.creationDate = creationDate;
        this.content = content;
    }

    public int getEvolutionId() {
        return evolutionId;
    }

    public void setEvolutionId(int evolutionId) {
        this.evolutionId = evolutionId;
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
