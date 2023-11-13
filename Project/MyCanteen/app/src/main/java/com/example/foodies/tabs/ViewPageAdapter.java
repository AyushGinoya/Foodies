package com.example.foodies.tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPageAdapter extends FragmentStateAdapter {
    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       if(position==0) {
           return new HomeFragment();
       }else if (position==1) {
           return new CartFragment();
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
