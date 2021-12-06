package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.grocery.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class CustomerOrderDetailsActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static Order order;
    public static ArrayList<Product> products;
    public static ArrayList<Integer> quantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_owner_order_details);

        recyclerView = findViewById(R.id.store_owner_order_details_recycler_view);

        StoreOwnerOrderDetailsAdapter storeOwnerOrderDetailsAdapter = new StoreOwnerOrderDetailsAdapter(this, new ArrayList<>(), new ArrayList<>());
        recyclerView.setAdapter(storeOwnerOrderDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String orderId = String.valueOf(getIntent().getIntExtra(CustomerOrdersActivity.ORDER_ID, 0));
        populateViewDataFromId(orderId, storeOwnerOrderDetailsAdapter);

        Button markReady = findViewById(R.id.button_ready_pickup);

        markReady.setVisibility(View.INVISIBLE);
    }
    public void populateViewDataFromId(String orderId, StoreOwnerOrderDetailsAdapter storeOwnerOrderDetailsAdapter) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores")
                .child(String.valueOf(getIntent().getStringExtra(MainActivity.STORE_ID)));
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Store store = dataSnapshot.getValue(Store.class);
                order = store.getOrders().get(orderId);
                ArrayList<Map<String, Integer>> products_data = order.getProducts_data();
                products = new ArrayList<>();
                quantities = new ArrayList<>();
                double subTotal = 0;

                for (Map<String, Integer> product_data: products_data) {
                    Product product = store.getProducts().get(String.valueOf(product_data.get("product_id")));
                    products.add(product);
                    quantities.add(product_data.get("quantity"));
                    subTotal += product.getPrice()*product_data.get("quantity");
                }
                Log.i("orders change", dataSnapshot.toString());
                storeOwnerOrderDetailsAdapter.setData(products, quantities);

                TextView nameView = findViewById(R.id.order_details_name);
                TextView subtotalView = findViewById(R.id.order_sub_total);
                TextView taxView = findViewById(R.id.order_tax);
                TextView totalView = findViewById(R.id.order_total);
                TextView statusView = findViewById(R.id.order_details_status);

                statusView.setText(getResources().getStringArray(R.array.customer_statuses)[order.getStatus()]);
                nameView.setText(store.getName());
                subtotalView.setText(NumberFormat.getCurrencyInstance().format(subTotal));
                taxView.setText(getResources().getString(R.string.tax_rate));
                totalView.setText(NumberFormat.getCurrencyInstance().format(subTotal * 1.13));
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
    public void onBackPressed()
    {
        NavUtils.navigateUpFromSameTask(this);
        super.onBackPressed();
    }
}