package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class CustomerHomeActivity extends AppCompatActivity {
    public static ArrayList<String> storeNames;
    public static Customer customer;
    public static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home);

        recyclerView = findViewById(R.id.store_owner_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String store_id = getIntent().getStringExtra("ID");

        CustomerStoresAdapter customerStoresAdapter = new CustomerStoresAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(customerStoresAdapter);
        populateStoreList();


        Button myProducts = findViewById(R.id.my_products);
        myProducts.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, ProductListActivity.class);
            this.startActivity(intent);
        });
    }

    public void populateStoreList() {
        StoreOwnerOrdersAdapter storeOwnerOrdersAdapter = new StoreOwnerOrdersAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(storeOwnerOrdersAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Order> orderList = new ArrayList<>();
                Collections.sort(orderList);
                Log.i("orders change", dataSnapshot.toString());
                storeOwnerOrdersAdapter.setOrders(orderList);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled",
                        databaseError.toException());
            }
        };
        ref.addListenerForSingleValueEvent(listener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.store_owner_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                populateStoreList();
                return true;
            case R.id.menu_logout:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}