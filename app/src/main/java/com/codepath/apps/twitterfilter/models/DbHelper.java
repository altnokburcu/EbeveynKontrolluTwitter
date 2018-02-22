package com.codepath.apps.twitterfilter.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "filterDB";
    // Contacts table name
    private static final String TABLE_FILTERS = "filters";
    private static final String TABLE_USERS = "users";
    public String[] db_users;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_filters = "CREATE TABLE " + TABLE_FILTERS + "(id INTEGER PRIMARY KEY,filter_name TEXT  )";
        String table_users = "CREATE TABLE " + TABLE_USERS + "(id INTEGER PRIMARY KEY, user_name TEXT )";
        Log.d("DBHelper", "SQL : " + table_filters);
        db.execSQL(table_filters);
        db.execSQL(table_users);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean filtreEkle(String filtre){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put("filter_name", filtre);
            db.insert(TABLE_FILTERS, null, content);
            Log.d("burcu",filtre);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean kullaniciEkle(String user){
        try {
            SQLiteDatabase db1 = getReadableDatabase();
            if((int) DatabaseUtils.queryNumEntries(db1,TABLE_USERS) < 1){
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues content = new ContentValues();
                content.put("user_name", user);
                db.insert(TABLE_USERS, null, content);
                Log.d("burcu", user);
                return true;

            }
            else {

                return false;
            }
        }
        catch(Exception e){
            return false;
        }
    }
   /* public String kullaniciCek(){

    }*/


    public String[] getVt(){

        SQLiteDatabase db = getReadableDatabase();
        String[] vt_filtreler = new String[(int) DatabaseUtils.queryNumEntries(db,"filters")];
        Integer i=0;
        Cursor cursor = db.rawQuery("select filter_name from filters",null);
        cursor.moveToFirst();
        if(cursor != null && cursor.getCount() != 0) {
            do {
                vt_filtreler[i] = cursor.getString(cursor.getColumnIndex("filter_name"));
                i++;
            }
            while (cursor.moveToNext());
            return vt_filtreler;
        }
        return null;
    }


    public String kullaniciCek(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select user_name from "+TABLE_USERS,null);
        cursor.moveToFirst();
        String kullanici = cursor.getString(cursor.getColumnIndex("user_name"));
        if(kullanici==""){
            return null;
        }
        return kullanici;
    }
}
