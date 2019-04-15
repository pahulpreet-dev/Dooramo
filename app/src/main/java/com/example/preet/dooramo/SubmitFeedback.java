package com.example.preet.dooramo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitFeedback extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText commentEt;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_feedback);
        initComponents();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(submitFeedback()){
                    Intent close = new Intent(SubmitFeedback.this, ResidentMyJobs.class);
                    close.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(close);
                }
            }
        });
    }

    //submit feedback

    /**
     *As the resident hits the submit feedback we are creating a new node in database named
     * "requestFeedback" that has the child node with id same as that of the id of request and
     * then it further have child nodes that has rating and comments stored in it.
     * Structure of the firebase is as below:
        requestFeedback: {
            requestID: { //same as id of the original request
                 rating: ""
                 comment: ""
            }
        }
     */
    private boolean submitFeedback() {
        String requestId = getIntent().getStringExtra("requestId");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("requestFeedback");
        db.child(requestId).child("rating").setValue(String.valueOf((int)ratingBar.getRating()));
        db.child(requestId).child("comment").setValue(commentEt.getText().toString());
        return true;
    }

    //initialize the components
    private void initComponents() {
        ratingBar = findViewById(R.id.ratingBarSF);
        commentEt = findViewById(R.id.feedbackeditTextSF);
        submitBtn = findViewById(R.id.submitButtonSF);
    }
}
