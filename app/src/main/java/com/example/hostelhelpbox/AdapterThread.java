package com.example.hostelhelpbox;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.SmileRating;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class AdapterThread extends RecyclerView.Adapter<AdapterThread.MyViewHolder> {
    Context context;
    ArrayList<Thread> mytodos;
    SharedPreferenceConfig sharedPreferenceConfig;
    DatabaseReference ref;

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

    public void dodialog(int j)
    {
        final Dialog mydialog = new Dialog(context);
        TextView txtclose,subject,body;
        mydialog.setContentView(R.layout.view_thread_popup);
        txtclose =(TextView) mydialog.findViewById(R.id.txtclose);
        subject =(TextView) mydialog.findViewById(R.id.subject);
        body =(TextView) mydialog.findViewById(R.id.body);
        subject.setText(mytodos.get(j).getsubject());
        body.setText(mytodos.get(j).getBody());

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();
    }

    public void rate(int j)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.smily_rating_popup);
        SmileRating smileRating = dialog.findViewById(R.id.smile_rating);
        TextView txtclose;
        txtclose = dialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey()+"/rates/");
        ref.child(sharedPreferenceConfig.readusername());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    // The child doesn't exist
                }
                else{
                    Toast.makeText(context,"Your initial rating will be overwritten",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey()+"/rates");
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley)
                {
                    case SmileRating.BAD:
                        ref2.child(sharedPreferenceConfig.readusername()).setValue(2);
                        break;
                    case SmileRating.GOOD:
                        ref2.child(sharedPreferenceConfig.readusername()).setValue(4);
                        break;
                    case SmileRating.GREAT:
                        ref2.child(sharedPreferenceConfig.readusername()).setValue(5);
                        break;
                    case SmileRating.OKAY:
                        ref2.child(sharedPreferenceConfig.readusername()).setValue(3);
                        break;
                    case SmileRating.TERRIBLE:
                        ref2.child(sharedPreferenceConfig.readusername()).setValue(1);
                        break;
                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void dodialogcomment2(final int j)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.view_comment_popup);

        final ArrayList<Comment> list;

        DatabaseReference ref;
        final RecyclerView viewSecretary;
        final TextView endpage;

        endpage = dialog.findViewById(R.id.endpage);
        viewSecretary = dialog.findViewById(R.id.viewSecretary);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        viewSecretary.setLayoutManager(mLayoutManager);

        list = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Threads/" + mytodos.get(j).gettheme() + "/" + mytodos.get(j).getKey()+"/comments/");
        ref.addValueEventListener(new ValueEventListener() {       //later on change for updated value
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    try{
                        Comment p = dataSnapshot1.getValue(Comment.class);
                        list.add(p);
                    } catch (Throwable e) {
                        Log.d("logginggg", "onCreate eror");
                    }
                }
                if (list.size() != 0) {
                    Collections.reverse(list);
                    String address = "Threads/" + mytodos.get(j).gettheme() + "/" + mytodos.get(j).getKey()+"/comments/";
                    com.example.hostelhelpbox.AdapterViewComment Adapter = new com.example.hostelhelpbox.AdapterViewComment(context, list,address);
                    viewSecretary.setAdapter(Adapter);
                    Adapter.notifyDataSetChanged();
                } else endpage.setText("No comments");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
            }
        });

        TextView txtclose;
        txtclose = dialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final EditText subject,body;
        ImageButton btncomment;
//        subject = mydialog.findViewById(R.id.subject);
        subject = dialog.findViewById(R.id.textSubject);
//        body = mydialog.findViewById(R.id.body);
        body = dialog.findViewById(R.id.textSend);
