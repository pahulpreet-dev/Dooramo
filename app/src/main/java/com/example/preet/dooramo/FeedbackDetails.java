package com.example.preet.dooramo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 * purpose: for management to see the details of a particular feedback
 * author: Pahulpreet Singh and team
 * date: Apr 7, 2019
 * ver: 1
 *
 */

public class FeedbackDetails extends AppCompatActivity {

    TextView reqTv, nameTv, serviceTv, emailTv, contactTv, aptNoTv, feedbackTv, dateTv;
    Button updateStatus;
    HashMap<String, String> feedback, requestDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        setTitle("Feedback Details");
        initComponents();
        getDetails();
        reqTv.setText(requestDetails.get("request"));
        nameTv.setText(nameTv.getText().toString() + requestDetails.get("name"));
        serviceTv.setText(serviceTv.getText().toString() + requestDetails.get("service"));
        emailTv.setText(emailTv.getText().toString() + requestDetails.get("email"));
        contactTv.setText(contactTv.getText().toString() + requestDetails.get("contact"));
        aptNoTv.setText(aptNoTv.getText().toString() + requestDetails.get("aptNo"));
        feedbackTv.setText("Rating: " + feedback.get("rating") + "\nComment: " + feedback.get("comment"));
        dateTv.setText(dateTv.getText().toString() + requestDetails.get("dateTime"));
    }

    //fetch feedback and request details from intent
    private void getDetails() {
        Intent intent = getIntent();
        feedback = (HashMap<String, String>) intent.getSerializableExtra("feedback");
        requestDetails = (HashMap<String, String>) intent.getSerializableExtra("requestDetails");

    }

    //initialize the components
    private void initComponents() {
        reqTv = findViewById(R.id.requesttextView13RD);
        nameTv = findViewById(R.id.nametextView14RD);
        serviceTv = findViewById(R.id.servicetextView14RD);
        emailTv = findViewById(R.id.emailtextView17RD);
        contactTv = findViewById(R.id.numberRDtextView16);
        aptNoTv = findViewById(R.id.aptnotextView15rd);
        feedbackTv = findViewById(R.id.statustextView18RD);
        dateTv = findViewById(R.id.datetextView19RD);
        updateStatus = findViewById(R.id.statusbuttonRD);
        updateStatus.setVisibility(View.GONE);
    }
}