package com.example.hostelhelpbox;

import java.util.ArrayList;

public class Thread {
    private String key;
    private String secy;
    private String creator;
    private String theme;       //like maintenance
//    private ArrayList<User> likes;
    private String date;
    private String time;
    private boolean imp;
    private boolean solved;
    private boolean acknowledged;
    private double rating;
//    private ArrayList<Comment> comments;
    private Integer likeCount;
    private Integer commentCount;
    private String subject;
    private String body;
    private String hostel;
    private String expectedTime;
    private ArrayList<User> cc;
    //attachment

    public Thread(){

    }

    public Thread(String secy, String creator, String theme, String subject, String body, String date, String time,boolean imp,String hostel) {
        this.secy = secy;
        this.creator = creator;
        this.theme = theme;
        this.subject = subject;
        this.body = body;
        this.date = date;
        this.time = time;
        this.imp = imp;
        this.solved = false;
        this.acknowledged = false;
        this.rating = 0;
        this.hostel = hostel;
        this.likeCount = 0;
        this.commentCount = 0;
        this.expectedTime = "0";
    }


    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String gettheme() {
        return theme;
    }

    public void settheme(String topic) {
        this.theme = topic;
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

    public String getsubject() {
        return subject;
    }

    public void setsubject(String subject) {
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

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}
