package com.strawberry.delivery.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
    private static final String DB_NAME="rest.db";
    private static final int DB_VERSION=1;

    public DBhelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS RestFood ("+
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "foodName TEXT NOT NULL,"+
                "foodPrice INTEGER NOT NULL,"+
                "foodUrl TEXT NOT NULL,"+
                "restaurant INTEGER NOT NULL);"
                ;

        db.execSQL(sql);

        sql="CREATE TABLE IF NOT EXISTS restaurant("+
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "restName TEXT NOT NULL,"+
                "restAddress TEXT NOT NULL,"+
                "restPhone TEXT NOT NULL,"+
                "percent FLOAT NOT NULL," +
                "restUrl TEXT NOT NULL);";
        db.execSQL(sql);

        sql="CREATE TABLE IF NOT EXISTS orderForm("+
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "foodName TEXT NOT NULL,"+
                "foodCount TEXT NOT NULL,"+
                "total INTEGER NOT NULL,"+
                "restName TEXT NOT NULL,"+
                "deliveryAddress TEXT NOT NULL);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS RestFood";
        db.execSQL(sql);
        sql="DROP TABLE IF EXISTS restaurant";
        db.execSQL(sql);
        sql="DROP TABLE IF EXISTS orderForm";
        db.execSQL(sql);
        onCreate(db);
    }
}
