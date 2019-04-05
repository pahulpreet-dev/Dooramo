package com.example.preet.dooramo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RequestDetails extends AppCompatActivity {

    TextView reqTv, nameTv, serviceTv, emailTv, contactTv, aptNoTv, statusTv, dateTv;
    Button updateStatus, feedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        setTitle("View request details");
        initComponents();
        Intent get = getIntent();

        //check the flag, if the request is made by resident then the update button is not visible
        if (get.getStringExtra("residentFlag").equals("resident")) {
            updateStatus.setVisibility(View.GONE);
            feedbackButton.setVisibility(View.VISIBLE);
        }

        //populate the TextViews with the data
        reqTv.setText(get.getStringExtra("request"));
        nameTv.setText(nameTv.getText().toString() + get.getStringExtra("name"));
        serviceTv.setText(serviceTv.getText().toString() + get.getStringExtra("service"));
        emailTv.setText(emailTv.getText().toString() + get.getStringExtra("email"));
        contactTv.setText(contactTv.getText().toString() + get.getStringExtra("contact"));
        aptNoTv.setText(aptNoTv.getText().toString() + get.getStringExtra("aptNo"));
        statusTv.setText(statusTv.getText().toString() + get.getStringExtra("status"));
        dateTv.setText(dateTv.getText().toString() + get.getStringExtra("dateTime"));

        //update the status for the request
        updateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RequestDetails.this);
                builder.setMessage("Confirm status update to: Done")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                statusUpdate();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!statusTv.getText().toString().equals("Status: Done")) {
                    Toast.makeText(RequestDetails.this, "The request is still pending", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(RequestDetails.this, SubmitFeedback.class)
                            .putExtra("requestId", getIntent().getStringExtra("id")));
                }
            }
        });

    }

    //status update in the database
    private void statusUpdate() {

        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("status", "Done");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("requests");
        reference.child(getIntent().getStringExtra("id")).updateChildren(taskMap);
        statusTv.setText("Status: Done");
    }

    //initialize the components
    private void initComponents() {
        reqTv = findViewById(R.id.requesttextView13RD);
        nameTv = findViewById(R.id.nametextView14RD);
        serviceTv = findViewById(R.id.servicetextView14RD);
        emailTv = findViewById(R.id.emailtextView17RD);
        contactTv = findViewById(R.id.numberRDtextView16);
        aptNoTv = findViewById(R.id.aptnotextView15rd);
        statusTv = findViewById(R.id.statustextView18RD);
        dateTv = findViewById(R.id.datetextView19RD);
        updateStatus = findViewById(R.id.statusbuttonRD);
        feedbackButton = findViewById(R.id.feedbackbuttonRD);
    }
}
