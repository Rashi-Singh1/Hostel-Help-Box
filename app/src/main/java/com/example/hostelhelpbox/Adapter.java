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
        //  Toast.makeText(context,"2000",Toast.LENGTH_LONG).show();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_does2, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        //if (mytodos.get(i).getQuesName())
//        String temp = mytodos.get(i).getUsername();
        // Toast.makeText(context,"1000",Toast.LENGTH_LONG).show();

        viewHolder.username.setText(mytodos.get(i).getUsername());
        viewHolder.fullname.setText(mytodos.get(i).getFullName());
        viewHolder.email.setText(mytodos.get(i).getEmail());
        viewHolder.hostel.setText(mytodos.get(i).getHostel());
        viewHolder.secyof.setText(mytodos.get(i).getSecyOf());
//        viewHolder.option5.setText(mytodos.get(i).getOp4Name());
        //viewHolder.keydoes.setText(mytodos.get(i).getKeydoes());

//        final String getquestion = mytodos.get(i).getQuesName();
//        final String getoption1 = mytodos.get(i).getOp1Name();
//        final String getoption2 = mytodos.get(i).getOp2Name();
//        final String getoption3 = mytodos.get(i).getOp3Name();
//        final String getoption4 = mytodos.get(i).getOp4Name();
//        final String getoption5 = mytodos.get(i).getOp5Name();

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent a = new Intent(context, EditTaskAct.class);
//                a.putExtra("titledoes",getTitleDoes);
//                a.putExtra("keydoes",getKeyDoes);
//                context.startActivity(a);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mytodos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView username,fullname,email,hostel,secyof,option5;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            fullname = (TextView) itemView.findViewById(R.id.fullname);
            email = (TextView) itemView.findViewById(R.id.email);
            hostel = (TextView) itemView.findViewById(R.id.hostel);
            secyof = (TextView) itemView.findViewById(R.id.secyof);
//            option5 = (TextView) itemView.findViewById(R.id.option5);
        }
    }
}
