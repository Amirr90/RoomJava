package com.e.roomjava;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    UserRepo repo;
    LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repo = new UserRepo(application);
        allUsers = repo.getUserList();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void addUser(User user) {
        repo.addUser(user);
    }

    public void deleteUser(User user) {
        repo.deleteUser(user);
    }
}
