package com.example.grocery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreOwnerOrdersAdapter extends RecyclerView.Adapter<StoreOwnerOrdersAdapter.ViewHolder> {
    ArrayList<Order> orders;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderName, orderTime, orderStatus;
        CardView container;

        public ViewHolder(View view) {
            super(view);
            orderName = view.findViewById(R.id.order_name);
            orderTime = view.findViewById(R.id.order_time);
            orderStatus = view.findViewById(R.id.order_status);
            container = view.findViewById(R.id.order_button);
        }
    }

    public StoreOwnerOrdersAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
        this.notifyItemInserted(orders.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.store_owner_order_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.orderName.setText(orders.get(position).getCustomer_name());
        holder.orderTime.setText(orders.get(position).getTimeFormatted());
        holder.orderStatus.setText(context.getResources().getStringArray(R.array.statuses)[orders.get(position).getStatus()]);
        holder.container.setOnClickListener((View view) -> {
            Intent intent = new Intent(context, StoreOwnerOrderDetailsActivity.class);
            intent.putExtra(StoreOwnerHomeActivity.ORDER_ID, orders.get(position).getId());
            intent.putExtra(StoreOwnerHomeActivity.ORDER_STATUS, orders.get(position).getStatus());
            context.startActivity(intent);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orders.size();
    }
}

