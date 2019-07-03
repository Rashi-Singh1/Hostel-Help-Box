package com.example.hostelhelpbox;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
    User removedItem;
    int removedPos;
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
//            Toast.makeText(getContext(),"index j : "+ Integer.toString(j),Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(),"index getpositionnnn : "+ Integer.toString(viewHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
            DatabaseReference ref;
            ref = FirebaseDatabase.getInstance().getReference("Users/"+list.get(j).getUsername());
            removedItem = new User(list.get(j).getFullName(),list.get(j).getEmail(),list.get(j).getPasswd(),list.get(j).getHostel(),list.get(j).getSecyOf());
            removedItem.setUsertype(list.get(j).getUsertype());
            removedItem.setUsername(list.get(j).getUsername());
            removedPos = j;
            ref.removeValue();
            list.remove(j);
            Snackbar.make(viewHolder.itemView, removedItem.getUsername()+" deleted",Snackbar.LENGTH_LONG).setAction("UNDO", new MyUndoListener()).show();
//  Adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                // Get RecyclerView item from the ViewHolder
                View itemView = viewHolder.itemView;
                int indianRed = Color.rgb(205,92,92);
                int raspberry = Color.rgb(210,31,60);

                int persian = Color.rgb(202,52,51);
                int redwood = Color.rgb(164,90,82);
                int desire = Color.rgb(234,60,83);
                int salmon = Color.rgb(250,128,114);
                Drawable trash = ContextCompat.getDrawable(getContext(),R.drawable.ic_delete_black_24dp);

                int iconMargin = (itemView.getHeight()-trash.getIntrinsicHeight())/2;
//                int iconMargin = (itemView.getHeight()-trash.getIntrinsicHeight())/2;
                Paint p = new Paint();
                if (dX > 0) {
                    /* Set your color for positive displacement */
                    p.setColor(salmon);
                    trash.setBounds(itemView.getLeft()+iconMargin,itemView.getTop()+iconMargin,itemView.getLeft()+iconMargin+trash.getIntrinsicWidth(),itemView.getBottom()-iconMargin);
                    // Draw Rect with varying right side, equal to displacement dX
                    c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                            (float) itemView.getBottom(), p);
                } else {
                    /* Set your color for negative displacement */
                    p.setColor(salmon);
                    trash.setBounds(itemView.getRight()-iconMargin-trash.getIntrinsicWidth(),itemView.getTop()+iconMargin,itemView.getRight()-iconMargin,itemView.getBottom()-iconMargin);

                    // Draw Rect with varying left side, equal to the item's right side plus negative displacement dX
                    c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                            (float) itemView.getRight(), (float) itemView.getBottom(), p);
                }
                trash.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

    };

    class MyUndoListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            DatabaseReference ref;
            ref = FirebaseDatabase.getInstance().getReference().child("Users");
            ref.child(removedItem.getUsername()).setValue(removedItem);
            list.add(removedPos,removedItem);
            Adapter.notifyDataSetChanged();
        }
    }



}
