package com.example.hostelhelpbox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class FragmentViewMessage extends Fragment {
    public FragmentViewMessage() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.activity_view_message, container, false);
        final ArrayList<message> list;
        DatabaseReference ref;
        final RecyclerView viewSecretary;
        final TextView endpage;
        final FloatingActionButton btnNewContact;

        endpage = RootView.findViewById(R.id.endpage);
        viewSecretary = RootView.findViewById(R.id.viewSecretary);
        btnNewContact = RootView.findViewById(R.id.btnNewContact);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
//        viewSecretary.setLayoutManager(new LinearLayoutManager(getContext()));
        viewSecretary.setLayoutManager(mLayoutManager);

        list = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Users/" + UserInfo.username + "/messages");
        ref.addValueEventListener(new ValueEventListener() {       //later on change for updated value
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    try{
                        message account  = dataSnapshot1.getChildren().iterator().next().getValue(message.class);
                        list.add(account);

                    } catch (Throwable e) {
                        Log.d("logginggg", "onCreate eror");
                    }
                }
                if (list.size() != 0) {
                    com.example.hostelhelpbox.AdapterViewMessage Adapter = new com.example.hostelhelpbox.AdapterViewMessage(getContext(), list);
                    viewSecretary.setAdapter(Adapter);
                    Adapter.notifyDataSetChanged();
                } else endpage.setText("No messages");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });

        setOnClick(btnNewContact, btnNewContact);

        return RootView;
    }

    private void setOnClick(final FloatingActionButton btn, final FloatingActionButton btnNewContact) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnNewContact) {
                    Intent intent;
                    intent = new Intent(getContext(), SearchContact.class);
                    startActivity(intent);
//                    Fragment someFragment = new FragmentSearchContact();
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.screen_area, someFragment ); // give your fragment container id in first parameter
//                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                    transaction.commit();
                }

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}