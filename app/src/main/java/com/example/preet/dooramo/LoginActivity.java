package com.example.preet.dooramo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText username, password;
    //LoginActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gin);
        initComponents();
        if(checkUserExistence()) {

        } else {
            Intent intent = new Intent(LoginActivity.this, ManagementHome.class);
            finish();
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, ServicesActivity.class));
                if(checkValidLogin()) {
                    Intent intent = new Intent(LoginActivity.this, ServicesActivity.class);
                    finish();
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
    private boolean checkValidLogin() {
        DBHelper dbHelper = new DBHelper(LoginActivity.this);
        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();

        String query = "select * from userAccount where username = '" + username.getText().toString()
                + "' AND password = '" + password.getText().toString() + "'";

        Cursor next = dbcall.rawQuery(query, null);
        if(next.moveToNext()) {
            return true;
        } else {
            username.setError("Invalid Username/Password");
            return false;
        }
    }

    private boolean checkUserExistence() {
        DBHelper dbHelper = new DBHelper(LoginActivity.this);
        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();
        String query = "select * from userAccount";

        Cursor next = dbcall.rawQuery(query, null);
        if(next.moveToNext()) {
            return true;
        } else
            return false;
    }

    private void initComponents() {
        loginBtn = findViewById(R.id.lgnBtn);
        username = findViewById(R.id.unameeditText);
        password = findViewById(R.id.passwordeditText2);
    }
}
