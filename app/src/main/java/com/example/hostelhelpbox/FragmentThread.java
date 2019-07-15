package com.example.hostelhelpbox;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import java.util.Objects;

public class FragmentThread extends Fragment {
//    private ArrayList<Thread> list;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public FragmentThread()
    {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_complaint, container, false);
        if(context!=null){
            final ArrayList<Thread> list;
            DatabaseReference ref;
            final RecyclerView viewSecretary;
            final TextView endpage;

            endpage = RootView.findViewById(R.id.endpage);
            viewSecretary = RootView.findViewById(R.id.viewSecretary);
            viewSecretary.setLayoutManager(new LinearLayoutManager(context));
            final SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(context);

            list = new ArrayList<>();

            Bundle bundle = this.getArguments();
            String theme = (String) bundle.getSerializable("theme");
            if (theme!=null && theme.equals("my_created")) {
                final String[] intArray = new String[]{"cultural", "maintenance", "mess", "sports", "technical", "welfare"};
                for (int i = 0; i < 6; i++) {
                    final int j = i;
                    ref = FirebaseDatabase.getInstance().getReference("Threads/" + intArray[i]);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            list.clear();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                Thread p = dataSnapshot1.getValue(Thread.class);
//                                if (context == null)
//                                    Toast.makeText(context, "yhi null h", Toast.LENGTH_SHORT).show();
//                                if (sharedPreferenceConfig.readusername() != null)
//                                    Toast.makeText(context, intArray[j] + " username : " + sharedPreferenceConfig.readusername() + " creator : " + p.getCreator(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "checking : " + UserInfo.username, Toast.LENGTH_SHORT).show();
                                if (sharedPreferenceConfig.readusername().equals(p.getCreator())) {
                                    list.add(p);
                                }
                            }
//                            Toast.makeText(context,"size : "+Integer.toString(list.size()),Toast.LENGTH_SHORT).show();
                            if (list.size() != 0) {
                                Collections.reverse(list);
                                com.example.hostelhelpbox.AdapterThread Adapter = new com.example.hostelhelpbox.AdapterThread(context, list);
                                viewSecretary.setAdapter(Adapter);
                                Adapter.notifyDataSetChanged();
                                endpage.setText("");
                                Adapter.notifyDataSetChanged();
//                                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                                ft.detach(this).attach(context).commit();
//                                FragmentTransaction ftr = getFragmentManager().beginTransaction();
//                                ftr.detach(FragmentThread.this).attach(FragmentThread.this).commit();
                            }
    //                        else endpage.setText("No uploaded complaints/suggestions ... ");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                ref = FirebaseDatabase.getInstance().getReference("Threads/" + theme);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Thread p = dataSnapshot1.getValue(Thread.class);
                            if (sharedPreferenceConfig.readhostel().equals(p.getHostel())) {
                                list.add(p);
                            }
                        }
                        if (list.size() != 0) {
                            Collections.reverse(list);
                            com.example.hostelhelpbox.AdapterThread Adapter = new com.example.hostelhelpbox.AdapterThread(context, list);
                            viewSecretary.setAdapter(Adapter);
                            Adapter.notifyDataSetChanged();
                            endpage.setText("");
                        } else endpage.setText("No complaints/suggestions ... ");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        return RootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
