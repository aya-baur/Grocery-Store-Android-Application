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

import com.example.grocery.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class CustomerHomeActivity extends AppCompatActivity {
    public static ArrayList<StoreNameId> storeNameIds;
    public static RecyclerView recyclerView;
    public static String customerId;

    public static class StoreNameId implements Comparable<StoreNameId> {
        String name;
        String id;
        StoreNameId(String name, String id) {
            this.name = name;
            this.id = id;
        }
        @Override
        public int compareTo(StoreNameId o) {
            return name.compareTo(o.name);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home);

        recyclerView = findViewById(R.id.customer_home_store_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (getIntent().getStringExtra(MainActivity.CUSTOMER_ID) != null) {
            customerId = getIntent().getStringExtra(MainActivity.CUSTOMER_ID);
        }

        CustomerStoresAdapter customerStoresAdapter = new CustomerStoresAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(customerStoresAdapter);
        populateStoreList();


        Button myProducts = findViewById(R.id.customer_orders_button);
        myProducts.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, CustomerOrdersActivity.class);
            intent.putExtra(MainActivity.CUSTOMER_ID, customerId);
            this.startActivity(intent);
        });
    }

    public void populateStoreList() {
        CustomerStoresAdapter customerStoresAdapter = new CustomerStoresAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(customerStoresAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storeNameIds = new ArrayList<>();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String store_name = child.child("name").getValue(String.class);
                    String store_id = String.valueOf(child.child("id").getValue(Integer.class));
                    StoreNameId storeNameId = new StoreNameId(store_name, store_id);
                    storeNameIds.add(storeNameId);
                }
                Collections.sort(storeNameIds);
                Log.i("orders change", dataSnapshot.toString());
                customerStoresAdapter.setData(storeNameIds);

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