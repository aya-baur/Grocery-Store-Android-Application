package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StoreOwnerHomeActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static RecyclerView recyclerView;
    public static String[] s1, s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_owner_orders);

        recyclerView = findViewById(R.id.store_owner_recycler_view);

        s1 = getResources().getStringArray(R.array.sample_customer_names);
        s2 = getResources().getStringArray(R.array.sample_order_times);

        StoreOwnerOrdersAdapter storeOwnerOrdersAdapter = new StoreOwnerOrdersAdapter(this, s1, s2);
        recyclerView.setAdapter(storeOwnerOrdersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}