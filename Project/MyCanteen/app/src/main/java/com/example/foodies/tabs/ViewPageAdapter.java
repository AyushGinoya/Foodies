package com.example.foodies.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

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
           return new SettingFragment();
       } else {
           return new HomeFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
