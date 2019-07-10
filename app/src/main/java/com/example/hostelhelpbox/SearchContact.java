package com.example.hostelhelpbox;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
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

public class SearchContact extends AppCompatActivity{

    ArrayList<User> list;
    DatabaseReference ref;
    RecyclerView viewFeedback;
    AdapterSearchContact Adapter;
    TextView title, endpage;
//    EditText search;
    SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);
        sharedPreferenceConfig = new SharedPreferenceConfig(this);

        title = findViewById(R.id.title);
        endpage = findViewById(R.id.endpage);
        viewFeedback = findViewById(R.id.viewFeedback);
        viewFeedback.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                list.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    User p = dataSnapshot1.getValue(User.class);
                    if(list.isEmpty())
                    {
                        list.add(p);
                    }
                    else {

                        if (!(sharedPreferenceConfig.readusername().equals(p.getUsername())) && !(p.getUsertype().equals("admin")))
                            list.add(p);

                    }
                }
                if (list.size() != 0)
                {
                    Adapter = new AdapterSearchContact(getApplicationContext(),list);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}