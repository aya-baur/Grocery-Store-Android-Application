package com.example.grocery;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.HashMap;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    HashMap<Integer, Cart.CartItem> productsToCartItem;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, price, quantity, unit;
        ImageButton add, remove, delete;

        public ViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.product_name);
            price = view.findViewById(R.id.cart_price);
            quantity = view.findViewById(R.id.cart_quantitiy);
            unit = view.findViewById(R.id.cart_unit);
            add = view.findViewById(R.id.cart_add_btn);
            remove = view.findViewById(R.id.cart_remove_btn);
            delete = view.findViewById(R.id.deleteButton);
        }
    }

    public CartAdapter(Context context, HashMap<Integer, Cart.CartItem> productsToCartItem){
        this.context = context;
        this.productsToCartItem = productsToCartItem;
    }

    public void setProductsToCartItem(HashMap<Integer, Cart.CartItem> products){
        this.productsToCartItem = products;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.productName.setText(productsToCartItem.get(position).productName);
        holder.price.setText(NumberFormat.getCurrencyInstance().format(productsToCartItem.get(position).price));
        holder.unit.setText(productsToCartItem.get(position).unit);
        holder.quantity.setText(productsToCartItem.get(position).quantity);
        //have to have two lines below to avoid an error :p
        Cart.CartItem cartItem = productsToCartItem.get(position);
        int productId = position;
        holder.remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //need decrease the number of items to be bought
                //if there is more than 1
                if(cartItem.quantity > 1){
                    cartItem.quantity--;
                    holder.quantity.setText(cartItem.quantity);
                }else{
                    productsToCartItem.remove(productId);
                    notifyItemRemoved(productId);
                    notifyItemRangeChanged(productId, productsToCartItem.size()); //productsToCartItem.size() - position (?)
                }
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItem.quantity++;
                holder.quantity.setText(cartItem.quantity);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productsToCartItem.remove(productId);
                notifyItemRemoved(productId);
                notifyItemRangeChanged(productId, productsToCartItem.size()); //productsToCartItem.size() - position (?)
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsToCartItem.size();
    }
}
