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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class CustomerOrdersActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static ArrayList<StoreOrder> storeOrders;
    public static final String ORDER_ID = "ORDER_ID";

    public static class StoreOrder implements Comparable<StoreOrder> {
        String name;
        String storeId;
        Order order;
        StoreOrder(String name, Order order, String storeId) {
            this.name = name;
            this.order = order;
            this.storeId = storeId;
        }
        @Override
        public int compareTo(StoreOrder o) {
            return name.compareTo(o.name);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_orders);

        recyclerView = findViewById(R.id.customer_orders_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String customer_id = getIntent().getStringExtra(CustomerHomeActivity.CUSTOMER_ID);

        if (storeOrders == null) {
            populateOrdersDataFromId(customer_id);
        } else {
            CustomerOrdersAdapter customerOrdersAdapter = new CustomerOrdersAdapter(this, new ArrayList<>());
            Collections.sort(storeOrders);
            customerOrdersAdapter.setData(storeOrders);
            recyclerView.setAdapter(customerOrdersAdapter);
        }
    }

    public void populateOrdersDataFromId(String customerId) {
        CustomerOrdersAdapter customerOrdersAdapter = new CustomerOrdersAdapter(this, new ArrayList<>());
        storeOrders = new ArrayList<>();
        recyclerView.setAdapter(customerOrdersAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.child("customers").child(customerId).getValue(Customer.class);
                for (Map<String, Integer> store_order: customer.getOrders_data()) {
                    String store_id = String.valueOf(store_order.get("store_id"));
                    String order_id = String.valueOf(store_order.get("order_id"));
                    Store store = dataSnapshot.child("stores").child(store_id).getValue(Store.class);
                    StoreOrder storeOrder = new StoreOrder(store.getName(), store.getOrders().get(order_id), String.valueOf(store.getId()));
                    storeOrders.add(storeOrder);
                }
                Log.i("orders change", dataSnapshot.toString());
                Collections.sort(storeOrders);
                customerOrdersAdapter.setData(storeOrders);

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
                if (CustomerHomeActivity.customer != null) {
                    populateOrdersDataFromId(String.valueOf(CustomerHomeActivity.customer.getId()));
                } else {
                    populateOrdersDataFromId(getIntent().getStringExtra(CustomerHomeActivity.CUSTOMER_ID));
                }
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