package com.example.mycontactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ContactDataSource {
    private SQLiteDatabase database;
    private ContactDBHelper dbHelper;

    public ContactDataSource(Context context){
        dbHelper = new ContactDBHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public boolean insertContact(Contact c){
        boolean didSucceed = false;

        try{
            ContentValues initialValues = new ContentValues();

            initialValues.put("contactname", c.getContactName());
            initialValues.put("streetaddress", c.getStreetAddress());
            initialValues.put("city", c.getCity());
            initialValues.put("state", c.getState());
            initialValues.put("zipcode", c.getZipcode());
            initialValues.put("phonenumber", c.getPhoneNumber());
            initialValues.put("cellnumber", c.getCellNumber());
            initialValues.put("email", c.getEMail());
            initialValues.put("birthday", String.valueOf(c.getBirthday().getTimeInMillis()));
            long rowChanged = database.insert("contact", null, initialValues);
            didSucceed =  rowChanged > 0;
            System.out.println("Success: "+ rowChanged);
        }catch(Exception e){
        }

        return didSucceed;
    }

    public boolean updateContact(Contact c){
        boolean didSucceed = false;

        try{
            long rowId = (long) c.getContactID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("contactname", c.getContactName());
            updateValues.put("streetaddress", c.getStreetAddress());
            updateValues.put("city", c.getCity());
            updateValues.put("state", c.getState());
            updateValues.put("zipcode", c.getZipcode());
            updateValues.put("phonenumber", c.getPhoneNumber());
            updateValues.put("cellnumber", c.getCellNumber());
            updateValues.put("email", c.getEMail());
            updateValues.put("birthday", String.valueOf(c.getBirthday().getTimeInMillis()));

            didSucceed = database.update("contact", updateValues, "_id="+rowId, null) > 0;
        }catch (Exception e){

        }

        return didSucceed;
    }

    public int getLastContactId(){
        int lastId;

        try{
            String query = "Select Max(_id) from contact";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();;
        }catch (Exception e){
            lastId = -1;
        }

        return lastId;
    }

    public ArrayList<String> getContactName(){
        ArrayList<String> contactName = new ArrayList<>();
        try{
            String query = "Select contactname from contact";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                contactName.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e){
            contactName = new ArrayList<>();
        }

        return contactName;
    }

}