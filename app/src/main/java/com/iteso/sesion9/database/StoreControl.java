package com.iteso.sesion9.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.sesion9.beans.City;
import com.iteso.sesion9.beans.Store;

import java.util.ArrayList;

import static com.iteso.sesion9.tools.Constant.KEY_CITY_ID;
import static com.iteso.sesion9.tools.Constant.KEY_CITY_NAME;
import static com.iteso.sesion9.tools.Constant.KEY_STORE_CITY;
import static com.iteso.sesion9.tools.Constant.KEY_STORE_ID;
import static com.iteso.sesion9.tools.Constant.KEY_STORE_LAT;
import static com.iteso.sesion9.tools.Constant.KEY_STORE_LNG;
import static com.iteso.sesion9.tools.Constant.KEY_STORE_NAME;
import static com.iteso.sesion9.tools.Constant.KEY_STORE_PHONE;
import static com.iteso.sesion9.tools.Constant.KEY_STORE_THUMBNAIL;
import static com.iteso.sesion9.tools.Constant.TABLE_CITY;
import static com.iteso.sesion9.tools.Constant.TABLE_STORE;

public class StoreControl {
    public void addStore(Store store, DataBaseHandler dh){
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STORE_ID, store.getId());
        values.put(KEY_STORE_NAME, store.getName());
        values.put(KEY_STORE_PHONE, store.getPhone());
        values.put(KEY_STORE_THUMBNAIL, store.getThumbnail());
        values.put(KEY_STORE_LAT, store.getLatitude());
        values.put(KEY_STORE_LNG, store.getLongitude());
        values.put(KEY_STORE_CITY, store.getCity().getId());
        db.insert(TABLE_STORE, null, values);
        try{
            db.close();
        }catch(Exception e){

        }
        db = null;
        values = null;

    }

    public ArrayList<Store> getStores(DataBaseHandler dh){

        ArrayList<Store> stores = new ArrayList<>();
        String selectQuery = "SELECT " + TABLE_STORE + "." + KEY_STORE_ID + ", " +
                                        TABLE_STORE + "." + KEY_STORE_NAME + ", " +
                                        TABLE_STORE + "." + KEY_STORE_PHONE + ", " +
                                        TABLE_STORE + "." + KEY_STORE_THUMBNAIL + ", " +
                                        TABLE_STORE + "." + KEY_STORE_LAT + ", " +
                                        TABLE_STORE + "." + KEY_STORE_LNG + ", " +
                                        TABLE_STORE + "." + KEY_STORE_CITY + ", " +
                                        TABLE_STORE + "." + KEY_CITY_NAME +
                                " FROM " + TABLE_STORE + ", " + TABLE_CITY +
                                " WHERE " + TABLE_STORE + "." + KEY_STORE_CITY + "=" + TABLE_CITY + "."+ KEY_CITY_ID;


        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()){
            Store store = new Store();
            store.setId(cursor.getInt(0));
            store.setName(cursor.getString(1));
            store.setPhone(cursor.getString(2));
            store.setThumbnail(cursor.getInt(3));
            store.setLatitude(cursor.getDouble(4));
            store.setLongitude(cursor.getDouble(5));
            store.setCity(new City(cursor.getInt(6), cursor.getString(7)));
            stores.add(store);
        }
        try {
            cursor.close();
            db.close();

        } catch (Exception e) {}
        db = null;
        cursor = null;

        return stores;
    }
    public static Store getStoreById(int idStore, DataBaseHandler dh){
        Store store = new Store();
        SQLiteDatabase db = dh.getWritableDatabase();
        String selectQuery = "SELECT " + TABLE_STORE + "." + KEY_STORE_ID + ", " +
                TABLE_STORE + "." + KEY_STORE_NAME + ", " +
                TABLE_STORE + "." + KEY_STORE_PHONE + ", " +
                TABLE_STORE + "." + KEY_STORE_THUMBNAIL + ", " +
                TABLE_STORE + "." + KEY_STORE_LAT + ", " +
                TABLE_STORE + "." + KEY_STORE_LNG + ", " +
                TABLE_STORE + "." + KEY_STORE_CITY + ", " +
                TABLE_STORE + "." + KEY_CITY_NAME +
                " FROM " + TABLE_STORE + ", " + TABLE_CITY +
                " WHERE " + TABLE_STORE + "." + KEY_STORE_ID + "=" + idStore +
                " AND " + TABLE_CITY + "." + KEY_CITY_ID + " = " + TABLE_STORE + "." + KEY_STORE_CITY;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToNext()) {
            store.setId(cursor.getInt(0));
            store.setName(cursor.getString(1));
            store.setPhone(cursor.getString(2));
            store.setThumbnail(cursor.getInt(3));
            store.setLatitude(cursor.getDouble(4));
            store.setLongitude(cursor.getDouble(5));

            City city = new City();
            city.setId(cursor.getInt(6));
            city.setName(cursor.getString(7));
            store.setCity(city);
        }
        try {
            cursor.close();
            db.close();
        } catch (Exception e){

        }
        db = null;
        cursor = null;
        return store;
    }
}

