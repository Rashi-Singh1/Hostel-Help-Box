package com.example.hostelhelpbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

public class FragmentDelSecy extends Fragment {
    private ArrayList<User> list;
    private com.example.hostelhelpbox.Adapter Adapter;

    public FragmentDelSecy()
    {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View RootView = inflater.inflate(R.layout.fragment_del_secy, container, false);
//        final ArrayList<User> list;
        DatabaseReference ref;
        final RecyclerView viewSecretary;
        final TextView title, endpage;

        title = RootView.findViewById(R.id.title);
        endpage = RootView.findViewById(R.id.endpage);
        viewSecretary = RootView.findViewById(R.id.viewSecretary);
        viewSecretary.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
//        Adapter Adapter = new com.example.hostelhelpbox.Adapter(getContext(),list);
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index = 0;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    User p = dataSnapshot1.getValue(User.class);
                    if(p!=null && p.getUsertype().equals("secy"))
                    {
                        if(list.size() > 0 && list.get(index).getUsername().equals(p.getUsername()))
                        {
                            index++;
                        }
                        else list.add(p);
                    }
                }
                if(list.size()!=0)
                {
                    Adapter = new com.example.hostelhelpbox.Adapter(getContext(),list);
                    new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(viewSecretary);

//                  Adapter = new Adapter(getContext(),list);
                    viewSecretary.setAdapter(Adapter);
                    Adapter.notifyDataSetChanged();
                }
                else endpage.setText("No secretaries added");

//                val itemTouchHelperCallBack = User :ItemTouchHelper.SimpleCallback
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

    ItemTouchHelper.SimpleCallback  itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            int j = viewHolder.getAdapterPosition();
            Toast.makeText(getContext(),"index j : "+ Integer.toString(j),Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(),"index getpositionnnn : "+ Integer.toString(viewHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
            DatabaseReference ref;
            ref = FirebaseDatabase.getInstance().getReference("Users/"+list.get(j).getUsername());
            ref.removeValue();
            list.remove(j);
//            Adapter.notifyDataSetChanged();
        }
    };
}
