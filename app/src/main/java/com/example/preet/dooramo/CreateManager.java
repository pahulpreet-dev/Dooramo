package com.example.preet.dooramo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateManager extends AppCompatActivity {

    EditText username, password;
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
                if(validate()) {
                    if(checkManagerExistence())
                        createManagerr();
                }
            }
        });
    }

    private boolean checkManagerExistence() {
        DBHelper dbHelper = new DBHelper(CreateManager.this);
        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();

        String query = "select * from 'managerAccount' where 'username' = '" + username.getText().toString() + "'";

        Cursor next = dbcall.rawQuery(query, null);
        if(next.moveToNext()) {
            username.setError("Selected username already exists");
            return false;
        } else
            return true;
    }

    private void createManagerr() {
        DBHelper dbHelper = new DBHelper(CreateManager.this);
        dbHelper.caller();
        long status = -1;

        status = dbHelper.createManager(username.getText().toString(), password.getText().toString());
        if(status > 0) {
            Toast.makeText(this, "New manager account has been created successfully",
                    Toast.LENGTH_SHORT).show();
            Intent close = new Intent(CreateManager.this, ManagementHome.class);
            close.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(close);
        }
        else
            Toast.makeText(this, "Error. Please contact developer.", Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        if(username.getText().toString().length() < 1) {
            username.setError("Enter username");
            return false;
        } else if(password.getText().toString().length() <= 5) {
            password.setError("Password must be of length 5");
            return false;
        }
        return true;
    }


    private void initializeComponents() {
        username = findViewById(R.id.unameeditTextCM);
        password = findViewById(R.id.passeditTextCM);
        createManager = findViewById(R.id.createbuttonCM);
    }
}
