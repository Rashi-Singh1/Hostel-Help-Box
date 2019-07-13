package com.example.hostelhelpbox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddThread extends AppCompatActivity {

    String theme;
    EditText secy,subject,body;
    Button submit;
    Switch urgent;
    DatabaseReference ref;
    SharedPreferenceConfig sharedPreferenceConfig;
    ArrayList<User> list;
    TextView mytheme;
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
        list = new ArrayList<>();
        found = false;
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

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
                Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    User p = dataSnapshot1.getValue(User.class);
                    if(p!=null && p.getUsertype().equals("secy"))
                    {
                        list.add(p);
                    }
                }
                if(list.size()!=0)
                {
                    for(int i = 0;i<list.size();i++)
                    {
                        if(list.get(i).getSecyOf().toLowerCase().equals(theme) && list.get(i).getHostel().toLowerCase().equals(sharedPreferenceConfig.readhostel().toLowerCase()))
                        {
                            secy.setText(list.get(i).getFullName());
                            found = true;
                            break;
                        }
                    }
                    secy.setEnabled(true);
                }
                if(secy.equals(""))
                {
                    secy.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();
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
        String getsecy = secy.getText().toString().trim();
        String getsubject = subject.getText().toString().trim();
        String getbody = body.getText().toString().trim();
        boolean isurgent = urgent.isChecked();
        if(getsecy.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please enter authority name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(getsubject.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Subject can not be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(getbody.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Body of the thread can not be empty",Toast.LENGTH_SHORT).show();
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
        if(found == false) secy.setText("");
        progress.hide();
    }

    private void getIncomingIntent()
    {
        if(getIntent().hasExtra("theme"))
        {
            theme = getIntent().getStringExtra("theme");
        }
    }
}
