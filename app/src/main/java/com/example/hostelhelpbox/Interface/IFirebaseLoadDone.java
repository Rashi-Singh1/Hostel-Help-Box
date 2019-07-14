package com.example.hostelhelpbox.Interface;

import com.example.hostelhelpbox.User;

import java.util.List;

public interface IFirebaseLoadDone {
    void onFirebaseLoadSuccess(List<User> users);
    void onFirebaseLoadFailed(String message);
}
