package com.example.grocery;

import androidx.annotation.NonNull;
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
import android.widget.EditText;

import com.example.grocery.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StoreOwnerHomeActivity extends AppCompatActivity {
    public static final String ORDER_ID = "ORDER_ID";
    public static final String ORDER_STATUS = "ORDER_STATUS";
    public static final String STORE_ID = "STORE_ID";
    public static RecyclerView recyclerView;
    public static Store store;
    public static String store_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_owner_orders);

        recyclerView = findViewById(R.id.store_owner_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (getIntent().getStringExtra(MainActivity.STORE_ID) != null) {
            store_id = getIntent().getStringExtra(MainActivity.STORE_ID);

        }
        populateStoreDataFromId(store_id);

        Button myProducts = findViewById(R.id.my_products);
        myProducts.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra(StoreOwnerHomeActivity.STORE_ID, store_id);
            this.startActivity(intent);
        });
    }

    public void populateStoreDataFromId(String storeId) {
        StoreOwnerOrdersAdapter storeOwnerOrdersAdapter = new StoreOwnerOrdersAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(storeOwnerOrdersAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores").child(storeId);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                store = dataSnapshot.getValue(Store.class);
                ArrayList<Order> orderList = new ArrayList<>(store.getOrders().values());
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
        ref.addValueEventListener(listener);

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