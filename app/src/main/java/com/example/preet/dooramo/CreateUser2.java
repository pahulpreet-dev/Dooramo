package com.example.preet.dooramo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CreateUser2 extends AppCompatActivity {

    private String name, dob, email, aptNo, number;
    private EditText username, password;
    private Button createUserBtn;
    private String signUpFlag;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user2);
        setTitle("Create New User");
        getExtras();
        initComponents();
        createUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    createUserNowFirebase();
                }
            }
        });

    }

    //sign up new user
    private void createUserNowFirebase() {
        SharedPreferences sharedPref = CreateUser2.this.getSharedPreferences("ForLogin",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        editor.apply();
        ref = FirebaseDatabase.getInstance().getReference().child("userAccount/usernames");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mUsername = username.getText().toString();
                String mPassword = password.getText().toString();
                if (dataSnapshot.hasChild(mUsername)) {
                    Toast.makeText(CreateUser2.this, "Username exist", Toast.LENGTH_SHORT).show();
                } else {
                    //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("userAccount/usernames");
                    ref.child(mUsername).child("password").setValue(mPassword);

                    if(signUpFlag.equals("management"))
                        ref.child(mUsername).child("verification").setValue("done");
                    else if(signUpFlag.equals("user"))
                        ref.child(mUsername).child("verification").setValue("pending");

                    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("userInfo/usernames");
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("name", name);
                    userInfo.put("Date of Birth", dob);
                    userInfo.put("email", email);
                    userInfo.put("number", number);
                    userInfo.put("apartment number", aptNo);
                    if(signUpFlag.equals("management"))
                        userInfo.put("verification", "done");
                    else if(signUpFlag.equals("user"))
                        userInfo.put("verification", "pending");
                    ref2.child(mUsername).setValue(userInfo);
                    if(signUpFlag.equals("management")) {
                        Toast.makeText(CreateUser2.this, "New user account has been" +
                                        " created successfully",
                                Toast.LENGTH_SHORT).show();
                        Intent close = new Intent(CreateUser2.this, ManagementHome.class);
                        close.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(close);
                    } else if(signUpFlag.equals("user")) {
                        Toast.makeText(CreateUser2.this, "Request for registration has " +
                                        "been sent to management",
                                Toast.LENGTH_SHORT).show();
                        Intent close = new Intent(CreateUser2.this, LoginActivity.class);
                        close.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(close);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //validate the inputs
    private boolean validate() {
        if (username.getText().toString().length() < 1) {
            username.setError("Enter username");
            return false;
        } else if (password.getText().toString().length() <= 5) {
            password.setError("Password must be of length 5");
            return false;
        }
        return true;
    }

    //initialize the components
    private void initComponents() {
        username = findViewById(R.id.unameeditTextCU2);
        password = findViewById(R.id.passeditText2cu2);
        createUserBtn = findViewById(R.id.createbuttoncu2);
    }

    //fetch the data passed from previous activity
    private void getExtras() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        dob = intent.getStringExtra("dob");
        email = intent.getStringExtra("email");
        aptNo = intent.getStringExtra("aptNo");
        number = intent.getStringExtra("number");
        signUpFlag = intent.getStringExtra("signUpFlag");
    }
}
