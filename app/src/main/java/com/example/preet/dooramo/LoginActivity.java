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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * purpose: A common place for everyone to login. Users: Residents, Service providers and management
 * author: Pahulpreet Singh and team
 * date: Nov 20, 2018
 * ver: 6
 *
 */

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText username, password;
    private String service;
    private TextView signUpUser, signUpProvider;

    //LoginActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gin);
        initComponents();
        if (!checkPreferences()) {
            Intent intent = new Intent(LoginActivity.this, ServicesActivity.class);
            finish();
            startActivity(intent);
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginDetails();
            }
        });
        signUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateUser.class)
                    .putExtra("signUpFlag", "user"));
            }
        });
        signUpProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateServiceProvider.class)
                        .putExtra("signUpFlag", "provider"));
            }
        });
    }

    /**
     * check if resident already logged in.
     * not checking for management and service providers because we want them to login everytime
     * for security purposes
     */
    private boolean checkPreferences() {
        SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("ForLogin",
                Context.MODE_PRIVATE);
        String login = sharedPref.getString("name", "n");
        return login.equals("n");
    }

    /**
     * update the preferences when a resident logs in
     */
    private void updatePrefrences(final String user) {
        SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("ForLogin",
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("userInfo/usernames");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.child(user);
                editor.putString("name", dataSnapshot1.child("name").getValue(String.class));
                editor.putString("email", dataSnapshot1.child("email").getValue(String.class));
                editor.putString("aptNo", dataSnapshot1.child("apartment number").getValue(String.class));
                editor.putString("contact", dataSnapshot1.child("number").getValue(String.class));
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, ServicesActivity.class);
                finish();
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //authentication
    private void checkLoginDetails() {
        final String mUsername = username.getText().toString();
        final String mPassword = password.getText().toString();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("userAccount/usernames");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(mUsername)) {
                    String checkVerification = dataSnapshot.child(mUsername).child("verification")
                            .getValue(String.class);
                    if(checkVerification.equals("done")) {
                        DataSnapshot dataSnapshot1 = dataSnapshot.child(mUsername).child("password");
                        String checkPassword = dataSnapshot1.getValue(String.class);
                        if (checkPassword.equals(mPassword)) {
                            updatePrefrences(mUsername);
                        } else {
                            username.setError("Invalid username/password");
                            Toast.makeText(LoginActivity.this,
                                    "Invalid username/password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Your account is pending verification",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("managerAccount/usernames");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(mUsername)) {
                                String checkPassword = dataSnapshot.child(mUsername)
                                        .child("password").getValue(String.class);
                                if (checkPassword.equals(mPassword)) {
                                    Intent intent = new Intent(LoginActivity.this, ManagementHome.class);
                                    finish();
                                    startActivity(intent);
                                } else {
                                    username.setError("Invalid username/password");
                                    Toast.makeText(LoginActivity.this,
                                            "Invalid username/password",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                        .getReference().child("serviceProviderAccount/usernames");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild(mUsername)) {
                                            String checkVerification = dataSnapshot.child(mUsername)
                                                    .child("verification")
                                                    .getValue(String.class);
                                            if(checkVerification.equals("done")) {
                                                String checkPassword = dataSnapshot.child(mUsername)
                                                        .child("password")
                                                        .getValue(String.class);
                                                if (checkPassword.equals(mPassword)) {
                                                    getProvidedService(mUsername);
                                                } else {
                                                    username.setError("Invalid username/password");
                                                    Toast.makeText(LoginActivity.this,
                                                            "Invalid username/password",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            else {
                                                Toast.makeText(LoginActivity.this,
                                                        "Your account is pending verification",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            username.setError("Invalid username/password");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //get service provider's details
    private void getProvidedService(final String user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("serviceProviderInfo/usernames");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                service = dataSnapshot.child(user).child("service provided").getValue(String.class);
                Intent intent = new Intent(LoginActivity.this, ProviderHome.class);
                intent.putExtra("service", service);
                finish();
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //initialize the components
    private void initComponents() {
        loginBtn = findViewById(R.id.lgnBtn);
        username = findViewById(R.id.unameeditText);
        password = findViewById(R.id.passwordeditText2);
        signUpUser = findViewById(R.id.signUptextViewUser);
        signUpProvider = findViewById(R.id.signUptextViewProvider);
    }
}
