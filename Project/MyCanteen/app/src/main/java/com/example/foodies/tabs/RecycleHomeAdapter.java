package com.example.foodies.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.userDatabase.DBLogin;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class RecycleHomeAdapter extends RecyclerView.Adapter<RecycleHomeAdapter.ViewHolder> {
     ArrayList<HomeModel> models;
    Context context;

    public RecycleHomeAdapter() {
    }

    DBLogin login;
    public RecycleHomeAdapter(Context  context, ArrayList<HomeModel> models) {
        this.context = context;
        this.models = models;
        login = new DBLogin(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_food_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.recycle_img.setImageResource(models.get(position).img);
        holder.recycle_food_name.setText(models.get(position).f_name);
        holder.recycle_food_prize.setText(models.get(position).f_prize);

        holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    HomeModel selectedItem = models.get(adapterPosition);
                    String itemName = selectedItem.f_name;
                    String itemPrice = selectedItem.f_prize;
                    int itemImage = selectedItem.img;

                    Drawable drawable = ContextCompat.getDrawable(context, itemImage);
                    assert drawable != null;
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    login.addToCart("email",itemName, itemPrice, byteArray);

                    Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView recycle_img;
        TextView recycle_food_name,recycle_food_prize;
        Button add_to_cart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recycle_img = itemView.findViewById(R.id.img);
            recycle_food_name = itemView.findViewById(R.id.food_name);
            recycle_food_prize = itemView.findViewById(R.id.food_prize);
            add_to_cart = itemView.findViewById(R.id.add_to_cart);
        }
    }


}
