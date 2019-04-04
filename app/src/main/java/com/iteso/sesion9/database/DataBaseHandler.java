package com.iteso.sesion9.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.iteso.sesion9.tools.Constant.*;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ItesoStore.db";
    private static final int DATABASE_VERSION = 1;
    static int STOREPRODUCT_ID = 1;
    public static int ITEMPRODUCT_ID = 1;
    private static DataBaseHandler dataBaseHandler;


    private DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseHandler getInstance(Context context){
        if(dataBaseHandler == null){
            dataBaseHandler = new DataBaseHandler(context);
        }
        return dataBaseHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CITY_TABLE = "CREATE TABLE " + TABLE_CITY + "("
                + KEY_CITY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CITY_NAME + " TEXT)";
        db.execSQL(CREATE_CITY_TABLE);

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CATEGORY_NAME + " TEXT)";
        db.execSQL(CREATE_CATEGORY_TABLE);

        String CREATE_STORE_TABLE = "CREATE TABLE " + TABLE_STORE + "("
                + KEY_STORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_STORE_NAME + " TEXT,"
                + KEY_STORE_PHONE + " TEXT,"
                + KEY_STORE_CITY + " INTEGER,"
                + KEY_STORE_THUMBNAIL + " INTEGER,"
                + KEY_STORE_LAT + " DOUBLE,"
                + KEY_STORE_LNG + " DOUBLE)";
        db.execSQL(CREATE_STORE_TABLE);

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PRODUCT_TITLE + " TEXT,"
                + KEY_PRODUCT_IMAGE + " INTEGER,"
                + KEY_PRODUCT_CATEGORY + " INTEGER)";
        db.execSQL(CREATE_PRODUCT_TABLE);

        String CREATE_STOREPRODUCT_TABLE = "CREATE TABLE " + TABLE_STOREPRODUCT + "("
                + KEY_STOREPRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_STOREPRODUCT_PRODUCT + " INTEGER,"
                + KEY_STOREPRODUCT_STORE + " INTEGER)";
        db.execSQL(CREATE_STOREPRODUCT_TABLE);

        addValues(db);

    }

    public void addValues(SQLiteDatabase db){
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" + KEY_CATEGORY_ID + "," + KEY_CATEGORY_NAME + ") VALUES (1, 'TECHNOLOGY')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" + KEY_CATEGORY_ID + "," + KEY_CATEGORY_NAME + ") VALUES (2, 'HOME')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" + KEY_CATEGORY_ID + "," + KEY_CATEGORY_NAME + ") VALUES (3, 'ELECTRONICS')");
        db.execSQL("INSERT INTO " + TABLE_CITY + " (" + KEY_CITY_ID + "," + KEY_CITY_NAME + ") VALUES (1, 'Guadalajara')");
        db.execSQL("INSERT INTO " + TABLE_CITY + " (" + KEY_CITY_ID + "," + KEY_CITY_NAME + ") VALUES (2, 'San Pedro Tlaquepaque')");
        db.execSQL("INSERT INTO " + TABLE_CITY + " (" + KEY_CITY_ID + "," + KEY_CITY_NAME + ") VALUES (3, 'Tlajomulco')");
        db.execSQL("INSERT INTO " + TABLE_CITY + " (" + KEY_CITY_ID + "," + KEY_CITY_NAME + ") VALUES (4, 'Tonalá')");
        db.execSQL("INSERT INTO " + TABLE_CITY + " (" + KEY_CITY_ID + "," + KEY_CITY_NAME + ") VALUES (5, 'Zapopan')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                upgradeVersion2(db);
            case 2:
                upgradeVersion3(db);
            case 3:
                upgradeVersion4(db);
                break;
        }
    }
    public void upgradeVersion2(SQLiteDatabase db) {

    }
    public void upgradeVersion3(SQLiteDatabase db) {

    }
    public void upgradeVersion4(SQLiteDatabase db) {

    }

}
