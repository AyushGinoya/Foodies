package com.example.foodies.tabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.userDatabase.DBLogin;

import java.util.ArrayList;

public class RecycleCartAdapter extends RecyclerView.Adapter<RecycleCartAdapter.ViewHolder> {

    Context context;
    ArrayList<CartModel> cartModels;

    public RecycleCartAdapter(ArrayList<CartModel> cartModels) {
        this.cartModels= cartModels;
    }

    public RecycleCartAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_food_list, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Bitmap bitmap = BitmapFactory.decodeByteArray(cartModels.get(position).img, 0, cartModels.get(position).img.length);
        holder.itemImageView.setImageBitmap(bitmap);
        holder.itemPriceTextView.setText(cartModels.get(position).price);
        holder.itemNameTextView.setText(cartModels.get(position).name);
        holder.quantity.setText(String.valueOf(cartModels.get(position).quantity));

            holder.pulse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(holder.quantity.getText().toString());
                    count++;

                    holder.quantity.setText(Integer.toString(count));
                }
            });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.quantity.getText().toString());
                if(count==0){
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        CartModel selectedItem = cartModels.get(adapterPosition);
                        String itemName = selectedItem.name;
                        String itemPrice = selectedItem.price;

                        DBLogin dbLogin = new DBLogin(context);
                        dbLogin.removeCartItem(itemName,itemPrice);
                        cartModels.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);

                        Toast.makeText(context, "Item removed to cart", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    count--;
                    holder.quantity.setText(Integer.toString(count));


                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION){
                    CartModel selectedItem = cartModels.get(adapterPosition);
                    String itemName = selectedItem.name;
                    String itemPrice = selectedItem.price;

                    DBLogin dbLogin = new DBLogin(context);
                    dbLogin.removeCartItem(itemName,itemPrice);
                    cartModels.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);

                    Toast.makeText(context, "Item removed to cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

