package com.example.hostelhelpbox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ProgressDialog;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference ref;
    private Button Submit;
    private EditText Name;
    private EditText Email;
    private EditText Password;
    private ProgressDialog progress;
    private Spinner Hosteldrop;
    private String Hostel;
    private String UserName;

    public static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        firebaseAuth = FirebaseAuth.getInstance();
        Submit = (Button) findViewById(R.id.submit);
        Name = (EditText) findViewById(R.id.name);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.passw);
        Hosteldrop = (Spinner) findViewById(R.id.hostel);

        //putting values in the dropdown list
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(SignUp.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.hostelNames));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Hosteldrop.setAdapter(myAdapter);

        progress = new ProgressDialog(this);
        Submit.setOnClickListener(this);
    }

    private void RegisterUser() {
        String getName = Name.getText().toString().trim();
        String getEMail = Email.getText().toString().trim();
        String getPassw = Password.getText().toString().trim();
        if(TextUtils.isEmpty(getName)){
            Toast.makeText(this,"Please enter your name",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(getEMail)){
            Toast.makeText(this,"Please enter your Email",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            if(!getEMail.endsWith("@iitg.ac.in"))
            {
                Toast.makeText(getApplicationContext(),"Only IITG members, use IITG email",Toast.LENGTH_LONG).show();
                return;
            }

        }

        if(getEMail.lastIndexOf('@')!=getEMail.indexOf('@'))
        {
            Toast.makeText(getApplicationContext(),"Please enter valid Email Id",Toast.LENGTH_LONG).show();
            return;
        }

        UserName = getEMail.substring(0,getEMail.lastIndexOf('@'));
        if(!isAlphaNumeric(UserName))
        {
            Toast.makeText(getApplicationContext(),"Please enter valid Email Id",Toast.LENGTH_LONG).show();
            return;
        }

        if(Hostel.equals("Select Hostel"))
        {
            Toast.makeText(this,"Please choose your hostel",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(getPassw)){
            Toast.makeText(this,"Please enter your Password",Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setMessage("Signing In ...");
        progress.show();

        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        //to store in the database
        User newUser = new User(getName,getEMail,getPassw,Hostel);
        newUser.setUsertype("normal");
        newUser.setUsername(UserName);
        ref.child(UserName).setValue(newUser);

        //to save info of current user
        UserInfo.fillUserInfo(UserName,getName,getEMail,getPassw,Hostel,newUser.getUsertype());
        Intent intent;
        if(newUser.getUsertype().equals("admin"))
        {
            intent = new Intent(SignUp.this,AdminHome.class);
        }
        else
        {
            intent = new Intent(SignUp.this,home.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v == Submit){
            Hostel = Hosteldrop.getSelectedItem().toString();
            RegisterUser();
        }
    }

}
