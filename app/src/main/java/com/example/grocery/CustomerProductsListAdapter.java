package com.example.grocery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CustomerProductsListAdapter extends RecyclerView.Adapter<CustomerProductsListAdapter.ViewHolder> {
    ArrayList<Product> Products;
    Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView testView1, testView2, testView4, quantity;
        Button addToCart, removeFromCart;

        public ViewHolder(View view) {
            super(view);
            testView1 = view.findViewById(R.id.textViewName2);
            testView2 = view.findViewById(R.id.textViewPrice2);
            testView4 = view.findViewById(R.id.textViewUnit2);
            quantity = view.findViewById(R.id.quantity_added);
            addToCart = view.findViewById(R.id.add_to_cart_by_one);
            removeFromCart = view.findViewById(R.id.remove_from_cart_by_one);


        }
    }

    public CustomerProductsListAdapter(Context context, ArrayList<Product> Products) {
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
        View view = inflater.inflate(R.layout.customer_product_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.testView1.setText(Products.get(position).getName());
        holder.testView2.setText(NumberFormat.getCurrencyInstance().format(Products.get(position).getPrice()));
        holder.testView4.setText(Products.get(position).getUnit());
        int id = Products.get(position).getId();
        holder.addToCart.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
            CustomerProductListActivity.cart.addToCart(id);
            holder.quantity.setText(String.valueOf(CustomerProductListActivity.cart.products.get(id)));



            }
        });

        holder.removeFromCart.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                CustomerProductListActivity.cart.removeFromCart(id);
                if (CustomerProductListActivity.cart.products.containsKey(id)) {
                holder.quantity.setText(String.valueOf(CustomerProductListActivity.cart.products.get(id)));
                }
                else {
                    holder.quantity.setText("");
                }
            }



            });
        }





    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return Products.size();
    }
}