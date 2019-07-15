package com.example.hostelhelpbox;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentViewSecy extends Fragment {
//    private ArrayList<User> list;

    public FragmentViewSecy()
    {

    }
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View RootView = inflater.inflate(R.layout.fragment_view_secy, container, false);
        if(context!=null) {
            final ArrayList<User> list;
            DatabaseReference ref;
            final RecyclerView viewSecretary;
            final TextView title, endpage;

            title = RootView.findViewById(R.id.title);
            endpage = RootView.findViewById(R.id.endpage);
            viewSecretary = RootView.findViewById(R.id.viewSecretary);
            viewSecretary.setLayoutManager(new LinearLayoutManager(context));
            list = new ArrayList<>();

//        Adapter Adapter = new com.example.hostelhelpbox.Adapter(context,list);
            ref = FirebaseDatabase.getInstance().getReference().child("Users");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        User p = dataSnapshot1.getValue(User.class);
                        if (p != null && p.getUsertype().equals("secy")) {
                            list.add(p);
                        }
                    }
                    if (list.size() != 0) {
                        com.example.hostelhelpbox.Adapter Adapter = new com.example.hostelhelpbox.Adapter(context, list);

//                  Adapter = new Adapter(context,list);
                        viewSecretary.setAdapter(Adapter);
                        Adapter.notifyDataSetChanged();
                    } else endpage.setText("No secretaries added");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return RootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
