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

import com.example.grocery.View.MainActivity;

import java.util.ArrayList;

public class CustomerStoresAdapter extends RecyclerView.Adapter<CustomerStoresAdapter.ViewHolder>{
    public static ArrayList<CustomerHomeActivity.StoreNameId> storeNameIds;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;
        CardView container;

        public ViewHolder(View view) {
            super(view);
            storeName = view.findViewById(R.id.store_name);
            container = view.findViewById(R.id.customer_store_button);
        }
    }

    public CustomerStoresAdapter(Context context, ArrayList<CustomerHomeActivity.StoreNameId> storeNameIds) {
        this.context = context;
        this.storeNameIds = storeNameIds;
    }

    public void setData(ArrayList<CustomerHomeActivity.StoreNameId> storeNameIds) {
        this.storeNameIds = storeNameIds;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.customer_stores_row, parent, false);
        return new CustomerStoresAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.storeName.setText(storeNameIds.get(position).name);
        holder.container.setOnClickListener((View view) -> {
            Intent intent = new Intent(context, CustomerProductListActivity.class);
            intent.putExtra(MainActivity.STORE_ID, storeNameIds.get(position).id);
            intent.putExtra(MainActivity.CUSTOMER_ID, CustomerHomeActivity.customerId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return storeNameIds.size();
    }
}
