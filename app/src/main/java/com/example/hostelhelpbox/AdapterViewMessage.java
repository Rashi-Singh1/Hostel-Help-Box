package com.example.hostelhelpbox;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterViewMessage extends RecyclerView.Adapter<AdapterViewMessage.MyViewHolder> {
    Context context;
    ArrayList<message> mytodos;
    SharedPreferenceConfig sharedPreferenceConfig;
    public AdapterViewMessage(Context c, ArrayList<message> p)
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
        sharedPreferenceConfig = new SharedPreferenceConfig(context);
        viewHolder.datetime.setText(mytodos.get(i).getDate() + " " + mytodos.get(i).getTime());
//        Toast.makeText(context,"loggedin user : "+sharedPreferenceConfig.readusername()+" receiver : "+mytodos.get(i).getReceiver()+" sender : "+mytodos.get(i).getSender(),Toast.LENGTH_SHORT).show();
        if(mytodos.get(i).getReceiver().equals(sharedPreferenceConfig.readusername()))
        {
            viewHolder.receiver.setText(mytodos.get(i).getSender());
        }
        else viewHolder.receiver.setText(mytodos.get(i).getReceiver());
//        viewHolder.receiver.setText(mytodos.get(i).getReceiver());
        viewHolder.subject.setText(mytodos.get(i).getSubject());
        final String usernamereceiver = mytodos.get(i).getReceiver();
        final String usernamesender = mytodos.get(i).getSender();

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,View_Message_Conversation.class);
                intent.putExtra("receiverUsername", usernamereceiver);
                intent.putExtra("receiverFullname", usernamesender);
                context.startActivity(intent);
//                context.overridePendingTransition(0, 0);
//                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mytodos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView datetime,receiver,subject;
        LinearLayout parentLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            datetime = (TextView) itemView.findViewById(R.id.datetime);
            receiver = (TextView) itemView.findViewById(R.id.receiver);
            subject = (TextView) itemView.findViewById(R.id.subject);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
