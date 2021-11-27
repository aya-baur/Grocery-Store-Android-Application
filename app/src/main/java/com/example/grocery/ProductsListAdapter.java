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

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {
    String[] names_arr, price_arr,quantity_arr,  units_arr;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView testView1, testView2, testView3, testView4;
        EditText editText;
        Button button;

        public ViewHolder(View view) {
            super(view);
            testView1 = view.findViewById(R.id.textViewName);
            testView2 = view.findViewById(R.id.textViewPrice);
            testView3 = view.findViewById(R.id.textViewQuantity);
            testView4 = view.findViewById(R.id.textViewUnit);
            editText = view.findViewById(R.id.editTextNumberSigned);
            button = view.findViewById(R.id.button_update);
        }
    }

    public ProductsListAdapter(Context context, String[] s1, String[] s2, String[] s3, String[] s4) {
        this.context = context;
        names_arr = s1;
        price_arr = s2;
        quantity_arr = s3;
        units_arr = s4;


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
        holder.testView1.setText(names_arr[position]);
        holder.testView2.setText(price_arr[position]);
        holder.testView3.setText(quantity_arr[position]);
        holder.testView4.setText(units_arr[position]);
        holder.button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
            if (quantity_arr[position] != null && !holder.editText.getText().toString().equals("")){
                int newQuantity= Integer.valueOf(quantity_arr[position]) + Integer.valueOf(holder.editText.getText().toString());
                if(newQuantity >= 0){
                    quantity_arr[position] = String.valueOf(newQuantity);
                    holder.testView3.setText(quantity_arr[position]);
                    holder.editText.getText().clear();}

                else{Toast toast = Toast.makeText(view.getContext(), R.string.app_name, Toast.LENGTH_SHORT);
                    toast.show();}





                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return names_arr.length;
    }
}
