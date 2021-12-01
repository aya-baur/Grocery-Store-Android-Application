package com.example.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        //Getting Product Quantity
        EditText editQuantity = (EditText) findViewById(R.id.editTextNumberDecimal);
        int productQuantity = Integer.parseInt(editQuantity.getText().toString());
        //Getting Product Unit
        EditText editUnit = (EditText) findViewById(R.id.editUnit);
        String productUnit = editUnit.getText().toString();

        //creating a Product Object -> Don't need to do this, i think
        Product newProduct = new Product(productName, productQuantity, productPrice, productUnit);

        //Getting intent and store ID
        Intent intent = getIntent();
        //Store ID to use when writing to database
        //String storeId = intent.getStringExtra(ProductListActivity.STORE_ID);

        // Writing to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
    }

}
