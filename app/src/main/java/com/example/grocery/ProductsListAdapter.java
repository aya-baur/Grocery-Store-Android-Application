package com.example.grocery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {
    ArrayList<Product> Products;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView testView1, testView2, testView4;
        Button button;

        public ViewHolder(View view) {
            super(view);
            testView1 = view.findViewById(R.id.textViewName);
            testView2 = view.findViewById(R.id.textViewPrice);
            testView4 = view.findViewById(R.id.textViewUnit);
        }
    }

    public ProductsListAdapter(Context context, ArrayList<Product> Products) {
        this.context = context;
        this.Products = Products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.Products = products;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.testView1.setText(Products.get(position).getName());
        holder.testView2.setText(NumberFormat.getCurrencyInstance().format(Products.get(position).getPrice()));
        holder.testView4.setText(Products.get(position).getUnit());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return Products.size();
    }
}
