package com.example.foodies.tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.userDatabase.DBLogin;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    RecycleCartAdapter cartAdapter;
    ArrayList<CartModel> cartModelsList;

    public CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        try {
            try (DBLogin dbLogin = new DBLogin(requireContext())) {
                cartModelsList = dbLogin.getAllCartItems();
            }
            Log.d("LIST", "Cart Items Count: " + cartModelsList.size());

            for (CartModel cartItem : cartModelsList) {
                Log.d("CART_ITEM", "Name: " + cartItem.name +
                        ", Price: " + cartItem.price +
                        ", Quantity: " + cartItem.quantity);
            }

            cartAdapter = new RecycleCartAdapter(cartModelsList);
            recyclerView.setAdapter(cartAdapter);
        } catch (Exception e) {
            Log.d("LIST", "Error retrieving cart items: " + e.getMessage());
        }

        return view;
    }
}
