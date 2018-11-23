package com.example.preet.dooramo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementHome extends AppCompatActivity {

    Button newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_home);
        initComponents();
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, CreateUser.class));
            }
        });
    }

    private void initComponents() {
        newUser = findViewById(R.id.userbuttonMH);
    }
}
