package com.example.hostelhelpbox;

public class User {
    private String fullName;
    private String email;
    private String passwd;
    private String hostel;
    private String username;
    private String usertype;
    private String SecyOf;

    public String getSecyOf() {
        return SecyOf;
    }

    public void setSecyOf(String secyOf) {
        SecyOf = secyOf;
    }

    public User() {}
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getHostel() {
        return hostel;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public User(String fullName, String email, String passwd, String hostel,String SecyOf) {
        this.fullName = fullName;
        this.email = email;
        this.passwd = passwd;
        this.hostel = hostel;
        this.SecyOf = SecyOf;
    }
}
