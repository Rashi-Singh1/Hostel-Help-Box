package com.example.hostelhelpbox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelhelpbox.Interface.IFirebaseLoadDone;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddThread extends AppCompatActivity implements IFirebaseLoadDone {

    String theme;
    EditText subject,body;
    SearchableSpinner secy;
    Button submit;
    Switch urgent;
    DatabaseReference ref;
    IFirebaseLoadDone iFirebaseLoadDone;
    SharedPreferenceConfig sharedPreferenceConfig;
    ArrayList<User> list;
    TextView mytheme;
    Switch switch2;
    boolean found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thread);
        getIncomingIntent();
        secy = findViewById(R.id.secy);
        subject = findViewById(R.id.subject);
        submit = findViewById(R.id.submit);
        body = findViewById(R.id.body);
        urgent = findViewById(R.id.urgent);
        mytheme = findViewById(R.id.theme);
        switch2 = findViewById(R.id.switch2);
        list = new ArrayList<>();
        found = false;
        sharedPreferenceConfig = new SharedPreferenceConfig(AddThread.this);
        iFirebaseLoadDone = this;
        mytheme.setText(theme);

        ref = FirebaseDatabase.getInstance().getReference("Users/"+sharedPreferenceConfig.readusername());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User p = dataSnapshot.getValue(User.class);
                String myHostel = p.getHostel();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddThread.this,"No Data",Toast.LENGTH_SHORT).show();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    User p = dataSnapshot1.getValue(User.class);
                    if(switch2.isChecked())
                    {
                        if(p.getUsertype().equals("secy"))
                        {
                            list.add(p);
                        }
                    }
                    else{
                        if(p!=null && p.getHostel().equals(sharedPreferenceConfig.readhostel()) && p.getUsertype().equals("secy"))
                        {
                            list.add(p);
                        }
                    }
                }
                iFirebaseLoadDone.onFirebaseLoadSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddThread.this,"No Data",Toast.LENGTH_SHORT).show();
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ref = FirebaseDatabase.getInstance().getReference("Users");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                        {
                            User p = dataSnapshot1.getValue(User.class);
                            if(switch2.isChecked())
                            {
                                if(p.getUsertype().equals("secy"))
                                {
                                    list.add(p);
                                }
                            }
                            else{
                                if(p!=null && p.getHostel().equals(sharedPreferenceConfig.readhostel()) && p.getUsertype().equals("secy"))
                                {
                                    list.add(p);
                                }
                            }
                        }
                        iFirebaseLoadDone.onFirebaseLoadSuccess(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AddThread.this,"No Data",Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addThread();
            }
        });
    }

    void addThread()
    {
        String getsecy = secy.getSelectedItem().toString().trim();
        String getsubject = subject.getText().toString().trim();
        String getbody = body.getText().toString().trim();
        boolean isurgent = urgent.isChecked();
        if(getsecy.isEmpty() || getsecy.equals("Select authority"))
        {
            Toast.makeText(AddThread.this,"Please enter authority name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(getsubject.isEmpty())
        {
            Toast.makeText(AddThread.this,"Subject can not be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(getbody.isEmpty())
        {
            Toast.makeText(AddThread.this,"Body of the thread can not be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        ProgressDialog progress;
        progress = new ProgressDialog(this);
        progress.setMessage("Adding New Thread ...");
        progress.show();

        ref = FirebaseDatabase.getInstance().getReference().child("Threads/"+theme);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String timee = format.format(calendar.getTime());
        Thread newThread = new Thread(getsecy, sharedPreferenceConfig.readusername(), theme, getsubject,getbody,currentDate,timee,isurgent,sharedPreferenceConfig.readhostel());
        String key = ref.push().getKey();
        newThread.setKey(key);
        ref.child(key).setValue(newThread);
        subject.setText("");
        body.setText("");
        urgent.setChecked(false);
        if(found == false) secy.setSelection(0);
        progress.hide();
    }

    private void getIncomingIntent()
    {
        if(getIntent().hasExtra("theme"))
        {
            theme = getIntent().getStringExtra("theme");
        }
    }

    @Override
    public void onFirebaseLoadSuccess(List<User> users) {
        List<String> name = new ArrayList<>();
        name.add("Select authority");
        int index = -1;
        if(list.size()!=0)
        {
            for(int i = 0;i<list.size();i++)
            {
                name.add(list.get(i).getHostel()+" "+list.get(i).getSecyOf()+" \n"+list.get(i).getFullName()+" ("+list.get(i).getUsername()+")");
                if(list.get(i).getSecyOf().toLowerCase().equals(theme) && list.get(i).getHostel().toLowerCase().equals(sharedPreferenceConfig.readhostel().toLowerCase()))
                {
                    index = i;
                    found = true;
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,name);
        secy.setAdapter(adapter);
        if(index!=-1 && index+1 < name.size())
        {
            secy.setSelection(index+1);
        }
        secy.setEnabled(true);

    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }
}
