package com.example.preet.dooramo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class CreateServiceProvider2 extends Activity {

    private String name, phone, email, service;
    private EditText uname, password;
    private Button create;
    private String signUpFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service_provider2);
        getExtras();
        initComponents();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    createNowFirebase();
                }
            }
        });
    }

    //sign up the new user
    private void createNowFirebase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("serviceProviderAccount/usernames");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mUsername = uname.getText().toString();
                String mPassword = password.getText().toString();
                if (dataSnapshot.hasChild(mUsername)) {
                    Toast.makeText(CreateServiceProvider2.this, "Username exist", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                            .child("serviceProviderAccount/usernames");
                    ref.child(mUsername).child("password").setValue(mPassword);

                    if(signUpFlag.equals("management")) {
                        ref.child(mUsername).child("verification").setValue("done");
                    } else if(signUpFlag.equals("provider")) {
                        ref.child(mUsername).child("verification").setValue("pending");
                    }

                    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference()
                            .child("serviceProviderInfo/usernames");
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("name", name);
                    userInfo.put("phone", phone);
                    userInfo.put("email", email);
                    userInfo.put("service provided", service);
                    if(signUpFlag.equals("management"))
                        userInfo.put("verification", "done");
                    else if(signUpFlag.equals("provider"))
                        userInfo.put("verification", "pending");
                    ref2.child(mUsername).setValue(userInfo);

                    if(signUpFlag.equals("management")) {
                        Toast.makeText(CreateServiceProvider2.this,
                                "New service provider account has been created successfully",
                                Toast.LENGTH_SHORT).show();
                        Intent close = new Intent(CreateServiceProvider2.this, ManagementHome.class);
                        close.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(close);
                    } else if(signUpFlag.equals("provider")) {
                        Toast.makeText(CreateServiceProvider2.this,
                                "Request for registration has been sent to management",
                                Toast.LENGTH_SHORT).show();
                        Intent close = new Intent(CreateServiceProvider2.this,
                                LoginActivity.class);
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
    private boolean valid() {
        if (uname.getText().toString().length() < 2) {
            uname.setError("Enter username");
            return false;
        } else if (password.getText().toString().length() < 5) {
            password.setError("Password must be 5 char");
            return false;
        }
        return true;
    }

    //initialize the components
    private void initComponents() {
        uname = findViewById(R.id.unameEditTextCSP2);
        password = findViewById(R.id.passwordEditText2CSP2);
        create = findViewById(R.id.creatButtonCSP2);
    }

    //fetch the values passed from previous activity
    private void getExtras() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        service = intent.getStringExtra("service");
        signUpFlag = intent.getStringExtra("signUpFlag");
    }
}
