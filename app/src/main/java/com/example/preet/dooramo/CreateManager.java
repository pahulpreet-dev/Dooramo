package com.example.preet.dooramo;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CreateManager extends AppCompatActivity {

    EditText username, passwordet;
    Button createManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_manager);
        setTitle("Create New Manager");
        initializeComponents();

        createManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String _username = username.getText().toString();
                    String password = passwordet.getText().toString();
                    createManagerFirebase(_username, password);
                }
            }
        });
    }

    //sign up the new manager
    private void createManagerFirebase(final String _username, final String password) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("managerAccount/usernames");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(_username)) {
                    Toast.makeText(CreateManager.this, "Username exist", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("managerAccount");
                    ref.child("usernames").child(_username).child("password").setValue(password);
                    Toast.makeText(CreateManager.this, "Manager account created successfully"
                            , Toast.LENGTH_SHORT).show();
                    Intent close = new Intent(CreateManager.this, ManagementHome.class);
                    close.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(close);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    //input validations
    private boolean validate() {
        if (username.getText().toString().length() < 1) {
            username.setError("Enter username");
            return false;
        } else if (passwordet.getText().toString().length() <= 5) {
            passwordet.setError("Password must be of length 5");
            return false;
        }
        return true;
    }

    //initialize the components
    private void initializeComponents() {
        username = findViewById(R.id.unameeditTextCM);
        passwordet = findViewById(R.id.passeditTextCM);
        createManager = findViewById(R.id.createbuttonCM);
    }
}
