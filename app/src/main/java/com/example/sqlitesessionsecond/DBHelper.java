package com.example.sqlitesessionsecond;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import java.io.ByteArrayOutputStream;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 4;

    // Table name
    public static final String TABLE_NAME = "my_table";
    public static final String SELECTED_TABLE_NAME = "selected";

    // Column names
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_RULE = "rule";

    private Context context;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_IMAGE + " BLOB, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DATE + " TEXT" +
                ")";
        String createSecondTableQuery = "CREATE TABLE " + SELECTED_TABLE_NAME + "(" +
                COLUMN_IMAGE + " BLOB, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_RULE + " TEXT" +
                ")";
        db.execSQL(createTableQuery);
        db.execSQL(createSecondTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SELECTED_TABLE_NAME);
        onCreate(db);
    }

    public void insertData(int drawableId, String title, String date) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        byte[] imageData = getByteArrayFromBitmap(bitmap);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IMAGE, imageData);
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_DATE, date);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public void insertPropertyData(int drawableId,String date,String price,String rule){
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        byte[] imageData = getByteArrayFromBitmap(bitmap);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IMAGE, imageData);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_PRICE, price);
        contentValues.put(COLUMN_RULE, rule);
        db.insert(SELECTED_TABLE_NAME, null, contentValues);
        db.close();
    }

    public void updatePropertyData(byte[] newImageBytes, String newDate, String newPrice, String newRule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IMAGE, newImageBytes);
        contentValues.put(COLUMN_PRICE, newPrice);
        contentValues.put(COLUMN_RULE, newRule);
        db.update(SELECTED_TABLE_NAME, contentValues, COLUMN_DATE + " = ?", new String[]{newDate});
        db.close();
    }


    public byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayInputStream);
        return byteArrayInputStream.toByteArray();

    }

}
