package com.example.hostelhelpbox;

import java.util.ArrayList;

public class Comment {
    private String key;
    private Integer likeCount;
    private String replyTo;
    private boolean pinned;
    private String subject;
    private String body;
    //how to tag people
    public Comment(){}

    public Comment(String replyTo, String subject, String body) {
        this.replyTo = replyTo;
        this.pinned = false;
        this.subject = subject;
        this.body = body;
        this.likeCount = 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
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
//think about tagging facility
    //attachment
}
