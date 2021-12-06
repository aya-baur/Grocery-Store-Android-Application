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

import com.example.grocery.View.MainActivity;
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
    public static final String ORDER_ID = "ORDER_ID";
    public static String customer_id;

    public static class CustomerOrder implements Comparable<CustomerOrder> {
        String name;
        String storeId;
        Order order;
        CustomerOrder(String name, Order order, String storeId) {
            this.name = name;
            this.order = order;
            this.storeId = storeId;
        }
        @Override
        public int compareTo(CustomerOrder o) {
            return order.compareTo(o.order);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_orders);

        recyclerView = findViewById(R.id.customer_orders_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().getStringExtra(MainActivity.CUSTOMER_ID) != null) {
            customer_id = getIntent().getStringExtra(MainActivity.CUSTOMER_ID);
        }
        populateOrdersDataFromId(customer_id);
    }

    public void populateOrdersDataFromId(String customerId) {
        CustomerOrdersAdapter customerOrdersAdapter = new CustomerOrdersAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(customerOrdersAdapter);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<CustomerOrder> customerOrders = new ArrayList<>();
                Customer customer = dataSnapshot.child("customers").child(customerId).getValue(Customer.class);
                for (Map<String, Integer> store_order: customer.getOrders_data()) {
                    String store_id = String.valueOf(store_order.get("store_id"));
                    String order_id = String.valueOf(store_order.get("order_id"));
                    Store store = dataSnapshot.child("stores").child(store_id).getValue(Store.class);
                    CustomerOrder customerOrder = new CustomerOrder(store.getName(), store.getOrders().get(order_id), String.valueOf(store.getId()));
                    customerOrders.add(customerOrder);
                }
                Log.i("orders change", dataSnapshot.toString());
                Collections.sort(customerOrders);
                customerOrdersAdapter.setData(customerOrders);

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