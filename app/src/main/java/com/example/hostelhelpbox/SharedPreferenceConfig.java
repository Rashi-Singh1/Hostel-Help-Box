package com.example.hostelhelpbox;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfig(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preference),Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_preference),status);
        editor.commit();
    }

     public boolean readLoginStatus()
     {
         boolean status = false;
         status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preference),false);
         return status;
     }

     public void fillUserInfo_Shared(String fullName, String email, String passw, String hostel, String username, String usertype, String secyof)
     {
         SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.putString(context.getResources().getString(R.string.fullName),fullName);
         editor.putString(context.getResources().getString(R.string.email),email);
         editor.putString(context.getResources().getString(R.string.passw),passw);
         editor.putString(context.getResources().getString(R.string.hostel),hostel);
         editor.putString(context.getResources().getString(R.string.username),username);
         editor.putString(context.getResources().getString(R.string.usertype),usertype);
         editor.putString(context.getResources().getString(R.string.secyof),secyof);
         editor.commit();
     }

    public String readfullName()
    {
        String name = null;
        name = sharedPreferences.getString(context.getResources().getString(R.string.fullName),null);
        return name;
    }

    public String reademail()
    {
        String name = null;
        name = sharedPreferences.getString(context.getResources().getString(R.string.email),null);
        return name;
    }

    public String readpassw()
    {
        String name = null;
        name = sharedPreferences.getString(context.getResources().getString(R.string.passw),null);
        return name;
    }

    public String readhostel()
    {
        String name = null;
        name = sharedPreferences.getString(context.getResources().getString(R.string.hostel),null);
        return name;
    }

    public String readusername()
    {
        String name = null;
        name = sharedPreferences.getString(context.getResources().getString(R.string.username),null);
        return name;
    }

    public String readusertype()
    {
        String name = null;
        name = sharedPreferences.getString(context.getResources().getString(R.string.usertype),null);
        return name;
    }

    public String readsecyof()
    {
        String name = null;
        name = sharedPreferences.getString(context.getResources().getString(R.string.secyof),null);
        return name;
    }
}
