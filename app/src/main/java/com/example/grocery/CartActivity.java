package com.example.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static final String STORE_ID = "STORE_ID";
    private int customerId;
    private String storeId;
    private double subtotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        recyclerView = findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        storeId = getIntent().getStringExtra("STORE_ID");

        Cart cart = CustomerProductListActivity.cart;
        customerId = cart.getCustomerID();

        CartAdapter adapter = new CartAdapter(this, new HashMap<>());
        adapter.setProductsToCartItem(cart.products);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Set the rest of the data
        TextView cartSubtotal, cartTotal, cartTax, cartStoreName;

        cartSubtotal = findViewById(R.id.cart_sub_total);
        cartTotal = findViewById(R.id.cart_order_total);
        cartTax = findViewById(R.id.cart_tax_value);
        cartStoreName = findViewById(R.id.cart_store_name);

        cartStoreName.setText(CustomerProductListActivity.storeName);
        subtotal=0;
        for(Map.Entry<Integer, Cart.CartItem> entry : cart.products.entrySet()){
            //calculating subtotal
            subtotal += entry.getValue().quantity * entry.getValue().price;
        }
        cartSubtotal.setText(NumberFormat.getCurrencyInstance().format(subtotal));
        cartTax.setText("13%");
        double total = subtotal * 1.13;
        cartTotal.setText(NumberFormat.getCurrencyInstance().format(total));

        ////

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
            //write data to customers database too
            //Prepare HashMap<String, Integer>
            HashMap<String, Integer> customerOrder = new HashMap<>();
            customerOrder.put("order_id", order.getId());
            customerOrder.put("store_id", Integer.valueOf(storeId));

            GenericTypeIndicator<ArrayList<HashMap<String, Integer>>> list = new GenericTypeIndicator<ArrayList<HashMap<String, Integer>>>() {};
            DatabaseReference refC = FirebaseDatabase.getInstance().getReference("customers").child(Integer.toString(customerId)).child("orders_data");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<HashMap<String, Integer>> orders = new ArrayList<>();
                    orders = dataSnapshot.getValue(list);
                    if(orders == null){
                        orders = new ArrayList<>();
                    }
                    orders.add(customerOrder);
                    refC.setValue(orders);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("warning", "loadPost:onCancelled",
                            databaseError.toException());
                }
            };
            refC.addListenerForSingleValueEvent(listener);
            //empty the cart

            finish();
        });
    }
    //Don't forget to restructure the Map<productID : CartItem> to the ArrayList<Map<productID : 348034, quantity : 2>>
    public void addSubtotal(double amount){
        subtotal += amount;
        TextView cartSubtotal, cartTotal, cartTax;

        cartSubtotal = findViewById(R.id.cart_sub_total);
        cartTotal = findViewById(R.id.cart_order_total);
        cartTax = findViewById(R.id.cart_tax_value);

        cartSubtotal.setText(NumberFormat.getCurrencyInstance().format(subtotal));
        cartTax.setText("13%");
        double total = subtotal * 1.13;
        cartTotal.setText(NumberFormat.getCurrencyInstance().format(total));
    }

}
