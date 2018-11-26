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

public class RequestDetails extends AppCompatActivity {

    TextView reqTv, nameTv, serviceTv, emailTv, contactTv, aptNoTv, statusTv, dateTv;
    Button updateStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        setTitle("View request details");
        initComponents();
        Intent get = getIntent();
        reqTv.setText(get.getStringExtra("request"));
        nameTv.setText(nameTv.getText().toString() + get.getStringExtra("name"));
        serviceTv.setText(serviceTv.getText().toString() + get.getStringExtra("service"));
        emailTv.setText(emailTv.getText().toString() + get.getStringExtra("email"));
        contactTv.setText(contactTv.getText().toString() + get.getStringExtra("contact"));
        aptNoTv.setText(aptNoTv.getText().toString() + get.getStringExtra("aptNo"));
        statusTv.setText(statusTv.getText().toString() + get.getStringExtra("status"));
        dateTv.setText(dateTv.getText().toString() + get.getStringExtra("dateTime"));

        updateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RequestDetails.this);
                builder.setMessage("COnfirm status update to: Done")
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

    }

    private void statusUpdate() {
        DBHelper dbHelper = new DBHelper(RequestDetails.this);
        dbHelper.caller();
        long status = -1;
        status = dbHelper.updateRequestStatus(getIntent().getStringExtra("id"), "Done");
        if(status > 0) {
            Toast.makeText(this, "Status updated", Toast.LENGTH_SHORT).show();
            statusTv.setText("Status: Done");
        }
    }

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
    }
}
