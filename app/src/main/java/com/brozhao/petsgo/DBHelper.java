package com.brozhao.petsgo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db";
    private static final String TABLE_NAME_MY_PET = "myPet";
    private static final int VERSION = 1;

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_MY_PET + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "name CHAR,"
                + "foodCookie DOUBLE,"
                + "foodCan DOUBLE,"
                + "water DOUBLE,"
                + "memo CHAR"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    static void insertDB(Context context, String name, double foodCookie, double foodCan, double water, String memo) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("foodCookie", foodCookie);
        cv.put("foodCan", foodCan);
        cv.put("water",water);
        cv.put("memo", memo);

        db.insert("myPet", null, cv);
        db.close();
    }

    static Cursor getMyPetCursor(Context context){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("Select * from myPet", null);
    }
}

