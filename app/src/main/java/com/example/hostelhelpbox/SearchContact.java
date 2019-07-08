package com.example.hostelhelpbox;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchContact extends AppCompatActivity {

    ArrayList<User> list;
    DatabaseReference ref;
    RecyclerView viewFeedback;
    TextView title, endpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);

        //Toast.makeText(getApplicationContext(),"loading or not",Toast.LENGTH_LONG).show();

        title = findViewById(R.id.title);
        endpage = findViewById(R.id.endpage);
        viewFeedback = findViewById(R.id.viewFeedback);
        viewFeedback.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    //Toast.makeText(getApplicationContext(),"getting or not",Toast.LENGTH_LONG).show();
                    User p = dataSnapshot1.getValue(User.class);
                    list.add(p);
                    //Toast.makeText(getApplicationContext(),p.getQuesName(),Toast.LENGTH_LONG).show();

                }
                if (list.size() != 0)
                {
                    AdapterSearchContact Adapter = new AdapterSearchContact(getApplicationContext(),list);
                    viewFeedback.setAdapter(Adapter);
                    Adapter.notifyDataSetChanged();
                }
                else endpage.setText("No contacts");

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}