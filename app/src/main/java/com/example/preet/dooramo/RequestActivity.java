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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RequestActivity extends AppCompatActivity {
    private String serviceRequest, name, aptNo, email, contact, username;
    Button requestButton;
    EditText explainET;
    @Override
    //just an update
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        serviceRequest = getIntent().getStringExtra("serviceName");
        initComponents();
        requestButton.setText("Request " + serviceRequest);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    sendRequestToManagement();

                    //request will be sent to management
                }
            }
        });
    }

    private void sendRequestToManagement() {
        DBHelper dbHelper = new DBHelper(RequestActivity.this);
        dbHelper.caller();
        long status = -1;

        //getting date and time
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM, yyyy - hh:mm");
        String formattedDate = df.format(c);
        Log.d("DATEEE", formattedDate);

        getDetailsFromPreferences();

        status = dbHelper.createRequest(username, explainET.getText().toString(), "Pending",
                formattedDate, serviceRequest, name, email, contact, aptNo);
        if(status > 0) {
            Toast.makeText(RequestActivity.this, "Request has been sent" +
                            " to the management", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RequestActivity.this, ServicesActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else {
            Toast.makeText(this, "Error. contact developer", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDetailsFromPreferences() {
        SharedPreferences sharedPref = RequestActivity.this.getSharedPreferences("ForLogin",
                Context.MODE_PRIVATE);
        name = sharedPref.getString("name", "n");
        email = sharedPref.getString("email", "n");
        contact = sharedPref.getString("contact", "n");
        aptNo = sharedPref.getString("aptNo", "n");
        username = sharedPref.getString("name", "n");
    }

    private boolean validate() {
        if(explainET.getText().toString().length() < 5) {
            explainET.setError("Please explain in detail");
            return false;
        } else
            return true;
    }

    private void initComponents() {
        requestButton = findViewById(R.id.requestButtonUser);
        explainET = findViewById(R.id.explain_editText);
    }
}