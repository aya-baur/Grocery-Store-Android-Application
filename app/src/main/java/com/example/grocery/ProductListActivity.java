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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ProductListActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String STORE_ID = "STORE_ID";
    public static RecyclerView recyclerView;
    public static FloatingActionButton add_new;
    public static String store_id;
    public static ProductsListAdapter Adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);



        recyclerView = findViewById(R.id.product_list_recycler_view);
        add_new = findViewById(R.id.floatingActionButton);



        Adapter = new ProductsListAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent1 = getIntent();
        store_id = intent1.getStringExtra("STORE_ID");


        populateProductDataFromStoreId(store_id, Adapter);


        add_new.setOnClickListener((View view) -> {

            Intent intent = new Intent(this, NewProductActivity.class);
            intent.putExtra("STORE_ID", store_id);
            this.startActivity(intent);
        });
    }

    public void populateProductDataFromStoreId(String storeId, ProductsListAdapter adapter) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores").child(storeId);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Store store = dataSnapshot.getValue(Store.class);
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
        ref.addListenerForSingleValueEvent(listener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                    populateProductDataFromStoreId(store_id, Adapter);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}