package com.example.covidsounds.dto;

import java.util.List;

/**
 * @author Abhishek
 */
public class DemographicDTO {
    private String gender;
    private String ageGroup;
    private List<String> respiratoryMedicalConditions;
    private List<String> cardiovascularMedicalConditions;
    private List<String> otherMedicalConditions;
    private String smokingHabit;
    private List<String> symptoms;
    private String testStatus;
    private String everHadCovid;
    private String inHospital;
    private String coughFilePath;
    private String breathFilePath;
    private String readSentenceFilePath;

    public String getCoughFilePath() {
        return coughFilePath;
    }

    public void setCoughFilePath(String coughFilePath) {
        this.coughFilePath = coughFilePath;
    }

    public String getBreathFilePath() {
        return breathFilePath;
    }

    public void setBreathFilePath(String breathFilePath) {
        this.breathFilePath = breathFilePath;
    }

    public String getReadSentenceFilePath() {
        return readSentenceFilePath;
    }

    public void setReadSentenceFilePath(String readSentenceFilePath) {
        this.readSentenceFilePath = readSentenceFilePath;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public List<String> getRespiratoryMedicalConditions() {
        return respiratoryMedicalConditions;
    }

    public void setRespiratoryMedicalConditions(List<String> respiratoryMedicalConditions) {
        this.respiratoryMedicalConditions = respiratoryMedicalConditions;
    }

    public List<String> getCardiovascularMedicalConditions() {
        return cardiovascularMedicalConditions;
    }

    public void setCardiovascularMedicalConditions(List<String> cardiovascularMedicalConditions) {
        this.cardiovascularMedicalConditions = cardiovascularMedicalConditions;
    }

    public List<String> getOtherMedicalConditions() {
        return otherMedicalConditions;
    }

    public void setOtherMedicalConditions(List<String> otherMedicalConditions) {
        this.otherMedicalConditions = otherMedicalConditions;
    }

    public String getSmokingHabit() {
        return smokingHabit;
    }

    public void setSmokingHabit(String smokingHabit) {
        this.smokingHabit = smokingHabit;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public String getEverHadCovid() {
        return everHadCovid;
    }

    public void setEverHadCovid(String everHadCovid) {
        this.everHadCovid = everHadCovid;
    }

    public String getInHospital() {
        return inHospital;
    }

    public void setInHospital(String inHospital) {
        this.inHospital = inHospital;
    }
}

