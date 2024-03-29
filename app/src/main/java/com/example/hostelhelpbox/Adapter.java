package com.example.hostelhelpbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    ArrayList<User> mytodos;

    public Adapter(Context c, ArrayList<User> p)
    {
        context = c;
        mytodos = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_does2, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.username.setText(mytodos.get(i).getUsername());
        viewHolder.fullname.setText(mytodos.get(i).getFullName());
        viewHolder.email.setText(mytodos.get(i).getEmail());
        viewHolder.hostel.setText(mytodos.get(i).getHostel());
        viewHolder.secyof.setText(mytodos.get(i).getSecyOf());
    }

    @Override
    public int getItemCount() {
        return mytodos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView username,fullname,email,hostel,secyof;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            fullname = (TextView) itemView.findViewById(R.id.fullname);
            email = (TextView) itemView.findViewById(R.id.email);
            hostel = (TextView) itemView.findViewById(R.id.hostel);
            secyof = (TextView) itemView.findViewById(R.id.secyof);
        }
    }
}
