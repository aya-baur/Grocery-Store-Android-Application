package com.example.grocery;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Cart {
    Map<String, Integer> products;
    private int customerID;
    private String customerName;

    //Constructor of Order class
    // public Order(String customer_name, int customer_id, ArrayList<Map<String, Integer>> products_data) {

    public Cart(){}
    public Cart(int ID){
        this.customerID = ID;
        getCustomerName(customerID);
    }

    public void getCustomerName(int customerID){
        //getting customer name form database
        //Reading from database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("customers/" + customerID);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                customerName = customer.getName();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled",
                        databaseError.toException());
            }
        };
        ref.addListenerForSingleValueEvent(listener);
    }

    //method to add to one product to cart
     public void addToCart(String productId){
        if(products.containsKey(productId)){
            //if product is already in the cart, increase the quantity
            int count = products.get(productId);
            count++;
            products.put(productId, count);
        }else{
            products.put(productId, 1);
        }
     }

     //method to add more than one product to cart
     public void addToCart(String productId, int quantity){
         if(products.containsKey(productId)){
             //if product is already in the cart, increase the quantity
             int count = products.get(productId);
             count += quantity;
             products.put(productId, count);
         }else{
             products.put(productId, quantity);
         }
     }
}
