package com.e.roomjava;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepo {

    UserDao userDao;
    public LiveData<List<User>> userList;

    public UserRepo(Application application) {
        UserDatabase userDatabase = UserDatabase.getDatabase(application);
        userDao = userDatabase.userDao();
        userList = userDao.getAllUser();
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }

    public void addUser(User user) {
        new InsertUserIntoDao(userDao).execute(user);

    }

    public void deleteUser(User user) {
        new DeleteUserFromDao(userDao).execute(user);
    }

    public static class InsertUserIntoDao extends AsyncTask<User, Void, Void> {

        UserDao userDao;

        public InsertUserIntoDao(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.addUser(users[0]);
            return null;
        }
    }

    public static class DeleteUserFromDao extends AsyncTask<User, Void, Void> {

        UserDao userDao;

        public DeleteUserFromDao(UserDao userDao) {
            this.userDao = userDao;
        }


        @Override
        protected Void doInBackground(User... users) {
            userDao.deleteUser(users[0]);
            return null;
        }
    }
}
