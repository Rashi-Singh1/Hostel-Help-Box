package com.example.hostelhelpbox;

import android.accessibilityservice.FingerprintGestureController;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterViewComment extends RecyclerView.Adapter<AdapterViewComment.MyViewHolder> {
    Context context;
    ArrayList<Comment> mytodos;
    SharedPreferenceConfig sharedPreferenceConfig;
    String Address;
    public AdapterViewComment(Context c, ArrayList<Comment> p, String address)
    {
        context = c;
        mytodos = p;
        Address = address;
        sharedPreferenceConfig = new SharedPreferenceConfig(context);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, viewGroup, false));
    }


    public void dodialogcomment(final int j)
    {
        final Dialog mydialog = new Dialog(context);

        mydialog.setContentView(R.layout.add_comment_popup);
        TextView txtclose;
        txtclose = mydialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        final EditText subject,body;
        final LinearLayout replytolayout;
        ImageButton btncomment;
        subject = mydialog.findViewById(R.id.subject);
        body = mydialog.findViewById(R.id.body);
        btncomment = mydialog.findViewById(R.id.btncomment);
        replytolayout = mydialog.findViewById(R.id.replytoLayout);
        ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView tv=new TextView(context);
        ImageView gv = new ImageView(context);
        gv.setLayoutParams(lparams);
        tv.setLayoutParams(lparams);
        gv.setImageResource(R.drawable.ic_reply);
        if(mytodos.get(j).getBody().length() > 10)  tv.setText(mytodos.get(j).getUsername()+"\n"+mytodos.get(j).getBody().substring(0,10));
        else tv.setText(mytodos.get(j).getUsername()+"\n"+mytodos.get(j).getBody());
        replytolayout.addView(gv);
        replytolayout.addView(tv);
        btncomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getsubject = subject.getText().toString().trim();
                String getbody = body.getText().toString().trim();
                if(getbody.isEmpty()){
                    Toast.makeText(context,"Can not have empty body",Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Address);
                    Comment comment = new Comment("", getsubject, getbody,sharedPreferenceConfig.readusername());
                    comment.setReplyTo(tv.getText().toString().trim());
                    String key = ref.push().getKey();
                    comment.setKey(key);
                    ref.child(key).setValue(comment);
                    ref = FirebaseDatabase.getInstance().getReference(Address.substring(0,Address.length()-10));
                    ref.child("commentCount").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            Integer value = snapshot.getValue(Integer.class);
                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference(Address.substring(0,Address.length()-10));
                            ref2.child("commentCount").setValue(value+1);
                            mydialog.dismiss();
                            // data available in snapshot.value()
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    ref.child("commentCount").setValue(ref.child("commentCount").);
//                    subject.setText("");
//                    body.setText("");
                }
            }
        });

        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();
    }

    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, int i) {
//        sharedPreferenceConfig = new SharedPreferenceConfig(context);
        viewHolder.subject.setText(mytodos.get(i).getSubject());
        viewHolder.body.setText(mytodos.get(i).getBody());
        viewHolder.like.setText(Integer.toString(mytodos.get(i).getLikeCount()));
        viewHolder.username.setText(mytodos.get(i).getUsername());
        if(!mytodos.get(i).getReplyTo().isEmpty())
        {
            ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView tv=new TextView(context);
            ImageView gv = new ImageView(context);
            gv.setLayoutParams(lparams);
            tv.setLayoutParams(lparams);
            gv.setImageResource(R.drawable.ic_reply);
            tv.setText(mytodos.get(i).getReplyTo());
            viewHolder.replytolayout.addView(gv);
            viewHolder.replytolayout.addView(tv);
        }
        if(mytodos.get(i).isPinned())
        {
            viewHolder.pin.setImageResource(R.drawable.ic_urgent);
        }
        else
        {
            viewHolder.pin.setImageResource(R.drawable.ic_not_urgent);
        }
        final int j = i;
        viewHolder.pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mytodos.get(j).isPinned()) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Address+"/"+mytodos.get(j).getKey());
                    ref.child("pinned").setValue(false);
                    viewHolder.pin.setImageResource(R.drawable.ic_not_urgent);
                }
                else{
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Address+"/"+mytodos.get(j).getKey());
                    ref.child("pinned").setValue(true);
                    viewHolder.pin.setImageResource(R.drawable.ic_urgent);
                }
            }
        });
        viewHolder.replyback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodialogcomment(j);
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Address+"/"+mytodos.get(j).getKey());
//                if(mytodos.get(j).getBody().length() > 10) ref.child("replyTo").setValue(mytodos.get(j).getUsername()+"\n"+mytodos.get(j).getBody().substring(0,10));
//                else ref.child("replyTo").setValue(mytodos.get(j).getUsername()+"\n"+mytodos.get(j).getBody());
            }
        });
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference(Address+mytodos.get(j).getKey()+"/likes/");
        ref.child(sharedPreferenceConfig.readusername());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    viewHolder.imagelike.setImageResource(R.drawable.ic_unheart);
                } else {
                    viewHolder.imagelike.setImageResource(R.drawable.ic_heart);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        viewHolder.imagelike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Address+mytodos.get(j).getKey()+"/likes/");
                ref.child(sharedPreferenceConfig.readusername());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) {
                            // The child doesn't exist
                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference(Address+mytodos.get(j).getKey()+"/likes/");
                            ref2.child(sharedPreferenceConfig.readusername()).setValue(sharedPreferenceConfig.readfullName());
                            ref2 = FirebaseDatabase.getInstance().getReference(Address+mytodos.get(j).getKey());
                            ref2.child("likeCount").setValue(mytodos.get(j).getLikeCount()+1);
                            viewHolder.imagelike.setImageResource(R.drawable.ic_heart);
                        }
                        else{
//                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference(Address+"likes/");
//                            ref2.child(sharedPreferenceConfig.readusername()).setValue(sharedPreferenceConfig.readfullName());
//                            ref2 = FirebaseDatabase.getInstance().getReference(Address);
//                            ref2.child("likeCount").setValue(mytodos.get(j).getLikeCount()+1);
//                            viewHolder.imagelike.setImageResource(R.drawable.ic_heart);
                            DatabaseReference ref2;
                            ref2 = FirebaseDatabase.getInstance().getReference(Address+mytodos.get(j).getKey()+"/likes/"+sharedPreferenceConfig.readusername());
                            ref2.removeValue();
                            ref2 = FirebaseDatabase.getInstance().getReference(Address+mytodos.get(j).getKey());
                            ref2.child("likeCount").setValue(mytodos.get(j).getLikeCount() - 1);
                            viewHolder.imagelike.setImageResource(R.drawable.ic_unheart);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mytodos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView replyto,like,body,subject,username;
        LinearLayout parentLayout,replytolayout;
        ImageView imagereplyto,imagelike, replyback,pin;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            body = (TextView) itemView.findViewById(R.id.body);
            subject = (TextView) itemView.findViewById(R.id.subject);
            username = itemView.findViewById(R.id.username);
//            replyto = itemView.findViewById(R.id.replyto);
            like = itemView.findViewById(R.id.like);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            replytolayout = itemView.findViewById(R.id.replytoLayout);
//            imagereplyto = itemView.findViewById(R.id.imagereplyto);
            imagelike = itemView.findViewById(R.id.imagelike);
            replyback = itemView.findViewById(R.id.replyback);
            pin = itemView.findViewById(R.id.pin);
        }
    }
}
