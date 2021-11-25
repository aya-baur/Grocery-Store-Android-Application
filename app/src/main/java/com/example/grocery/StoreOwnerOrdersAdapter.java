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

public class StoreOwnerOrdersAdapter extends RecyclerView.Adapter<StoreOwnerOrdersAdapter.ViewHolder> {
    String[] data1, data2;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderName, orderTime, isComplete;
        CardView container;

        public ViewHolder(View view) {
            super(view);
            orderName = view.findViewById(R.id.order_name);
            orderTime = view.findViewById(R.id.order_time);
            isComplete = view.findViewById(R.id.is_complete);
            container = view.findViewById(R.id.order_button);
        }
    }

    public StoreOwnerOrdersAdapter(Context context, String[] s1, String[] s2) {
        this.context = context;
        this.data1 = s1;
        this.data2 = s2;
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
        holder.orderName.setText(data1[position]);
        holder.orderTime.setText(data2[position]);
        holder.container.setOnClickListener((View view) -> {
            Intent intent = new Intent(context, StoreOwnerOrderDetailsActivity.class);
            intent.putExtra(StoreOwnerHomeActivity.EXTRA_MESSAGE, "test");
            context.startActivity(intent);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data1.length;
    }
}