//        btncomment = mydialog.findViewById(R.id.btncomment);
        btncomment = dialog.findViewById(R.id.send);

        btncomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getsubject = subject.getText().toString().trim();
                String getbody = body.getText().toString().trim();
                if(getbody.isEmpty()){
                    Toast.makeText(context,"Can not have empty body",Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Threads/" + mytodos.get(j).gettheme() + "/" + mytodos.get(j).getKey() + "/comments");
                    Comment comment = new Comment("", getsubject, getbody,sharedPreferenceConfig.readusername());
                    String key = ref.push().getKey();
                    comment.setKey(key);
                    ref.child(key).setValue(comment);
                    ref = FirebaseDatabase.getInstance().getReference("Threads/" + mytodos.get(j).gettheme() + "/" + mytodos.get(j).getKey());
                    ref.child("commentCount").setValue(mytodos.get(j).getCommentCount() + 1);
                    subject.setText("");
                    body.setText("");
                }
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
//
//        setOnClick(btnNewContact, btnNewContact);
//
//        return dialog;
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
        ImageButton btncomment;
//        subject = mydialog.findViewById(R.id.subject);
        subject = mydialog.findViewById(R.id.textSubject);
//        body = mydialog.findViewById(R.id.body);
        body = mydialog.findViewById(R.id.textSend);
//        btncomment = mydialog.findViewById(R.id.btncomment);
        btncomment = mydialog.findViewById(R.id.send);

        btncomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getsubject = subject.getText().toString().trim();
                String getbody = body.getText().toString().trim();
                if(getbody.isEmpty()){
                    Toast.makeText(context,"Can not have empty body",Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Threads/" + mytodos.get(j).gettheme() + "/" + mytodos.get(j).getKey() + "/comments");
                    Comment comment = new Comment("", getsubject, getbody,sharedPreferenceConfig.readusername());
                    String key = ref.push().getKey();
                    comment.setKey(key);
                    ref.child(key).setValue(comment);
                    ref = FirebaseDatabase.getInstance().getReference("Threads/" + mytodos.get(j).gettheme() + "/" + mytodos.get(j).getKey());
                    ref.child("commentCount").setValue(mytodos.get(j).getCommentCount() + 1);
                    subject.setText("");
                    body.setText("");
                }
            }
        });

        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();
    }

    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, int i) {
        final int j = i;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dodialog(j);
            }
        });

        viewHolder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodialog(j);
            }
        });

        viewHolder.textcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dodialogcomment(j);
                dodialogcomment2(j);
            }
        });

        if(mytodos.get(i).isSolved()) {
            viewHolder.unsolved.setText("Solved");
            viewHolder.image.setImageResource(R.drawable.ic_solved);
            viewHolder.unsolved.setTextColor(context.getResources().getColor(R.color.green));
            viewHolder.expecteddatetext.setText("");
            viewHolder.expecteddate.setText("");

        }else if(mytodos.get(i).isAcknowledged()) {
            viewHolder.unsolved.setText("Acknowledged");
            viewHolder.image.setImageResource(R.drawable.ic_acknowledge);
            viewHolder.unsolved.setTextColor(context.getResources().getColor(R.color.green));
            if(!mytodos.get(i).getExpectedTime().isEmpty()) {
                viewHolder.expecteddate.setText(mytodos.get(i).getExpectedTime());
                viewHolder.expecteddatetext.setHint("Expected Date : ");
            }
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
        viewHolder.likes.setText(Integer.toString(mytodos.get(i).getLikeCount()));
        viewHolder.comment.setText(Integer.toString(mytodos.get(i).getCommentCount()));
        DatabaseReference ref;
        final SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(context);
        ref = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey()+"/likes");
        ref.child(sharedPreferenceConfig.readusername());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.getValue() == null) {
                   viewHolder.imagelike.setImageResource(R.drawable.ic_unlike);
               } else {
                   viewHolder.imagelike.setImageResource(R.drawable.ic_like);
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
        viewHolder.imagelike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref;
                final SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(context);
                ref = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey()+"/likes");
                ref.child(sharedPreferenceConfig.readusername());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) {
                            // The child doesn't exist
                            DatabaseReference ref2;
                            ref2 = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey()+"/likes/");
                            ref2.child(sharedPreferenceConfig.readusername()).setValue(sharedPreferenceConfig.readfullName());
                            ref2 = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey());
                            ref2.child("likeCount").setValue(mytodos.get(j).getLikeCount() + 1);
                        }
                        else{
                            DatabaseReference ref2;
                            ref2 = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey()+"/likes/"+sharedPreferenceConfig.readusername());
                            ref2.removeValue();
                            ref2 = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey());
                            ref2.child("likeCount").setValue(mytodos.get(j).getLikeCount() - 1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        viewHolder.dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,viewHolder.dots);
                String username = sharedPreferenceConfig.readusername();
                String getauthority =  viewHolder.authority.getText().toString().trim();
                String authorityextracted="";
                String updatedusername = "("+username+")";
                if(getauthority.indexOf("\n") < getauthority.length())
                {
                    authorityextracted = getauthority.substring(getauthority.indexOf("("));
                }
                if(updatedusername.equals(authorityextracted))
                {
                    popupMenu.inflate(R.menu.thread_option_secy);
                }
                else if(username.equals(viewHolder.creator.getText().toString().trim()))
                {
                    popupMenu.inflate(R.menu.thread_option_creator);
                }
                else{
                    popupMenu.inflate(R.menu.thread_option);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        DatabaseReference ref;
                        switch (item.getItemId())
                        {
                            case R.id.menu_ack:
                                Toast.makeText(context,"Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey(),Toast.LENGTH_SHORT).show();
                                ref = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey());
                                ref.child("acknowledged").setValue(true);
                                Toast.makeText(context,"Please add an expected solution date to this thread",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_expected_time:

                                final Calendar myCalendar = Calendar.getInstance();
                                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                          int dayOfMonth) {

                                        myCalendar.set(Calendar.YEAR, year);
                                        myCalendar.set(Calendar.MONTH, monthOfYear);
                                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey());
                                        ref2.child("expectedTime").setValue(Integer.toString(dayOfMonth)+" - "+Integer.toString(monthOfYear)+" - "+Integer.toString(year));
                                        ref2.child("acknowledged").setValue(true);
                                        viewHolder.expecteddate.setText(Integer.toString(dayOfMonth)+" - "+Integer.toString(monthOfYear)+" - "+Integer.toString(year));
                                        viewHolder.expecteddatetext.setHint("Expected Date : ");
                                    }
                                };

                                new DatePickerDialog(context, date, myCalendar
                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                break;
                            case R.id.menu_del:
                                DatabaseReference ref2;
                                ref2 = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey());
                                ref2.removeValue();
                                break;
                            case R.id.menu_urgent:
                                ref = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey());
                                if(mytodos.get(j).isImp()) ref.child("imp").setValue(false);
                                else ref.child("imp").setValue(true);
                                break;
                            case R.id.menu_solved:
                                ref = FirebaseDatabase.getInstance().getReference("Threads/"+mytodos.get(j).gettheme()+"/"+mytodos.get(j).getKey());
                                viewHolder.expecteddatetext.setText("");
                                viewHolder.expecteddate.setText("");
                                ref.child("solved").setValue(true);
                                break;
                            case R.id.menu_rate:
                                rate(j);
                                break;
                            case R.id.menu_share:
                                break;
                            case R.id.menu_change_authority:
                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mytodos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView unsolved,creator,subject,datetime,authority,body,likes,comment,dots,expecteddatetext,expecteddate,textcomment;
        ImageView image,urgent,imagelike;
        LinearLayout mLinearLayout;

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
            dots = (TextView) itemView.findViewById(R.id.dots);
            expecteddatetext = itemView.findViewById(R.id.expecteddatetext);
            expecteddate = itemView.findViewById(R.id.expecteddate);
            imagelike = itemView.findViewById(R.id.imagelike);
            image = itemView.findViewById(R.id.image);
            urgent = itemView.findViewById(R.id.urgent);
            textcomment = itemView.findViewById(R.id.textcomment);
            mLinearLayout = itemView.findViewById(R.id.mLinearLayout);
            sharedPreferenceConfig = new SharedPreferenceConfig(context);
        }
    }
}

