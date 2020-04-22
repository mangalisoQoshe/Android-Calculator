package com.example.kilojoulecounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.nfc.Tag;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABSASE_NAME ="mylist.db";
    public static final String TABLE_NAME="mylist_data";
    public static final String COL1= "ID";
    public static final String COL2 ="DTE";
    public static final String COL3 = "FCT";
    public static final String COL4="ECT";
    public static final String COL5 ="NETT";
    private static final String Tag ="MainActivity";

    public DatabaseHelper(Context context){
        super(context,DATABSASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          String createTable = "CREATE TABLE "+TABLE_NAME+"("+ COL1+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                  COL2+ " TEXT ,"+
                  COL3+ " TEXT ,"+
                  COL4+ " TEXT ,"+
                  COL5+ " INT "+");";
          db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String date,String fct,String ect,String nett){
        int x = Integer.valueOf(nett).intValue();
        ContentValues values = new ContentValues();
        values.put(COL2,date);
        values.put(COL3,fct);
        values.put(COL4,ect);
        values.put(COL5,nett);
        SQLiteDatabase db =getWritableDatabase();
        long a= db.insert(TABLE_NAME,null,values);
        db.close();
        if(a==-1){return false;}//if data is inserted incorrectly it will return -1
        else{return true;}
    }

    public Cursor getListContents(){
        SQLiteDatabase db =getWritableDatabase();
        Cursor data =db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return data;
    }
   public void getRid()
    {
        SQLiteDatabase db =getWritableDatabase();

        db.execSQL("DELETE  FROM mylist_data;");
    }





}
