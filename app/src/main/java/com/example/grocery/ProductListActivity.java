package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class ProductListActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static RecyclerView recyclerView;
    public static String[] names, prices, quantities, unit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);



        recyclerView = findViewById(R.id.product_list_recycler_view);

        names = getResources().getStringArray(R.array.sample_products_names);
        unit = getResources().getStringArray(R.array.sample_units);
        prices = getResources().getStringArray(R.array.sample_prices);
        quantities = getResources().getStringArray(R.array.sample_quantities);

        ProductsListAdapter Adapter = new ProductsListAdapter(this, names, prices, quantities, unit);
        recyclerView.setAdapter(Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}