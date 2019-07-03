package com.example.hostelhelpbox;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentProfile extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Toast.makeText(getContext(),"My profile",Toast.LENGTH_SHORT).show();
        View RootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView username = RootView.findViewById(R.id.username);
        TextView FullName = (TextView) RootView.findViewById(R.id.fullname);
        TextView Email = (TextView) RootView.findViewById(R.id.email);
        TextView Usertype = (TextView) RootView.findViewById(R.id.usertype);
        TextView Hostel = (TextView) RootView.findViewById(R.id.hostel);

        if(UserInfo.username == null) return RootView;
//        Toast.makeText(getContext(),"Welcome2 "+UserInfo.username,Toast.LENGTH_SHORT).show();
        username.setText(UserInfo.username);
        FullName.setText(UserInfo.fullname);
        Email.setText(UserInfo.email);
        Hostel.setText(UserInfo.hostel);
        if(!UserInfo.usertype.equals("normal")) Usertype.setText(UserInfo.usertype);
        return RootView;
    }
}
