package com.example.hostelhelpbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterViewConversation extends RecyclerView.Adapter<AdapterViewConversation.MyViewHolder> {
    Context context;
    ArrayList<message> mytodos;

    public AdapterViewConversation(Context c, ArrayList<message> p)
    {
        context = c;
        mytodos = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.datetime.setText(mytodos.get(i).getDate() + mytodos.get(i).getTime());
        viewHolder.body.setText(mytodos.get(i).getBody());
        viewHolder.subject.setText(mytodos.get(i).getSubject());
    }

    @Override
    public int getItemCount() {
        return mytodos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView datetime,body,subject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            datetime = (TextView) itemView.findViewById(R.id.datetime);
            body = (TextView) itemView.findViewById(R.id.body);
            subject = (TextView) itemView.findViewById(R.id.subject);
        }
    }
}
