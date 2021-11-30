package com.example.grocery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerStoresAdapter extends RecyclerView.Adapter<CustomerStoresAdapter.ViewHolder>{
    ArrayList<String> storeNames;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;

        public ViewHolder(View view) {
            super(view);
            storeName = view.findViewById(R.id.store_name);
        }
    }

    public CustomerStoresAdapter(Context context, ArrayList<String> storeNames) {
        this.context = context;
        this.storeNames = storeNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return storeNames.size();
    }
}
