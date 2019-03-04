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

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText username, password;
    private int intentFlag;
    private String service;

    //LoginActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gin);
        initComponents();
        if (checkUserExistence()) {
            if (!checkPreferences()) {
                Intent intent = new Intent(LoginActivity.this, ServicesActivity.class);
                finish();
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(LoginActivity.this, ManagementHome.class);
            finish();
            startActivity(intent);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginDetails();
//                if(checkLoginDetails()) {
//                    if(intentFlag == 0) {
//                        updatePrefrences();
//
//                    } else if (intentFlag == 1) {
//                        Intent intent = new Intent(LoginActivity.this, ManagementHome.class);
//                        finish();
//                        startActivity(intent);
//                    } else if (intentFlag == 2) {
//                        Intent intent = new Intent(LoginActivity.this, ProviderHome.class);
//                        intent.putExtra("service", service);
//                        finish();
//                        startActivity(intent);
//                    }
//                }
            }
        });
    }

    private boolean checkPreferences() {
        SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("ForLogin",
                Context.MODE_PRIVATE);
        String login = sharedPref.getString("name", "n");
        return login.equals("n");
    }

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
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        DBHelper dbHelper = new DBHelper(LoginActivity.this);
//        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();
//
//        String query = "select * from userInfo where username = '" + username.getText().toString()
//                + "'";
//
//        Cursor next = dbcall.rawQuery(query, null);
//        if(next.moveToNext()) {
//            editor.putString("username", username.getText().toString());
//            String name = next.getString(next.getColumnIndex("name"));
//            Log.d("NAMEEE", name);
//            editor.putString("name", name);
//            editor.putString("email", next.getString(next.getColumnIndex("email")));
//            editor.putString("aptNo", next.getString(next.getColumnIndex("aptNo")));
//            editor.putString("contact", next.getString(next.getColumnIndex("contact")));
//            editor.commit();
//            Intent intent = new Intent(LoginActivity.this, ServicesActivity.class);
//            finish();
//            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }

    }

    private void checkLoginDetails() {
        final String mUsername = username.getText().toString();
        final String mPassword = password.getText().toString();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("userAccount/usernames");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(mUsername)) {
                    DataSnapshot dataSnapshot1 = dataSnapshot.child(mUsername).child("password");
                    String checkPassword = dataSnapshot1.getValue(String.class);
                    if (checkPassword.equals(mPassword)) {
                        updatePrefrences(mUsername);
                        Toast.makeText(LoginActivity.this, "GOTCHA", Toast.LENGTH_SHORT).show();
                    } else {
                        username.setError("Invalid username/password");
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
                                }
                            } else {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                        .getReference().child("serviceProviderAccount/usernames");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild(mUsername)) {
                                            String checkPassword = dataSnapshot.child(mUsername)
                                                    .child("password")
                                                    .getValue(String.class);
                                            if (checkPassword.equals(mPassword)) {
                                                getProvidedService(mUsername);
                                            } else {
                                                username.setError("Invalid username/password");
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

    private boolean checkValidLogin() {
        DBHelper dbHelper = new DBHelper(LoginActivity.this);
        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();

        String query = "select * from userAccount where username = '" + username.getText().toString()
                + "' AND password = '" + password.getText().toString() + "'";

        Cursor next = dbcall.rawQuery(query, null);
        if (next.moveToNext()) {
            intentFlag = 0;
            return true;
        } else {
            String queryM = "select * from managerAccount where username = '" + username.getText().toString()
                    + "' AND password = '" + password.getText().toString() + "'";

            Cursor nextM = dbcall.rawQuery(queryM, null);
            if (nextM.moveToNext()) {
                intentFlag = 1;
                return true;
            } else {
                String queryS = "select * from serviceProviderAccount where username = '" + username.getText().toString()
                        + "' AND password = '" + password.getText().toString() + "'";
                Cursor nextS = dbcall.rawQuery(queryS, null);
                if (nextS.moveToNext()) {
                    String queryS2 = "select * from serviceProviderInfo where username = '" + username.getText().toString() + "'";
                    Cursor nextS2 = dbcall.rawQuery(queryS2, null);
                    if (nextS2.moveToNext()) {
                        service = nextS2.getString(nextS2.getColumnIndex("service"));
                    }
                    intentFlag = 2;
                    return true;
                } else {
                    username.setError("Invalid Username/Password");
                    return false;
                }
            }
            //return false;
        }
    }

    private boolean checkUserExistence() {
        DBHelper dbHelper = new DBHelper(LoginActivity.this);
        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();
        String query = "select * from userAccount";

        Cursor next = dbcall.rawQuery(query, null);
        if (next.moveToNext()) {
            return true;
        } else {
            String query2 = "select * from managerAccount";
            Cursor nextM = dbcall.rawQuery(query2, null);
            if (nextM.moveToNext()) {
                return true;
            } else
                return false;
        }
        //return false;
    }

    private void initComponents() {
        loginBtn = findViewById(R.id.lgnBtn);
        username = findViewById(R.id.unameeditText);
        password = findViewById(R.id.passwordeditText2);
    }
}
