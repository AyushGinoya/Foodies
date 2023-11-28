package com.example.foodies.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodies.tabs.CartFragment;
import com.example.foodies.tabs.HomeFragment;
import com.example.foodies.tabs.SettingFragment;

public class ViewPageAdapter extends FragmentStateAdapter {
    String email;
    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity, String email) {
        super(fragmentActivity);
        this.email = email;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       if(position==0) {
           HomeFragment fragment = new HomeFragment();
           if(fragment!=null){
               Bundle bundle = new Bundle();
               bundle.putString("email", email);
               fragment.setArguments(bundle);
           }
           return fragment;
       }else if (position==1) {
           CartFragment fragment = new CartFragment();
           if(fragment!=null){
               Bundle bundle = new Bundle();
               bundle.putString("email", email);
               fragment.setArguments(bundle);
           }
           return fragment;
       }else if(position==2){
           SettingFragment fragment = new SettingFragment();
           if(fragment!=null){
               Bundle bundle = new Bundle();
               bundle.putString("email", email);
               fragment.setArguments(bundle);
           }
           return fragment;
       } else {
           HomeFragment fragment = new HomeFragment();
           if(fragment!=null){
               Bundle bundle = new Bundle();
               bundle.putString("email", email);
               fragment.setArguments(bundle);
           }
           return fragment;
       }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
