package com.example.preet.dooramo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    Context con;
    static final String DB_NAME = "dooramoDB";
    static final int VERSION = 1;
    SQLiteDatabase dbcall;

    DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUserDetailsQ = "create table if not exists 'userInfo'('srno' integer primary key AUTOINCREMENT" +
                ", 'name' varchar(100), 'dob' varchar(100), 'email' varchar(100), 'aptNo' varchar(100)," +
                " 'contact' varchar(100), 'username' varchar(100))";

        String createUsernameQ = "create table if not exists 'userAccount'('srno' integer primary key AUTOINCREMENT" +
                ", 'username' varchar(100), 'password' varchar(100))";

        String createManagerQuery = "create table if not exists 'managerAccount'('srno' integer primary key AUTOINCREMENT" +
                ", 'username' varchar(100), 'password' varchar(100))";

        String createRequestQuery = "create table if not exists 'requests'('srno' integer primary key " +
                "AUTOINCREMENT" +
                ", 'username' varchar(100), 'request' varchar(500), 'status' varchar(100), 'dateTime' " +
                "varchar (100), 'service' varchar(100), 'name' varchar(100), 'email' varchar(100)," +
                " 'contact' varchar(100), 'aptNo' varchar(100))";

        String createServiceProviderQ = "create table if not exists 'serviceProviderInfo'('srno' " +
                "integer primary key AUTOINCREMENT" +
                ", 'name' varchar(100), 'phone' varchar(100), 'email' varchar(100)," +
                "'service' varchar(100), 'username' varchar(100))";

        String createServiceProviderAccountQ = "create table if not exists " +
                "'serviceProviderAccount'('srno' integer primary key AUTOINCREMENT" +
                ", 'username' varchar(100), 'password' varchar(100))";

        try {
            db.execSQL(createUserDetailsQ);
            db.execSQL(createUsernameQ);
            db.execSQL(createManagerQuery);
            db.execSQL(createRequestQuery);
            db.execSQL(createServiceProviderQ);
            db.execSQL(createServiceProviderAccountQ);
        } catch (Exception e) {
            Toast.makeText(con,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void caller()
    {
        try
        {

            dbcall=this.getWritableDatabase();

        }
        catch(Exception e)
        {
            Toast.makeText(con,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public long createUserDetails(String name,String dob, String email, String aptNo, String contact,
                                  String username)
    {

        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("dob", dob);
        cv.put("email",email);
        cv.put("aptNo", aptNo);
        cv.put("contact", contact);
        cv.put("username", username);
        return dbcall.insert("userInfo",null,cv);
    }


    public long createUsername(String username,String password)
    {

        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("password",password);

        return dbcall.insert("userAccount",null,cv);
    }
    public long createManager(String username,String password)
    {

        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("password",password);

        return dbcall.insert("managerAccount",null,cv);
    }

    public long createRequest(String username,String request, String status, String dateTime,
                              String service, String name, String email, String contact, String aptNo)
    {

        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("request",request);
        cv.put("status", status);
        cv.put("dateTime", dateTime);
        cv.put("service", service);
        cv.put("name", name);
        cv.put("email", email);
        cv.put("contact", contact);
        cv.put("aptNo", aptNo);

        return dbcall.insert("requests",null,cv);
    }
    public long updateRequestStatus(String id, String status) {
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        return dbcall.update("requests", cv, "srno = " + id, null);
    }

    public long createServiceProvider(String name,String phone, String email, String service, String username)
    {

        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("phone", phone);
        cv.put("email",email);
        cv.put("service", service);
        cv.put("username", username);
        return dbcall.insert("serviceProviderInfo",null,cv);
    }

    public long createServiceProviderAccount(String username, String password)
    {

        ContentValues cv=new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        return dbcall.insert("serviceProviderAccount",null,cv);
    }
}
