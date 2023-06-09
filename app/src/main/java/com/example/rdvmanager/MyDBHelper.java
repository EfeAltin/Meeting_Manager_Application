package com.example.rdvmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "RDVM.db";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_NAME ="my_database";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_TITLE="rdv_title";
    private static final String COLUMN_DATE="rdv_date";
    private static final String COLUMN_TIME="rdv_time";
    private static final String COLUMN_CONTACT="rdv_contact";
    private static final String COLUMN_PHONE_NUMBER="rdv_phone_number";
    private static final String COLUMN_ADDRESS="rdv_address";


    MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE        + " TEXT," +
                COLUMN_DATE         + " TEXT," +
                COLUMN_TIME         + " TEXT," +
                COLUMN_CONTACT + " TEXT);";
db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
onCreate(db);

    }

    void addRDV(String title,String date, String time, String contact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_CONTACT, contact);

        long result= db.insert(TABLE_NAME, null, cv);

        if(result==-1){
            Toast.makeText(context, "Failed",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added Successfully",Toast.LENGTH_SHORT).show();
        }


    }

    Cursor readAllData(){

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor= null;

        if (db!=null){
           cursor = db.rawQuery(query,null);
        }
return cursor;

    }

    void updateData(String row_id,String title,String date, String time,String contact){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_CONTACT,contact);


       long result= db.update(TABLE_NAME,cv,"_id=?", new String[]{row_id});

       if (result==-1){
           Toast.makeText(context,"Failed to Update",Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(context,"Successfully Updated!",Toast.LENGTH_SHORT).show();
       }

    }

    void deleteOneRow(String row_id){

        SQLiteDatabase db = this.getWritableDatabase();
       long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

       if (result==-1){
           Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(context,"Successfully Deleted!",Toast.LENGTH_SHORT).show();
       }

    }


}
