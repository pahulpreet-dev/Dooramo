package com.example.preet.dooramo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RequestActivity extends AppCompatActivity {
    String serviceRequest;
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
                    Toast.makeText(RequestActivity.this, "Request has been sent" +
                            " to the management", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RequestActivity.this, ServicesActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    //request will be sent to management
                }
            }
        });
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