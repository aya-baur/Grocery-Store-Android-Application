package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_owner_orders);

        recyclerView = findViewById(R.id.store_owner_recycler_view);

        StoreOwnerOrdersAdapter storeOwnerOrdersAdapter = new StoreOwnerOrdersAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(storeOwnerOrdersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String store_id = getIntent().getStringExtra("ID");
        populateStoreDataFromId(store_id, storeOwnerOrdersAdapter);

        Button myProducts = findViewById(R.id.my_products);
        myProducts.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra(StoreOwnerHomeActivity.STORE_ID, store.getId());
            this.startActivity(intent);
        });
    }

    public void populateStoreDataFromId(String storeId, StoreOwnerOrdersAdapter storeOwnerOrdersAdapter) {
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
        ref.addListenerForSingleValueEvent(listener);

    }

}