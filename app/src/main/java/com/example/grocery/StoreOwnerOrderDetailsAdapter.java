package com.example.grocery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class StoreOwnerOrderDetailsAdapter extends RecyclerView.Adapter<StoreOwnerOrderDetailsAdapter.ViewHolder> {
    ArrayList<Product> products;
    ArrayList<Integer> quantities;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderItemName, price, amount, orderItemTotal;

        public ViewHolder(View view) {
            super(view);
            orderItemName = view.findViewById(R.id.order_item_name);
            price = view.findViewById(R.id.order_item_price);
            amount = view.findViewById(R.id.order_item_amount);
            orderItemTotal = view.findViewById(R.id.order_item_total);
        }
    }

    public StoreOwnerOrderDetailsAdapter(Context context, ArrayList<Product> products, ArrayList<Integer> quantities) {
        this.context = context;
        this.products = products;
        this.quantities = quantities;
    }

    public void setData(ArrayList<Product> products, ArrayList<Integer> quantities) {
        this.products = products;
        this.quantities = quantities;
        this.notifyItemInserted(products.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.store_owner_order_detail_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.orderItemName.setText(products.get(position).getName());
        String price_unit = context.getString(R.string.price_per_unit,
                NumberFormat.getCurrencyInstance().format(products.get(position).getPrice()),
                products.get(position).getUnit());
        holder.price.setText(price_unit);
        holder.amount.setText(String.valueOf(this.quantities.get(position)));
        double total = products.get(position).getPrice() * this.quantities.get(position);
        holder.orderItemTotal.setText(NumberFormat.getCurrencyInstance().format(total));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.size();
    }
}

