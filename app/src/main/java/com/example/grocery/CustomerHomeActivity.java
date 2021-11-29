package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CustomerHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        String user_id = getIntent().getStringExtra("ID");
    }
}