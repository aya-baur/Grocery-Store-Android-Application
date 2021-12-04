package com.example.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static final String STORE_ID = "STORE_ID";
    private int customerId;
    private String storeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        //Customer might want to go back to add a new product to cart
        Intent intent = getIntent();
        if(intent.getStringExtra(CartActivity.STORE_ID) != null){
            storeId = intent.getStringExtra(CartActivity.STORE_ID);
        }

        recyclerView = findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        storeId = getIntent().getStringExtra("STORE_ID");

        Cart cart = CustomerProductListActivity.cart;
        customerId = cart.getCustomerID();

        CartAdapter adapter = new CartAdapter(this, new HashMap<>());
        adapter.setProductsToCartItem(cart.products);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        Button placeOrder = findViewById(R.id.button_place_order);
        placeOrder.setOnClickListener((View view) -> {
            //prepare an ArrayList<Map<productId:ID, Quantity:1>> to create an Order object
            ArrayList<Map<String, Integer>> productsData = new ArrayList<>();
            for(Map.Entry<Integer, Cart.CartItem> entry : cart.products.entrySet()){
                Map<String, Integer> productsMap = new HashMap<>();
                productsMap.put("product_id", entry.getKey());
                productsMap.put("quantity", entry.getValue().quantity);
                productsData.add(productsMap);
            }

            //create an Order Object
            Order order = new Order(cart.customerName, customerId, productsData);

            //write into the store's database
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores").child(storeId);
            ref.child("orders").child(Integer.toString(order.getId())).setValue(order);
            finish();
        });
    }
    //Don't forget to restructure the Map<productID : CartItem> to the ArrayList<Map<productID : 348034, quantity : 2>>
}
