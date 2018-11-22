package com.example.preet.dooramo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateUser extends AppCompatActivity {

    private EditText name, dob, email,aptNo,number;
    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        initComponents();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    startActivity(new Intent(CreateUser.this, CreateUser2.class)
                            .putExtra("name", name.getText().toString())
                            .putExtra("dob", dob.getText().toString())
                            .putExtra("email", email.getText().toString())
                            .putExtra("aptNo", aptNo.getText().toString())
                            .putExtra("number", number.getText().toString()));

                }
            }
        });
    }

    private boolean validate() {
        if(name.getText().toString().length() < 2) {
            name.setError("Enter name");
            return false;
        } else if(dob.getText().toString().length() < 2) {
            dob.setError("Enter DOB");
            return false;
        } else if(email.getText().toString().length() < 2) {
            email.setError("Enter E-mail");
            return false;
        } else if(aptNo.getText().toString().length() < 1) {
            aptNo.setError("Enter Apartment Number");
            return false;
        } else if(number.getText().toString().length() != 10) {
            number.setError("Enter valid Contact Number");
            return false;
        }
        return true;
    }

    private void initComponents() {
        name = findViewById(R.id.nameeditText2CU);
        dob = findViewById(R.id.dobeditText3Cu);
        email = findViewById(R.id.emaileditTextcu);
        aptNo = findViewById(R.id.aptNoeditText5cu);
        next = findViewById(R.id.nextbuttoncu);
        number = findViewById(R.id.numbereditText3CU);
    }
}
