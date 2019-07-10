package com.example.hostelhelpbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterSearchContact extends RecyclerView.Adapter<AdapterSearchContact.MyViewHolder> implements Filterable{
    Context context;
    ArrayList<User> mytodos;
    private ArrayList<User> searchList;
    public AdapterSearchContact(Context c, ArrayList<User> p)
    {
        context = c;
        mytodos = p;
        searchList = new ArrayList<>(p);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search_contact, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.fullname.setText(mytodos.get(i).getFullName());
        viewHolder.email.setText(mytodos.get(i).getEmail());
        viewHolder.hostel.setText(mytodos.get(i).getHostel());
        if (mytodos.get(i).getSecyOf().equals("none"))
        {
        }
        else viewHolder.secyof.setText(mytodos.get(i).getSecyOf());
        final String username = mytodos.get(i).getUsername();
        final String full = mytodos.get(i).getFullName();

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,View_Message_Conversation.class);
                intent.putExtra("receiverUsername", username);
                intent.putExtra("receiverFullname", full);
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

        TextView fullname,email,hostel,secyof;
        LinearLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = (TextView) itemView.findViewById(R.id.receiver);
            email = (TextView) itemView.findViewById(R.id.email);
            hostel = (TextView) itemView.findViewById(R.id.hostel);
            secyof = (TextView) itemView.findViewById(R.id.secyof);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<User> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(searchList);
            }
            else{
                String filterpattern = constraint.toString().toLowerCase().trim();

                for (User item: searchList) {
                    if(item.getFullName().toLowerCase().contains(filterpattern) || item.getHostel().toLowerCase().contains(filterpattern) || item.getSecyOf().toLowerCase().contains(filterpattern))
                    {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mytodos.clear();
            mytodos.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
