package com.parsh.feedback;

public class User {
    private String uid, name, phoneNumber, feedback;

    public User() {

    }

    public User(String uid, String name, String phoneNumber, String feedback) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.feedback = feedback;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

