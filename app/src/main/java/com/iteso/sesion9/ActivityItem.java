package com.iteso.sesion9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ActivityItem extends AppCompatActivity {
    protected EditText title;
    protected Button save;
    protected Spinner images;
    protected Spinner category;
    protected Spinner stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        images = findViewById(R.id.activity_image_spinner);
        title = findViewById(R.id.activity_item_title);
        category = findViewById(R.id.activity_item_category);
        stores = findViewById(R.id.activity_item_store);
        save = findViewById(R.id.activity_item_save);
    }
}
