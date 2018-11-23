package com.example.preet.dooramo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
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
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ServicesActivity.class));
            }
        });
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
    }
}
