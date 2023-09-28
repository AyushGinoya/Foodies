package com.example.foodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.DrawerFragment.AccountFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private TextView textView_d_user;
    private TextView textView_d_email;
    private ImageView user_img;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_d_email = findViewById(R.id.user_email_inDrawer);
        textView_d_user = findViewById(R.id.user_name_inDrawer);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationBar);

        // Set the toolbar as the app's action bar
        setSupportActionBar(toolbar);

        // Create and configure the ActionBarDrawerToggle for the navigation drawer
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Set a listener for items in the navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Account) {
                    loadFragment(new AccountFragment());
                } else if (id == R.id.feedback) {

                } else if (id == R.id.about) {

                } else {

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Handle back button press
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Close the navigation drawer if it's open
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Call the default behavior (exit the activity) if the drawer is closed
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment) {
        // Load a fragment into the activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();
    }
}
