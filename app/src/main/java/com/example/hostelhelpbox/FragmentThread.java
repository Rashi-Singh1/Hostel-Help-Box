package com.example.hostelhelpbox;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentThread extends Fragment {
//    private ArrayList<Thread> list;

    public FragmentThread()
    {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_complaint, container, false);
        final ArrayList<Thread> list;
        DatabaseReference ref;
        final RecyclerView viewSecretary;
        final TextView endpage;

        endpage = RootView.findViewById(R.id.endpage);
        viewSecretary = RootView.findViewById(R.id.viewSecretary);
        viewSecretary.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();

        Bundle bundle = this.getArguments();
        String theme = (String) bundle.getSerializable("theme");
        ref = FirebaseDatabase.getInstance().getReference("Threads/"+theme);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(getContext());
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Thread p = dataSnapshot1.getValue(Thread.class);
                    if(sharedPreferenceConfig.readhostel().equals(p.getHostel()))
                    {
                        list.add(p);
                    }
                }
                if(list.size()!=0)
                {
                    Collections.reverse(list);
                    com.example.hostelhelpbox.AdapterThread Adapter = new com.example.hostelhelpbox.AdapterThread(getContext(),list);
                    viewSecretary.setAdapter(Adapter);
                    Adapter.notifyDataSetChanged();
                    endpage.setText("");
                }
                else endpage.setText("No complaints/suggestions ... ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"No Data",Toast.LENGTH_SHORT).show();
            }
        });
        return RootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
