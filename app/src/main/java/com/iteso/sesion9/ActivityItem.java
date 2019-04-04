package com.iteso.sesion9;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iteso.sesion9.beans.Category;
import com.iteso.sesion9.beans.ItemProduct;
import com.iteso.sesion9.beans.Store;
import com.iteso.sesion9.database.ControlCategory;
import com.iteso.sesion9.database.DataBaseHandler;
import com.iteso.sesion9.database.ItemProductControl;
import com.iteso.sesion9.database.StoreControl;

import java.util.ArrayList;

public class ActivityItem extends AppCompatActivity {
    protected EditText title;
    protected Button save;
    protected Spinner images;
    protected Spinner category;
    protected Spinner stores;

    protected ArrayAdapter<Store> storeArrayAdapter;
    protected ArrayAdapter<Category> categoryArrayAdapter;
    protected ArrayAdapter<String> imageArrayAdapter;

    protected Store selectedStore;
    protected Category selectedCategory;
    protected int selectedImage;

    protected DataBaseHandler dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        images = findViewById(R.id.activity_image_spinner);
        title = findViewById(R.id.activity_item_title);
        category = findViewById(R.id.activity_item_category);
        stores = findViewById(R.id.activity_item_store);
        save = findViewById(R.id.activity_item_save);

        selectedCategory = null;
        selectedImage = -1;
        selectedStore = null;

        dh = DataBaseHandler.getInstance(this);
        StoreControl storeControl = new StoreControl();
        ControlCategory controlCategory = new ControlCategory();

        ArrayList<Store> storeArrayList = storeControl.getStores(dh);
        ArrayList<Category> categoryArrayList = controlCategory.getCategories(dh);

        storeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, storeArrayList);
        stores.setAdapter(storeArrayAdapter);
        categoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryArrayList);
        category.setAdapter(categoryArrayAdapter);

        stores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStore = storeArrayAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoryArrayAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        images.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedImage = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notEmpty()){
                    ItemProduct itemProduct = new ItemProduct();
                    itemProduct.setCode(DataBaseHandler.ITEMPRODUCT_ID);
                    DataBaseHandler.ITEMPRODUCT_ID++;
                    itemProduct.setTitle(title.getText().toString().trim());
                    itemProduct.setStore(selectedStore);
                    itemProduct.setCategory(selectedCategory);
                    itemProduct.setImage(selectedImage);
                    ItemProductControl itemProductControl = new ItemProductControl();
                    itemProductControl.addItemProduct(itemProduct, dh);

                    Intent intent = new Intent();
                    intent.putExtra("ITEM", itemProduct);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    private boolean notEmpty(){
        if(title.getText().toString().isEmpty()){
            Toast.makeText(ActivityItem.this, "Por favor completa todos los campos", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
