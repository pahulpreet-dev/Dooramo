package com.example.preet.dooramo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private int intentFlag;
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
            startActivity(intent);
        }
        if(!checkPreferences()) {
            Intent intent = new Intent(LoginActivity.this, ServicesActivity.class);
            finish();
            startActivity(intent);
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidLogin()) {
                    if(intentFlag == 0) {
                        updatePrefrences();
                        Intent intent = new Intent(LoginActivity.this, ServicesActivity.class);
                        finish();
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (intentFlag == 1) {
                        Intent intent = new Intent(LoginActivity.this, ManagementHome.class);
                        finish();
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private boolean checkPreferences() {
        SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("ForLogin",
                Context.MODE_PRIVATE);
        String login = sharedPref.getString("name", "n");
        return login.equals("n");
    }

    private void updatePrefrences() {
        SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("ForLogin",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", username.getText().toString());
        editor.commit();
    }

    private boolean checkValidLogin() {
        DBHelper dbHelper = new DBHelper(LoginActivity.this);
        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();

        String query = "select * from userAccount where username = '" + username.getText().toString()
                + "' AND password = '" + password.getText().toString() + "'";

        Cursor next = dbcall.rawQuery(query, null);
        if(next.moveToNext()) {
            intentFlag = 0;
            return true;
        } else {
            String queryM = "select * from managerAccount where username = '" + username.getText().toString()
                    + "' AND password = '" + password.getText().toString() + "'";

            Cursor nextM = dbcall.rawQuery(queryM, null);
            if(nextM.moveToNext()) {
                intentFlag = 1;
                return true;
            } else {
                return false;
            }
            //return false;
        }
    }

    private boolean checkUserExistence() {
        DBHelper dbHelper = new DBHelper(LoginActivity.this);
        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();
        String query = "select * from userAccount";

        Cursor next = dbcall.rawQuery(query, null);
        if(next.moveToNext()) {
            return true;
        } else {
            String query2 = "select * from managerAccount";
            Cursor nextM = dbcall.rawQuery(query2, null);
            if(nextM.moveToNext()) {
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
