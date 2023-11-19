package com.example.foodies.tabs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;

import java.util.ArrayList;

public class RecycleCartAdapter extends RecyclerView.Adapter<RecycleCartAdapter.ViewHolder> {

    private ArrayList<CartModel> cartModels;
    Context context;

    public RecycleCartAdapter(ArrayList<CartModel> cartModels, Context context) {
        this.cartModels = cartModels != null ? cartModels : new ArrayList<>();
        this.context = context;
    }

    public RecycleCartAdapter() {

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_food_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("Adapter", "onBindViewHolder called at position: " + position);;

            holder.itemImageView.setImageResource(cartModels.get(position).img);
            holder.itemPriceTextView.setText(cartModels.get(position).f_prize);
            holder.itemNameTextView.setText(cartModels.get(position).f_name);

        Log.d("RecycleCartAdapter", "Item at position " + position + ": " +
                "Name: " + cartModels.get(position).f_name +
                ", Price: " + cartModels.get(position).f_prize +
                ", Image: " + cartModels.get(position).img);
    }

    @Override
    public int getItemCount() {
        return (cartModels != null) ? cartModels.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImageView;
        TextView itemNameTextView, itemPriceTextView,quantity;
        Button remove,pulse,minus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImageView = itemView.findViewById(R.id.cimg);
            itemNameTextView = itemView.findViewById(R.id.cfood_name);
            itemPriceTextView = itemView.findViewById(R.id.cfood_prize);
            remove = itemView.findViewById(R.id.remove);
            pulse = itemView.findViewById(R.id.pulse);
            minus = itemView.findViewById(R.id.minus);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}

