package com.example.hostelhelpbox;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class View_Message_Conversation extends AppCompatActivity {

    String receiverUsername;
    String senderUsername;
    String receiverFullName;
    String sender;
    SharedPreferenceConfig sharedPreferenceConfig;

    TextView receiver_username;
    EditText textSend,textSubject;
    ImageButton send;

    DatabaseReference ref;
    DatabaseReference ref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__message__conversation);
        getIncomingIntent();
        sharedPreferenceConfig = new SharedPreferenceConfig(this);

        receiver_username = findViewById(R.id.receiver_username);
        textSend = findViewById(R.id.textSend);
        textSubject = findViewById(R.id.textSubject);
        send = findViewById(R.id.send);

        sender = sharedPreferenceConfig.readusername();
        final ArrayList<message> list;
        DatabaseReference ref;
        final RecyclerView viewSecretary;
        final TextView endpage;
        final FloatingActionButton btnNewContact;

        endpage = findViewById(R.id.endpage);
        viewSecretary = findViewById(R.id.viewSecretary);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        LinearLayoutManager linearlayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);

//        viewSecretary.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        viewSecretary.setLayoutManager(linearlayoutManager);

        list = new ArrayList<>();
        int i = 0;
        if(receiverUsername.equals(sender))
        {
            //sender is actually sender and senderusername is receiver
            ref2=FirebaseDatabase.getInstance().getReference("Users/"+receiverUsername);
            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User newUser = dataSnapshot.getValue(User.class);
                    receiver_username.setText(newUser.getFullName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            ref = FirebaseDatabase.getInstance().getReference("Users/" + sender + "/messages/"+senderUsername);
            ref.addValueEventListener(new ValueEventListener() {       //later on change for updated value
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    int i = 0;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        if(i >= 15) break;
                        message p = dataSnapshot1.getValue(message.class);
                        list.add(p);
                        i+=1;
                    }
                    if (list.size() != 0) {
                        Collections.reverse(list);
                        com.example.hostelhelpbox.AdapterViewConversation Adapter = new com.example.hostelhelpbox.AdapterViewConversation(getApplicationContext(), list);
                        viewSecretary.setAdapter(Adapter);
                        Adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            //sender is sender , receiver is receievr
            ref2=FirebaseDatabase.getInstance().getReference("Users/"+receiverUsername);
            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User newUser = dataSnapshot.getValue(User.class);
                    receiver_username.setText(newUser.getFullName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            ref = FirebaseDatabase.getInstance().getReference("Users/" + UserInfo.username + "/messages/"+receiverUsername);
            ref.addValueEventListener(new ValueEventListener() {       //later on change for updated value
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    int i = 0;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        if(i >= 15) break;
                        message p = dataSnapshot1.getValue(message.class);
                        list.add(p);
                        i+=1;
                    }
                    if (list.size() != 0) {
                        Collections.reverse(list);
                        com.example.hostelhelpbox.AdapterViewConversation Adapter = new com.example.hostelhelpbox.AdapterViewConversation(getApplicationContext(), list);
                        viewSecretary.setAdapter(Adapter);
                        Adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
            });
        }


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textSend.getText().toString().trim();
                String subject = textSubject.getText().toString().trim();
                if(message.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Can not send empty message",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d("sending message","things : "+sender+receiverUsername+subject+message);
                    if(receiverUsername.equals(sender))
                    {
                        //sender is actually sender and senderusername is receiver
                        sendMessage(sender,senderUsername,subject,message);
//                        receiver_username.setText(receiverFullName);

                    }
                    else
                    {
                        sendMessage(sender,receiverUsername,subject,message);
//                        receiver_username.setText(receiverFullName);
                        //sender is sender , receiver is receievr
                    }
                    textSend.setText("");
                    textSubject.setText("");
                }
            }
        });
        Toast.makeText(getApplicationContext(),"receiver username : " + receiverUsername + " and sender username : " + sharedPreferenceConfig.readusername(),Toast.LENGTH_SHORT).show();
    }

    private void getIncomingIntent()
    {
        if(getIntent().hasExtra("receiverUsername") && getIntent().hasExtra("receiverFullname"))
        {
            receiverUsername = getIntent().getStringExtra("receiverUsername");
            senderUsername = getIntent().getStringExtra("receiverFullname");
        }
    }

    private void sendMessage(String sender, String receiver, String subject,String message)
    {
        ref = FirebaseDatabase.getInstance().getReference("Users/"+sender+"/messages/"+receiver);
        ref2 = FirebaseDatabase.getInstance().getReference("Users/"+receiver+"/messages/"+sender);
        message newMessage = new message(sender,receiver,subject,message);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        newMessage.setDate(currentDate);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String timee = format.format(calendar.getTime());
        newMessage.setTime(timee);
        String key = ref.push().getKey();
        newMessage.setKey(key);
        ref.child(key).setValue(newMessage);
        ref2.child(key).setValue(newMessage);
    }

}
