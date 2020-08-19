package com.nsmk.thesis.medaid.model;

public class Symptom {
private String symptomName;
    private boolean isChecked;

    public Symptom(String symptomName, boolean isChecked) {
        this.symptomName = symptomName;
        this.isChecked = isChecked;
    }

    public Symptom() {
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
