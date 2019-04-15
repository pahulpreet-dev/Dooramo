package com.example.preet.dooramo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * purpose: to register new resident by management (get details)
 * author: Pahulpreet Singh and team
 * date: Nov 23, 2018
 * ver: 1
 *
 * ver: 2
 * update: the resident can register themselves as well. They can provide details and
 *         management will decide to accept or reject the request
 *
 */

public class CreateUser extends AppCompatActivity {

    private EditText name, dob, email, aptNo, number;
    private Button next;
    private String signUpFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        setTitle("Create New User");

        signUpFlag = getIntent().getStringExtra("signUpFlag");

        initComponents();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    startActivity(new Intent(CreateUser.this, CreateUser2.class)
                            .putExtra("name", name.getText().toString())
                            .putExtra("dob", dob.getText().toString())
                            .putExtra("email", email.getText().toString())
                            .putExtra("aptNo", aptNo.getText().toString())
                            .putExtra("number", number.getText().toString())
                            .putExtra("signUpFlag", signUpFlag));

                }
            }
        });
    }

    //validate the inputs
    private boolean validate() {
        if (name.getText().toString().length() < 2) {
            name.setError("Enter name");
            return false;
        } else if (dob.getText().toString().length() < 2) {
            dob.setError("Enter DOB");
            return false;
        } else if (email.getText().toString().length() < 2) {
            email.setError("Enter E-mail");
            return false;
        } else if (aptNo.getText().toString().length() < 1) {
            aptNo.setError("Enter Apartment Number");
            return false;
        } else if (number.getText().toString().length() != 10) {
            number.setError("Enter valid Contact Number");
            return false;
        }
        return true;
    }

    //initialize the components
    private void initComponents() {
        name = findViewById(R.id.nameeditText2CU);
        dob = findViewById(R.id.dobeditText3Cu);
        email = findViewById(R.id.emaileditTextcu);
        aptNo = findViewById(R.id.aptNoeditText5cu);
        next = findViewById(R.id.nextbuttoncu);
        number = findViewById(R.id.numbereditText3CU);
    }
}
