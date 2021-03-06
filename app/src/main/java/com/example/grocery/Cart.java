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
import java.util.HashMap;

public class Cart {
    //Key is a product ID, Value is the quantity ordered
    HashMap<Integer,CartItem> products;
//    ArrayList<Product> orderedProducts;
    private int customerID;
    public String customerName;


    public static class CartItem{
        int quantity;
        double price;
        String unit;
        String productName;
        int productId;
        public CartItem(double p, String u, String pN, int productId){
            quantity = 1;
            price = p;
            unit = u;
            productName = pN;
            this.productId = productId;
        }
    }
    //Constructor of Order class
    // public Order(String customer_name, int customer_id, ArrayList<Map<String, Integer>> products_data) {

    public Cart(){}
    public Cart(int ID, String customerName){
        products = new HashMap<>();
        this.customerID = ID;
        this.customerName = customerName;
    }

    //method to add to one product to cart
     public void addToCart(int productId, CartItem cart){
        if(products.containsKey(productId)){
            //if product is already in the cart, increase the quantity
            products.get(productId).quantity++;
        }else{
            products.put(productId, cart);
        }
     }

     //method to remove one product from Cart object
    public void removeFromCart(int productID){
        if(!products.containsKey(productID)){}
        else if(products.get(productID).quantity == 1){
            products.remove(productID);
        }
        else{
            products.get(productID).quantity--;
        }
    }

    //NOT BEEN USED
//     //method to add more than one product to cart
//     public void addToCart(int productId, int quantity){
//         if(products.containsKey(productId)){
//             //if product is already in the cart, increase the quantity
//             int count = products.get(productId); //returns the value attached to key
//             count += quantity;
//             products.put(productId, count);
//         }else{
//             products.put(productId, quantity);
//         }
//     }

     public int getCustomerID(){
        return customerID;
    }
    //Don't know if we need this
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


}
