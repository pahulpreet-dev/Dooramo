package com.example.preet.dooramo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementHome extends AppCompatActivity {

    Button newUser, createManager, checkRequest, requestProviders, createService;
    //ManagementHome
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_home);
        setTitle("Dooramo Management");
        initComponents();
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, CreateUser.class));
            }
        });
        createManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, CreateManager.class));
            }
        });
        checkRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, CheckRequests.class));
            }
        });
        requestProviders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, SelectProviders.class));
            }
        });
        createService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, CreateServiceProvider.class));
            }
        });    }

    private void initComponents() {
        newUser = findViewById(R.id.userbuttonMH);
        createManager = findViewById(R.id.managerbuttonMH);
        checkRequest = findViewById(R.id.requestbuttonMH);
        requestProviders = findViewById(R.id.requestProvidersbutton2MH);
        createService = findViewById(R.id.SPbuttonMH);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
