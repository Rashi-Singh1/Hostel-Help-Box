package com.example.hostelhelpbox;

public class Book {
    private String key;
    private boolean busy;
    private String title;
    private String author;
    private String edition;
    private String user;
    private String freetime;
    private String bookForUse;
    private Integer contact;
    private Integer qty;

    public Book()
    {

    }

    public Book(String title, String author, String edition, Integer qty) {
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.qty = qty;
        this.busy = false;
        this.user = "";
        this.freetime = "";
        this.bookForUse = "";
        this.contact = 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFreetime() {
        return freetime;
    }

    public void setFreetime(String freetime) {
        this.freetime = freetime;
    }

    public String getBookForUse() {
        return bookForUse;
    }

    public void setBookForUse(String bookForUse) {
        this.bookForUse = bookForUse;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

}
