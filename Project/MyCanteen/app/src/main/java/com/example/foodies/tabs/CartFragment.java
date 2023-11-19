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

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    RecycleHomeAdapter recycleHomeAdapter;
    RecycleCartAdapter cartAdapter;
    ArrayList<CartModel> cartModelsList;

    public CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recycleHomeAdapter = new RecycleHomeAdapter();

        cartModelsList = recycleHomeAdapter.getCartModels();
        Log.d("CartFragment", "Cart Items Count: " + cartModelsList.size());

        cartAdapter = new RecycleCartAdapter(cartModelsList, getContext());
        recyclerView.setAdapter(cartAdapter);
        return view;
    }
}
