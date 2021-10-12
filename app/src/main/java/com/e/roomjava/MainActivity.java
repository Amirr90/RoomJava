package com.e.roomjava;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    private static final String TAG = "MainActivity";
    TextView textView;

    EditText name, age, etId;
    Button add, update, delete, notification;
    List<User> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv);
        name = findViewById(R.id.etName);
        age = findViewById(R.id.etAge);
        etId = findViewById(R.id.etId);
        add = findViewById(R.id.btnAdd);
        delete = findViewById(R.id.btnDelete);
        notification = findViewById(R.id.btnNotification);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        userViewModel.getAllUsers().observe(this, users -> {
            StringBuilder builder = new StringBuilder();
            textView.setText("");
            Log.d(TAG, "onChanged: " + users.toString());
            for (User user : users) {
                builder.append(user.toString()).append("\n\n");
            }

            textView.setText(builder.toString());

            userList.clear();
            userList.addAll(users);
        });


        add.setOnClickListener(v -> {
            String Name = name.getText().toString();
            int Age = Integer.parseInt(age.getText().toString());
            if (TextUtils.isEmpty(Name) && TextUtils.isEmpty(age.getText().toString())) {
                Toast.makeText(MainActivity.this, "please fill all the fields !!", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User(Name, Age);
            userViewModel.addUser(user);
            Toast.makeText(MainActivity.this, "Added !!", Toast.LENGTH_SHORT).show();
        });

        delete.setOnClickListener(v -> {
            String ID = etId.getText().toString();

            if (TextUtils.isEmpty(ID)) {
                Toast.makeText(MainActivity.this, "please enter user Id !!", Toast.LENGTH_SHORT).show();
                return;
            }

            int id = Integer.parseInt(ID);
            for (User user : userList)
                if (user.getId() == id) {
                    userViewModel.deleteUser(user);
                    Toast.makeText(MainActivity.this, "Deleted !!", Toast.LENGTH_SHORT).show();
                }


        });

        notification.setOnClickListener(v -> AppUtils.showFullScreenNotification(MainActivity.this));
        Toast.makeText(this, "Show Toast", Toast.LENGTH_SHORT).show();
    }

    public void fun(){

    }
}