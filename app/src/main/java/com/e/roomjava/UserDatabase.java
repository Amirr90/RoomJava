package com.e.roomjava;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = User.class, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public static UserDatabase userDatabase;

    public abstract UserDao userDao();

    public static synchronized UserDatabase getDatabase(Application application) {
        if (userDatabase == null)
            userDatabase = Room.databaseBuilder(application, UserDatabase.class, "user_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();

        return userDatabase;
    }


    //call only once when the App is installed !!
    public static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new AddTaskToDatabase(userDatabase).execute();
        }
    };

    public static class AddTaskToDatabase extends AsyncTask<Void, Void, Void> {

        public UserDatabase userDatabase;

        public AddTaskToDatabase(UserDatabase userDatabase) {
            this.userDatabase = userDatabase;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDatabase.userDao().addUser(new User("Aamirr Khan", 20));
            userDatabase.userDao().addUser(new User("Sameer", 14));
            userDatabase.userDao().addUser(new User("AamirrKing ", 36));
            userDatabase.userDao().addUser(new User("Aamirr", 25));
            userDatabase.userDao().addUser(new User("Khan", 54));
            return null;
        }
    }
}

