package com.iteso.sesion9.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.sesion9.beans.Category;

import java.util.ArrayList;

import static com.iteso.sesion9.tools.Constant.KEY_CATEGORY_ID;
import static com.iteso.sesion9.tools.Constant.KEY_CATEGORY_NAME;
import static com.iteso.sesion9.tools.Constant.TABLE_CATEGORY;

public class ControlCategory {

    public ArrayList<Category> getCategories(DataBaseHandler dh){
        ArrayList<Category> categories = new ArrayList<>();
        String select = "SELECT " + KEY_CATEGORY_ID + ", " + KEY_CATEGORY_NAME + "FROM "
                + TABLE_CATEGORY;
        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        while (cursor.moveToNext()){
            Category category = new Category();
            category.setId(cursor.getInt(0));
            category.setName(cursor.getString(1));
            categories.add(category);
        }
        try{
            cursor.close();
            db.close();
        }catch (Exception e){}
        db = null;
        cursor = null;
        return categories;
    }
}
