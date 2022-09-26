package com.usman.qrcodereader.PkQr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBmanager extends SQLiteOpenHelper {

    private static final String dbname = "QR_APP_DB.db";
    public DBmanager(@Nullable Context context)
    {
        super( context, dbname, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry = "create table qrdata (sno INTEGER primary key AUTOINCREMENT, data text, isurl integer)";
        sqLiteDatabase.execSQL( qry );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String qry1 = "DROP TABLE IF EXISTS qrdata";
        sqLiteDatabase.execSQL( qry1 );
        onCreate( sqLiteDatabase );
    }

    public String add_data(String data, int isurl){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put( "data",data );
        cv.put( "isurl",isurl );
        float res = db.insert( "qrdata",null,cv );
        if (res == -1){
            return "failed";
        }
        else{
            return "success";
        }
    }
    public boolean first_data(){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put( "data","dummy" );
        cv.put( "isurl",1 );
        float res = db.insert( "qrdata",null,cv );
        if (res == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor readalldata(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry3 = "select * from qrdata";
        Cursor cursor = db.rawQuery( qry3,null );
        return cursor;
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry4 = "DELETE * FROM qrdata";
        db.delete( "qrdata",null,null );
//        db.execSQL( qry4 );
    }
}
