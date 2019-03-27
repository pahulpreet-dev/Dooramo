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
        //click listener for create new user
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, CreateUser.class));
            }
        });
        //click listener for create new manager
        createManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, CreateManager.class));
            }
        });
        //click listener for check requests
        checkRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, CheckRequests.class));
            }
        });
        //click listener for select providers
        requestProviders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, SelectProviders.class));
            }
        });
        //click listener for create new service provider
        createService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, CreateServiceProvider.class));
            }
        });
    }

    //initialize the components
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
