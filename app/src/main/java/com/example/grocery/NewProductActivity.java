package com.example.grocery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_product_page);
    }

    public void addNewProduct(View view){

        //create a Product Object
        //String name, int quantity, double price, String unit
        //Getting Product Name
        EditText editProductName = (EditText) findViewById(R.id.editNewProductName);
        String productName = editProductName.getText().toString();
        //Getting Product Price
        EditText editPrice = (EditText) findViewById(R.id.editPriceDecimal);
        Double productPrice = Double.parseDouble(editPrice.getText().toString());
        //Getting Product Unit
        EditText editUnit = (EditText) findViewById(R.id.editUnit);
        String productUnit = editUnit.getText().toString();

        //creating a Product Object
        Product newProduct = new Product(productName, productPrice, productUnit);
        int productId = newProduct.getId();

        //Getting intent and store ID
        Intent intent = getIntent();
        //Store ID to use when writing to database

        String storeId = intent.getStringExtra(ProductListActivity.STORE_ID);

        // Writing to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("stores/" + storeId);

        //Checking that the id is unique
        //Reading from database
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Store store = dataSnapshot.getValue(Store.class);
                if(store.getProducts().containsKey(Integer.toString(productId))){
                   //Throw an Exception to the user
                    Context context = getApplicationContext();
                    CharSequence text = "Error: Product with such name already exists!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, text, duration).show();
                }else{
                    //Writing to the database
                    //If we got to this line, we are sure that product does not exist in the database -> should be like that
                    ref.child("products").child(Integer.toString(productId)).setValue(newProduct);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled",
                        databaseError.toException());
            }
        };
        ref.addListenerForSingleValueEvent(listener);
        //Go back to Nigel's page
        finish();
    }

}
