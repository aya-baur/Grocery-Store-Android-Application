package com.example.grocery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class CustomerOrdersAdapter extends RecyclerView.Adapter<CustomerOrdersAdapter.ViewHolder>{
    ArrayList<CustomerOrdersActivity.StoreOrder> storeOrders;
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

    public CustomerOrdersAdapter(Context context, ArrayList<CustomerOrdersActivity.StoreOrder> storeOrders) {
        this.context = context;
        this.storeOrders = storeOrders;
    }

    public void setData(ArrayList<CustomerOrdersActivity.StoreOrder> storeOrders) {
        this.storeOrders = storeOrders;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomerOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.store_owner_order_row, parent, false);
        return new CustomerOrdersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrdersAdapter.ViewHolder holder, int position) {
        Order order = storeOrders.get(position).order;
        holder.orderName.setText(storeOrders.get(position).name);
        holder.orderTime.setText(order.getTimeFormatted());
        holder.orderStatus.setText(context.getResources().getStringArray(R.array.statuses)[order.getStatus()]);
        holder.container.setOnClickListener((View view) -> {
            Intent intent = new Intent(context, CustomerOrderDetailsActivity.class);
            intent.putExtra(CustomerOrdersActivity.ORDER_ID, order.getId());
            intent.putExtra(CustomerHomeActivity.STORE_ID, storeOrders.get(position).storeId);
            context.startActivity(intent);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return storeOrders.size();
    }
}
