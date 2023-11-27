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
    String email;
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
            Bundle bundle = getArguments();

            if (bundle != null) {
                email = bundle.getString("email");
                if (email != null && !email.isEmpty()) {
                    cartModelsList = dbLogin.getAllCartItems(email);
                    int totalCost = calculateTotalCost(cartModelsList);
                    total.setText(Integer.toString(totalCost));


                    RecycleHomeAdapter recycleHomeAdapter = new RecycleHomeAdapter(email);
                   recycleHomeAdapter.setEmail(email);

                   RecycleCartAdapter recycleCartAdapter = new RecycleCartAdapter(email);
                   recycleCartAdapter.setEmail(email);

                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    cartAdapter = new RecycleCartAdapter(cartModelsList);
                    cartAdapter.setEmail(email);
                    recyclerView.setAdapter(cartAdapter);
                }
            }
        } catch (Exception e) {
            Log.d("LIST", "Error retrieving cart items: " + e.getMessage());
        }
    }

    private int calculateTotalCost(ArrayList<CartModel> cartModelsList) {
        int totalCost = 0;
        for (CartModel cartModel : cartModelsList) {
            String priceString = cartModel.price.replaceAll("[^0-9]", "");
            if (!priceString.isEmpty()) {
                int price = Integer.parseInt(priceString);
                totalCost += cartModel.quantity * price;
            }
        }
        return totalCost;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cart_rec);
        refreshLayout = view.findViewById(R.id.refresh);
        total = view.findViewById(R.id.amount);


        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        try (DBLogin dbLogin = new DBLogin(requireContext())) {
            Bundle bundle = getArguments();
            if (bundle != null) {
                email = bundle.getString("email");
            }
            RecycleHomeAdapter adapter = new RecycleHomeAdapter(email);
            adapter.setEmail(email);
            RecycleCartAdapter recycleCartAdapter = new RecycleCartAdapter(email);
            recycleCartAdapter.setEmail(email);
            if (email != null) {
                cartModelsList = dbLogin.getAllCartItems(email);
            } else {
                Log.d("Email", "Email is null");
            }
        }

        int totalCost = calculateTotalCost(cartModelsList);
        total.setText(Integer.toString(totalCost));


        cartAdapter = new RecycleCartAdapter(cartModelsList);
        cartAdapter.setEmail(email);
        recyclerView.setAdapter(cartAdapter);

        try {
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try (DBLogin dbLogin = new DBLogin(requireContext())) {
                        cartModelsList = dbLogin.getAllCartItems(email);
                    }
                    cartAdapter = new RecycleCartAdapter(cartModelsList);
                    cartAdapter.setEmail(email);
                    recyclerView.setAdapter(cartAdapter);
                    new Handler().postDelayed(() -> refreshLayout.setRefreshing(false),500);
                }
            });

        } catch (Exception e) {
            Log.d("LIST", "Error retrieving cart items: " + e.getMessage());
        }
        return view;
    }
}
