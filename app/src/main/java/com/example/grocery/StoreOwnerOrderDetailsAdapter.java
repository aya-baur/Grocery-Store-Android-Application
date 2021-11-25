package com.example.grocery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreOwnerOrderDetailsAdapter extends RecyclerView.Adapter<StoreOwnerOrderDetailsAdapter.ViewHolder> {
    String[] data1, data2, data3, data4;
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

    public StoreOwnerOrderDetailsAdapter(Context context, String[] s1, String[] s2, String[] s3, String[] s4) {
        this.context = context;
        this.data1 = s1;
        this.data2 = s2;
        this.data3 = s3;
        this.data4 = s4;
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
        holder.orderItemName.setText(data1[position]);
        holder.price.setText(data2[position]);
        holder.amount.setText("x3");
        holder.orderItemTotal.setText("$30.00");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data1.length;
    }
}

