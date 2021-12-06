package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerProductListActivity extends AppCompatActivity {
    public static final String STORE_ID = "STORE_ID";
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static RecyclerView recyclerView;
    public static Button viewCart;
    public static String store_id;
    public static int customer_id;
    public static CustomerProductsListAdapter adapter;
    public static Cart cart;
    public static String storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_product_list);

        Intent intent1 = getIntent();
        if (intent1.getStringExtra(MainActivity.STORE_ID) != null) {
            store_id = intent1.getStringExtra(MainActivity.STORE_ID);
        }

        Intent intent2 = getIntent();
        if (intent2.getStringExtra(MainActivity.CUSTOMER_ID) != null) {
            customer_id = Integer.parseInt(intent2.getStringExtra(MainActivity.CUSTOMER_ID));
            CustomerProductListActivity.cart = new Cart(customer_id, "");
            CustomerProductListActivity.cart.getCustomerName(customer_id);
        }

        recyclerView = findViewById(R.id.customer_product_list_recycler_view);
        viewCart = findViewById(R.id.view_cart);

        adapter = new CustomerProductsListAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        populateProductDataFromStoreId(store_id, adapter);

        viewCart.setOnClickListener((View view) -> {
            if(cart.products.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, CartActivity.class);
                intent.putExtra(STORE_ID, store_id);
                this.startActivity(intent);
            }
        });
    }

    public void populateProductDataFromStoreId(String storeId, CustomerProductsListAdapter adapter) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores").child(storeId);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Store store = dataSnapshot.getValue(Store.class);
                storeName = store.getName();
                ArrayList<Product> productList = new ArrayList<>(store.getProducts().values());
                Log.i("orders change", dataSnapshot.toString());
                adapter.setProducts(productList);

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
    public void onBackPressed()
    {
        NavUtils.navigateUpFromSameTask(this);
        super.onBackPressed();
    }
}