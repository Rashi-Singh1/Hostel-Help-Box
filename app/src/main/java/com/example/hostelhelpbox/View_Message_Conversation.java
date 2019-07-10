package com.example.hostelhelpbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
