package com.example.hostelhelpbox;

public class WashingMachine {
    private String key;
    private boolean busy;
    private String block;
    private String floor;
    private String numb;
    private String user;
    private String freetime;
    private String book;
    private Integer contact;

    public WashingMachine()
    {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBlock() {
        return block;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getNumb() {
        return numb;
    }

    public void setNumb(String numb) {
        this.numb = numb;
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

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public WashingMachine(String block, String floor, String numb) {
        this.block = block;
        this.floor = floor;
        this.numb = numb;
        this.user = "";
        this.freetime = "";
        this.book = "";
        this.contact = 0;
        this.busy = false;
    }
}
