package com.example.preet.dooramo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RequestActivity extends AppCompatActivity {
    private String serviceRequest, name, aptNo, email, contact, username;
    Button requestButton;
    EditText explainET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        setTitle("Request");
        serviceRequest = getIntent().getStringExtra("serviceName");
        initComponents();
        requestButton.setText("Request " + serviceRequest);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    sendRequestToManagement();
                }
            }
        });
    }

    //create new request
    private void sendRequestToManagement() {
        getDetailsFromPreferences();
        //getting system date and time
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM, yyyy - hh:mm");
        String formattedDate = df.format(c);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("requests");

        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("request", explainET.getText().toString());
        data.put("status", "Pending");
        data.put("dateTime", formattedDate);
        data.put("service", serviceRequest);
        data.put("name", name);
        data.put("email", email);
        data.put("contact", contact);
        data.put("apartment number", aptNo);

        databaseReference.push().setValue(data);

        Toast.makeText(RequestActivity.this, "Request has been sent" +
                " to the management", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RequestActivity.this, ServicesActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    //fetch residet details from preferences
    private void getDetailsFromPreferences() {
        SharedPreferences sharedPref = RequestActivity.this.getSharedPreferences("ForLogin",
                Context.MODE_PRIVATE);
        name = sharedPref.getString("name", "n");
        email = sharedPref.getString("email", "n");
        contact = sharedPref.getString("contact", "n");
        aptNo = sharedPref.getString("aptNo", "n");
        username = sharedPref.getString("name", "n");
    }

    //validate the inputs
    private boolean validate() {
        if (explainET.getText().toString().length() < 5) {
            explainET.setError("Please explain in detail");
            return false;
        } else
            return true;
    }

    //initialize the components
    private void initComponents() {
        requestButton = findViewById(R.id.requestButtonUser);
        explainET = findViewById(R.id.explain_editText);
    }
}