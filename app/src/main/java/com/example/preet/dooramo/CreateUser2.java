package com.example.preet.dooramo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUser2 extends AppCompatActivity {

    private String name, dob, email, aptNo, number;
    private EditText username, password;
    private Button createUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user2);
        getExtras();
        initComponents();
        createUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    if(checkUserExistance()) {
                        createUserNow();
                    }
                }
            }
        });

    }

    private void createUserNow() {
        DBHelper dbHelper = new DBHelper(CreateUser2.this);
        dbHelper.caller();
        long status = -1;

        status = dbHelper.createUserDetails(name, dob, email, aptNo, number);
        if(status > 1) {
            long status1 = -1;
            status1 = dbHelper.createUsername(username.getText().toString(), password.getText().toString());
            if(status1 > 1) {
                Toast.makeText(this, "New user account has been created successfully",
                        Toast.LENGTH_SHORT).show();
                Intent close = new Intent(CreateUser2.this, ManagementHome.class);
                close.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(close);
            } else
                Toast.makeText(this, "Error. Please contact developer. 1", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Error. Please contact developer. 2", Toast.LENGTH_SHORT).show();
    }

    private boolean checkUserExistance() {
        DBHelper dbHelper = new DBHelper(CreateUser2.this);
        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();

        String query = "select * from 'userAccount' where 'username' = '" + username.getText().toString() + "'";

        Cursor next = dbcall.rawQuery(query, null);
        if(next.moveToNext()) {
            username.setError("Selected username already exists");
            return false;
        } else
            return true;

    }

    private boolean validate() {
        if(username.getText().toString().length() < 1) {
            username.setError("Enter username");
            return false;
        } else if(password.getText().toString().length() <= 5) {
            password.setError("Password must be of length 5");
            return false;
        }
        return true;
    }

    private void initComponents() {
        username = findViewById(R.id.unameeditTextCU2);
        password = findViewById(R.id.passeditText2cu2);
        createUserBtn = findViewById(R.id.createbuttoncu2);
    }

    private void getExtras() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        dob = intent.getStringExtra("dob");
        email = intent.getStringExtra("email");
        aptNo = intent.getStringExtra("aptNo");
        number = intent.getStringExtra("number");
    }
}
