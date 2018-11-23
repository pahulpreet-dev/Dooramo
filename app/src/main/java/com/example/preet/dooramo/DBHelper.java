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
                " 'contact' varchar(100))";

        String createUsernameQ = "create table if not exists 'userAccount'('srno' integer primary key AUTOINCREMENT" +
                ", 'username' varchar(100), 'password' varchar(100))";

        String createManagerQuery = "create table if not exists 'managerAccount'('srno' integer primary key AUTOINCREMENT" +
                ", 'username' varchar(100), 'password' varchar(100))";

        try {
            db.execSQL(createUserDetailsQ);
            db.execSQL(createUsernameQ);
            db.execSQL(createManagerQuery);
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
    String createUserDetailsQ = "create table if not exists 'userInfo'('srno' integer primary key AUTOINCREMENT" +
            ", 'name' varchar(100), 'dob' varchar(100), 'email' varchar(100), 'aptNo' varchar(100)," +
            " 'contact' varchar(100))";
    public long createUserDetails(String name,String dob, String email, String aptNo, String contact)
    {

        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("dob", dob);
        cv.put("email",email);
        cv.put("aptNo", aptNo);
        cv.put("contact", contact);
        return dbcall.insert("userInfo",null,cv);
    }

    String createUsernameQ = "create table if not exists 'userAccount'('srno' integer primary key AUTOINCREMENT" +
            ", 'username' varchar(100), 'password' varchar(100))";
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
}
