package com.example.hostelhelpbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterThread extends RecyclerView.Adapter<AdapterThread.MyViewHolder> {
    Context context;
    ArrayList<Thread> mytodos;

    public AdapterThread(Context c, ArrayList<Thread> p)
    {
        context = c;
        mytodos = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_thread, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        if(mytodos.get(i).isSolved()) {
            viewHolder.unsolved.setText("Solved");
            viewHolder.image.setImageResource(R.drawable.ic_solved);
            viewHolder.unsolved.setTextColor(context.getResources().getColor(R.color.green));
        }else if(mytodos.get(i).isAcknowledged()) {
            viewHolder.unsolved.setText("Acknowledged");
            viewHolder.image.setImageResource(R.drawable.ic_acknowledge);
            viewHolder.timeLimit.setText(mytodos.get(i).getExpectedTime());
            viewHolder.unsolved.setTextColor(context.getResources().getColor(R.color.green));
        }
        else{
            viewHolder.unsolved.setText("Unsolved");
            viewHolder.image.setImageResource(R.drawable.ic_cross);
            viewHolder.unsolved.setTextColor(context.getResources().getColor(R.color.red));
        }
        if(mytodos.get(i).isImp())
        {
            viewHolder.urgent.setImageResource(R.drawable.ic_urgent);
        }
        else{
            viewHolder.urgent.setImageResource(R.drawable.ic_not_urgent);
        }
        viewHolder.creator.setText(mytodos.get(i).getCreator());
        viewHolder.subject.setText(mytodos.get(i).getsubject());
        viewHolder.datetime.setText(mytodos.get(i).getTime()+"  "+ mytodos.get(i).getDate());
        viewHolder.authority.setText(mytodos.get(i).getSecy());
        viewHolder.body.setText(mytodos.get(i).getBody());
        if(mytodos.get(i).getLikes()!=null) viewHolder.likes.setText(Integer.toString(mytodos.get(i).getLikes().size()));
        else viewHolder.likes.setText(" 0");
        if(mytodos.get(i).getComments()!=null) viewHolder.comment.setText(Integer.toString(mytodos.get(i).getComments().size()));
        else viewHolder.comment.setText(" 0");
    }

    @Override
    public int getItemCount() {
        return mytodos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView unsolved,creator,subject,datetime,authority,body,likes,comment,timeLimit;
        ImageView image,urgent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unsolved = (TextView) itemView.findViewById(R.id.unsolved);
            creator = (TextView) itemView.findViewById(R.id.creator);
            subject = (TextView) itemView.findViewById(R.id.subject);
            datetime = (TextView) itemView.findViewById(R.id.datetime);
            authority = (TextView) itemView.findViewById(R.id.authority);
            body = (TextView) itemView.findViewById(R.id.body);
            likes = (TextView) itemView.findViewById(R.id.likes);
            comment = (TextView) itemView.findViewById(R.id.comment);
            timeLimit = (TextView) itemView.findViewById(R.id.timeLimit);
            image = itemView.findViewById(R.id.image);
            urgent = itemView.findViewById(R.id.urgent);
        }
    }
}
