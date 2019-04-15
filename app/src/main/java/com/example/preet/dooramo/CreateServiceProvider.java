package com.example.preet.dooramo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * purpose: to register new service provider by management (get details)
 * author: Pahulpreet Singh and team
 * date: Nov 23, 2018
 * ver: 1
 *
 * ver: 2
 * update: the service provider can register themselves as well. They can provide details and
 *         management will decide to accept or reject the request
 *
 */
public class CreateServiceProvider extends AppCompatActivity {

    private EditText name, phone, email;
    private Spinner serviceProvided;
    private Button next;
    private String signUpFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service_provider);

        initComponents();

        initSpinner();

        signUpFlag = getIntent().getStringExtra("signUpFlag");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    Intent next = new Intent(CreateServiceProvider.this, CreateServiceProvider2.class);
                    next.putExtra("name", name.getText().toString());
                    next.putExtra("phone", phone.getText().toString());
                    next.putExtra("email", email.getText().toString());
                    next.putExtra("service", serviceProvided.getSelectedItem().toString());
                    next.putExtra("signUpFlag", signUpFlag);
                    startActivity(next);
                }
            }
        });

    }

    //validating the inputs
    private boolean valid() {

        if (name.getText().toString().length() < 2) {
            name.setError("Enter a valid name");
            return false;
        } else if (phone.getText().toString().length() < 10) {
            phone.setError("Enter a valid phone number");
            return false;
        } else if (email.getText().toString().length() < 2) {
            email.setError("Enter a valid email id");
            return false;
        } else if (serviceProvided.getSelectedItemPosition() == 0) {
            name.setError("Select the service provided");
            return false;
        }

        return true;
    }

    //initializing the spinner with static values
    private void initSpinner() {
        String[] spinnerEntries = {"Select", "Carpenter", "Plumber", "Electrician"};
        serviceProvided.setAdapter(new ArrayAdapter<>(CreateServiceProvider.this,
                android.R.layout.simple_spinner_item, spinnerEntries));
    }

    //initialize the components
    private void initComponents() {
        name = findViewById(R.id.nameeditTextCSP);
        phone = findViewById(R.id.phoneEditTextCSP);
        email = findViewById(R.id.emailEditTextCSP);
        serviceProvided = findViewById(R.id.serviceSpinnerCSP);
        next = findViewById(R.id.nextButtonCSP);
    }
}