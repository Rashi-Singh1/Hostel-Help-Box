package com.example.hostelhelpbox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText Username;
    private EditText Password;
    private Button Submit;
    private Switch switch1;
    private SharedPreferenceConfig sharedPreferenceConfig;
    private Boolean StayLoggedIn;

    private ProgressDialog progressDialog;

    public static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.passw);
        Submit = findViewById(R.id.button);
        progressDialog = new ProgressDialog(this);
        switch1 = findViewById(R.id.switch1);
        StayLoggedIn = false;

        Submit.setOnClickListener(this);
    }

    private void userLogin() {
        final String getUsername = Username.getText().toString().trim();
        final String getPasswd = Password.getText().toString().trim();
        if(TextUtils.isEmpty(getUsername)){
            Toast.makeText(this,"Please enter your Username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(getPasswd)){
            Toast.makeText(this,"Please enter your Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isAlphaNumeric(getUsername))
        {
            Toast.makeText(getApplicationContext(),"Please enter Username",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Logging In ...");
        progressDialog.show();
        DatabaseReference ref;


        ref = FirebaseDatabase.getInstance().getReference("Users/"+getUsername);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User curUser = dataSnapshot.getValue(User.class);
                if(curUser == null)
                {
                    Toast.makeText(getApplicationContext(),"Username doesn't exist",Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                    return;
                }
//                Toast.makeText(getApplicationContext(),"Hey "+ curUser.getFullName(),Toast.LENGTH_SHORT).show();
                if(curUser.getPasswd().equals(getPasswd))
                {
                    UserInfo.fillUserInfo(getUsername,curUser.getFullName(),curUser.getEmail(),getPasswd,curUser.getHostel(),curUser.getUsertype(),curUser.getSecyOf());
                    Intent intent;
                    if(curUser.getUsertype().equals("admin"))
                    {
                        intent = new Intent(Login.this,AdminHome2.class);
                    }
                    else if(curUser.getUsertype().equals("secy"))
                    {
                        intent = new Intent(Login.this,SecyHome2.class);
                    }
                    else {
                        intent = new Intent(Login.this,home2.class);
                    }
                    sharedPreferenceConfig.fillUserInfo_Shared(curUser.getFullName(), curUser.getEmail(), curUser.getPasswd(), curUser.getHostel(), curUser.getUsername(), curUser.getUsertype(),curUser.getSecyOf());
                    if(StayLoggedIn) {
                        sharedPreferenceConfig.writeLoginStatus(true);
                    }
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Password does not match Username",Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == Submit)
        {
            StayLoggedIn = switch1.isChecked();
            userLogin();
        }
    }
}
