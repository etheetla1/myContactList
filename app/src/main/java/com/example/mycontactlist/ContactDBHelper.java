package com.example.mycontactlist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "mycontact.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_CONTACT = "" +
            "create table contact (_id integer primary key autoincrement," +
            "city text, state text, zipcode text," +
            "phonenumber text, cellnumber text," +
            "email text, birthday text);";

    public ContactDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ContactDBHelper.class.getName(),
                "Upgrading to new version");

        db.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(db);
    }
}
