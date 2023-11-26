package com.example.foodies.tabs;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodies.R;
import com.example.foodies.userDatabase.DBLogin;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    RecycleCartAdapter cartAdapter;
    ArrayList<CartModel> cartModelsList;
    SwipeRefreshLayout refreshLayout;
    TextView total;
    String email="ginoyaayushi@gmail.com";
    public CartFragment() {
    }
    @Override
    public void onResume() {
        super.onResume();

        refreshCartItems();
    }

    private void refreshCartItems() {
        try {
            DBLogin dbLogin = new DBLogin(requireContext());
            cartModelsList = dbLogin.getAllCartItems(email);

            int totalCost = 0;
            for (int i = 0; i < cartModelsList.size(); i++) {
                String priceString = cartModelsList.get(i).price;
                priceString = priceString.replaceAll("[^0-9]", "");

                if (!priceString.isEmpty()) {
                    int price = Integer.parseInt(priceString);
                    int quantity = cartModelsList.get(i).quantity;
                    totalCost += quantity * price;
                }
            }
            total.setText(String.valueOf(totalCost));

            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            cartAdapter = new RecycleCartAdapter(cartModelsList);
            recyclerView.setAdapter(cartAdapter);
        } catch (Exception e) {
            Log.d("LIST", "Error retrieving cart items: " + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cart_rec);
        refreshLayout = view.findViewById(R.id.refresh);
        total = view.findViewById(R.id.amount);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        try (DBLogin dbLogin = new DBLogin(requireContext())) {
            cartModelsList = dbLogin.getAllCartItems(email);
        }
        int totalCost = 0;
        for (int i = 0; i < cartModelsList.size(); i++) {
            String priceString = cartModelsList.get(i).price;
            priceString = priceString.replaceAll("[^0-9]", "");

            if (!priceString.isEmpty()) {
                int price = Integer.parseInt(priceString);
                int quantity = cartModelsList.get(i).quantity;
                totalCost += quantity * price;
            }

        }
        Log.d("COST" , "Cost - "+totalCost);
        total.setText(String.valueOf(totalCost) + "₹");


        cartAdapter = new RecycleCartAdapter(cartModelsList);
        recyclerView.setAdapter(cartAdapter);

        try {
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try (DBLogin dbLogin = new DBLogin(requireContext())) {
                        cartModelsList = dbLogin.getAllCartItems(email);
                    }
                    cartAdapter = new RecycleCartAdapter(cartModelsList);
                    recyclerView.setAdapter(cartAdapter);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            refreshLayout.setRefreshing(false);
                        }
                    },500);
                }
            });

        } catch (Exception e) {
            Log.d("LIST", "Error retrieving cart items: " + e.getMessage());
        }
        return view;
    }
}
