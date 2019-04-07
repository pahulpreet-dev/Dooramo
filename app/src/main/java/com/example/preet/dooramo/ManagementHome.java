package com.example.preet.dooramo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementHome extends AppCompatActivity {

    Button newUser, createManager, checkRequest, requestProviders, createService,
            regRequests, checkFeedback;

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
                startActivity(new Intent(ManagementHome.this, CreateUser.class)
                .putExtra("signUpFlag", "management"));
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
                startActivity(new Intent(ManagementHome.this, CreateServiceProvider.class)
                        .putExtra("signUpFlag", "management"));
            }
        });
        //click listener for checking self registration requests
        regRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, RegistrationRequests.class));
            }
        });
        checkFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementHome.this, CheckFeedback.class));
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
        regRequests = findViewById(R.id.registarationReqsbuttonMH);
        checkFeedback = findViewById(R.id.checkFeedbackbuttonMH);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
