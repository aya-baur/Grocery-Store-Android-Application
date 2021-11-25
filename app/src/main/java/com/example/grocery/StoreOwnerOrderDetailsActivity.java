package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StoreOwnerOrderDetailsActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static String[] s1, s2, s3, s4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_owner_order_details);

        recyclerView = findViewById(R.id.store_owner_order_details_recycler_view);

        s1 = getResources().getStringArray(R.array.sample_order_items);
        s2 = getResources().getStringArray(R.array.sample_order_prices);

        StoreOwnerOrderDetailsAdapter storeOwnerOrderDetailsAdapter = new StoreOwnerOrderDetailsAdapter(this, s1, s2, s3, s4);
        recyclerView.setAdapter(storeOwnerOrderDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}