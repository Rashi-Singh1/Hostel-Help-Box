package com.example.hostelhelpbox;

import java.util.ArrayList;

public class Comment {
    private String key;
    private ArrayList<User> likes;
    private String replyTo;
    private boolean pinned;
    private String subject;

    public Comment(){}

    public Comment(String replyTo, boolean pinned, String subject, String body) {
        this.replyTo = replyTo;
        this.pinned = pinned;
        this.subject = subject;
        this.body = body;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<User> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
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

    private String body;
    //think about tagging facility
    //attachment
}
