package com.example.hostelhelpbox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//public class FragmentAddSecy extends Fragment implements View.OnClickListener {
public class FragmentAddSecy extends Fragment {
    public static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        Toast.makeText(getContext(), "Add Decy", Toast.LENGTH_SHORT).show();
        View RootView = inflater.inflate(R.layout.fragment_add_secy, container, false);

        SharedPreferenceConfig sharedPreferenceConfig;

        Button Submit;
        EditText Name;
        EditText Email;
        EditText Password;
        Spinner Hosteldrop;
        Spinner DesignDrop;

        sharedPreferenceConfig = new SharedPreferenceConfig(getContext());

//        firebaseAuth = FirebaseAuth.getInstance();
        Submit = (Button) RootView.findViewById(R.id.submit);
        Name = (EditText) RootView.findViewById(R.id.name);
        Email = (EditText) RootView.findViewById(R.id.email);
        Password = (EditText) RootView.findViewById(R.id.passw);
        Hosteldrop = (Spinner) RootView.findViewById(R.id.hostel);
        DesignDrop = (Spinner) RootView.findViewById(R.id.designation);

//        //putting values in the dropdown list
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.hostelNames));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Hosteldrop.setAdapter(myAdapter);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.secyNames));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DesignDrop.setAdapter(myAdapter2);

        setOnClick(Submit,sharedPreferenceConfig,Name,Email,Password,Hosteldrop, DesignDrop,Submit);
        return RootView;
//        return inflater.inflate(R.layout.fragment_add_secy, container, false);
    }

    private void RegisterUser(SharedPreferenceConfig sharedPreferenceConfig,EditText Name, EditText Email, EditText Password, String Hostel, String Designation) {
        String UserName;
        String getName = Name.getText().toString().trim();
        String getEMail = Email.getText().toString().trim();
        String getPassw = Password.getText().toString().trim();
        DatabaseReference ref;
        ProgressDialog progress;
        progress = new ProgressDialog(getContext());
        if (TextUtils.isEmpty(getName)) {
            Toast.makeText(getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(getEMail)) {
            Toast.makeText(getContext(), "Please enter your Email", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!getEMail.endsWith("@iitg.ac.in")) {
                Toast.makeText(getContext(), "Only IITG members, use IITG email", Toast.LENGTH_LONG).show();
                return;
            }

        }

        if (getEMail.lastIndexOf('@') != getEMail.indexOf('@')) {
            Toast.makeText(getContext(), "Please enter valid Email Id", Toast.LENGTH_LONG).show();
            return;
        }

        UserName = getEMail.substring(0, getEMail.lastIndexOf('@'));
        if (!isAlphaNumeric(UserName)) {
            Toast.makeText(getContext(), "Please enter valid Email Id", Toast.LENGTH_LONG).show();
            return;
        }

        if (Hostel.equals("Select Hostel")) {
            Toast.makeText(getContext(), "Please choose your hostel", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(getPassw)) {
            Toast.makeText(getContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setMessage("Registering New Secy ...");
        progress.show();

        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        //to store in the database
        User newUser = new User(getName, getEMail, getPassw, Hostel,Designation);
        newUser.setUsertype("secy");
        newUser.setUsername(UserName);
        ref.child(UserName).setValue(newUser);
        progress.hide();
        Name.setText("");
        Email.setText("");
        Password.setText("");
    }
    private void setOnClick(final Button btn,final SharedPreferenceConfig sharedPreferenceConfig, final EditText name, final EditText Email, final EditText Password, final Spinner Hosteldrop,final Spinner DesignDrop, final Button Submit){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == Submit) {
                    String Hostel;
                    String Designation;
                    Hostel = Hosteldrop.getSelectedItem().toString();
                    Designation = DesignDrop.getSelectedItem().toString();
                    RegisterUser(sharedPreferenceConfig,name,Email,Password,Hostel,Designation);
                    Hosteldrop.setSelection(0);
                    Toast.makeText(getContext(),"New Secy Registered",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}