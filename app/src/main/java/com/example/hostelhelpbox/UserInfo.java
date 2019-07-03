package com.example.hostelhelpbox;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class UserInfo extends AppCompatActivity {
    public static String fullname;
    public static String email;
    public static String passwd;
    public static String hostel;
    public static String username;
    public static String usertype;
    public static String Secyof;
    private static final UserInfo ourInstance = new UserInfo();

    public static UserInfo getInstance() {
        return ourInstance;
    }

    private UserInfo() {
    }

    public static void fillUserInfo(String username,String fullname, String email, String passwd, String hostel,String usertype,String SecyOf) {
        UserInfo.username = username;
        UserInfo.fullname = fullname;
        UserInfo.email = email;
        UserInfo.passwd = passwd;
        UserInfo.hostel = hostel;
        UserInfo.usertype = usertype;
        UserInfo.Secyof = SecyOf;
    }

    public static void logout()
    {
        FirebaseAuth.getInstance().signOut();
        fullname=null;
        username=null;
        email=null;
        passwd=null;
        hostel=null;
        usertype=null;
        Secyof=null;
    }
}
