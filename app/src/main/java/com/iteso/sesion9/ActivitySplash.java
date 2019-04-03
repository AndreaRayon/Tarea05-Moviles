package com.iteso.sesion9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.iteso.sesion9.beans.Store;
import com.iteso.sesion9.beans.User;
import com.iteso.sesion9.database.DataBaseHandler;
import com.iteso.sesion9.database.StoreControl;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.iteso.sesion9.tools.Constant.*;

public class ActivitySplash extends AppCompatActivity {
    DataBaseHandler dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                User user = loadUser();
                Intent intent;
                if(user.isLogged()){
                    intent = new Intent(ActivitySplash.this, MainActivity.class);
                }else{
                    intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                }
                startActivity(intent);
                finish();
            }
        };
        dh = DataBaseHandler.getInstance(ActivitySplash.this);
        loadStores();
        Timer timer = new Timer();
        timer.schedule(task, 2000);

    }
    private void loadStores() {
        ArrayList<Store> stores = new StoreControl().getStores(dh);
        if (stores.size() == 0) addStores();
    }
    public void addStores(){
        SQLiteDatabase db = dh.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_STORE
                + " (" + KEY_STORE_NAME + "," + KEY_STORE_PHONE + ","
                + KEY_STORE_CITY + "," + KEY_STORE_THUMBNAIL + ","
                + KEY_STORE_LAT + "," + KEY_STORE_LNG
                + ") VALUES ('BESTBUY ANDARES', '33 1253 7349', 1, 0, 20.7122093, -103.4109143)");

        db.execSQL("INSERT INTO " + TABLE_STORE
                + " (" + KEY_STORE_NAME + "," + KEY_STORE_PHONE + ","
                + KEY_STORE_CITY + "," + KEY_STORE_THUMBNAIL + ","
                + KEY_STORE_LAT + "," + KEY_STORE_LNG
                + ") VALUES ('LIVERPOOL GALERIAS', '01 800 376 7683', 2, 1, 20.7123956, -103.3774786)");

        db.execSQL("INSERT INTO " + TABLE_STORE
                + " (" + KEY_STORE_NAME + "," + KEY_STORE_PHONE + ","
                + KEY_STORE_CITY + "," + KEY_STORE_THUMBNAIL + ","
                + KEY_STORE_LAT + "," + KEY_STORE_LNG
                + ") VALUES ('OFFICE DEPOT PALOMAR', '01 800 667 7319', 3, 2, 20.5900951, -103.4423109)");
    }

    public User loadUser(){
        SharedPreferences sharedPreferences =
                getSharedPreferences("com.iteso.USER_PREFERENCES",
                        MODE_PRIVATE);
        User user = new User();
        user.setName(sharedPreferences.getString("NAME", null));
        user.setPassword(sharedPreferences.getString("PWD", null));
        user.setLogged(sharedPreferences.getBoolean("LOGGED", false));
        sharedPreferences = null;
        return user;
    }
}
