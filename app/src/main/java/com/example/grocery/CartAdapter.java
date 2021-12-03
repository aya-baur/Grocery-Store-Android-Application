package com.example.grocery;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    HashMap<Integer, Integer> productsToQuantities;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, price, quantity, unit;
        Button add, remove;

        public ViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.product_name);
            price = view.findViewById(R.id.cart_price);
            quantity = view.findViewById(R.id.cart_quantitiy);
            unit = view.findViewById(R.id.cart_unit);
            add = view.findViewById(R.id.cart_add_btn);
            remove = view.findViewById(R.id.cart_remove_btn);
        }
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
