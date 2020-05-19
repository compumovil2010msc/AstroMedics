package com.example.astromedics.model;

import java.io.Serializable;

public class EvaluationQuestion implements Serializable {
    private int evaluationQuestionId;
    private String question;
    private String answer;

    public EvaluationQuestion(){

    }

    public EvaluationQuestion(int evaluationQuestionId, String question, String answer) {
        this.evaluationQuestionId = evaluationQuestionId;
        this.question = question;
        this.answer = answer;
    }

    public int getEvaluationQuestionId() {
        return evaluationQuestionId;
    }

    public void setEvaluationQuestionId(int evaluationQuestionId) {
        this.evaluationQuestionId = evaluationQuestionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
