package com.example.hostelhelpbox;

import java.util.ArrayList;

public class Thread {
    private String key;
    private String secy;
    private String creator;
    private String theme;       //like maintenance
    private ArrayList<User> likes;
    private boolean imp;
    private boolean solved;
    private boolean acknowledged;
    private double rating;
    private ArrayList<Comment> comments;
    private String subject;
    private String body;
    private ArrayList<User> cc;
    //attachment

    public Thread(){}
    public Thread(String secy, String creator, String theme, String subject, String body, ArrayList<User> cc) {
        this.secy = secy;
        this.creator = creator;
        this.theme = theme;
        this.subject = subject;
        this.body = body;
        this.cc = cc;
    }

    public String getSecy() {
        return secy;
    }

    public void setSecy(String secy) {
        this.secy = secy;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String topic) {
        this.theme = topic;
    }

    public ArrayList<User> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }

    public boolean isImp() {
        return imp;
    }

    public void setImp(boolean imp) {
        this.imp = imp;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArrayList<User> getCc() {
        return cc;
    }

    public void setCc(ArrayList<User> cc) {
        this.cc = cc;
    }
}
