package com.example.preet.dooramo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateServiceProvider2 extends Activity {

    private String name, phone, email, service;
    private EditText uname, password;
    private Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service_provider2);
        getExtras();
        initComponents();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()) {
                    if(checkExisting()) {
                        createNow();
                    }
                }
            }
        });
    }

    private void createNow() {
        DBHelper dbHelper = new DBHelper(CreateServiceProvider2.this);
        dbHelper.caller();
        long status = -1;

        status = dbHelper.createServiceProvider(name, phone, email, service, uname.getText().toString());
        if(status > 0) {
            long status1 = -1;
            dbHelper.caller();
            status1 = dbHelper.createServiceProviderAccount(uname.getText().toString(), password.getText().toString());
            if(status1 > 0) {
                Toast.makeText(this, "New service provider account has been created successfully",
                        Toast.LENGTH_SHORT).show();
                Intent close = new Intent(CreateServiceProvider2.this, ManagementHome.class);
                close.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(close);
            } else {
                Toast.makeText(this, "Error. Please contact developer. 1", Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(this, "Error. Please contact developer. 2", Toast.LENGTH_SHORT).show();
    }

    private boolean checkExisting() {
        DBHelper dbHelper = new DBHelper(CreateServiceProvider2.this);
        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();

        String query = "select * from 'serviceProviderAccount' where 'username' = '" +
                uname.getText().toString() + "'";

        Cursor next = dbcall.rawQuery(query, null);
        if(next.moveToNext()) {
            uname.setError("Selected username already exists");
            return false;
        } else
            return true;
    }

    private boolean valid() {
        if (uname.getText().toString().length() < 2) {
            uname.setError("Enter username");
            return false;
        } else if (password.getText().toString().length() < 5) {
            password.setError("Password must be 5 char");
            return false;
        }
        return true;
    }

    private void initComponents() {
        uname = findViewById(R.id.unameEditTextCSP2);
        password = findViewById(R.id.passwordEditText2CSP2);
        create = findViewById(R.id.creatButtonCSP2);
    }

    private void getExtras() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        service= intent.getStringExtra("service");
    }
}
