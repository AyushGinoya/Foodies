package com.example.foodies;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView_d_user;
    private TextView textView_d_email;
    private ImageView user_img;
    private Toolbar toolbar;
    private EditText name;
    private EditText email;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_d_email = findViewById(R.id.user_email_inDrawer);
        textView_d_user = findViewById(R.id.user_name_inDrawer);
       toolbar = findViewById(R.id.toolbar);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
       drawerLayout = findViewById(R.id.drawer_layout);

        textView_d_email.setText(email.getText().toString());
        textView_d_user.setText(name.getText().toString());

        setSupportActionBar(toolbar);
    }

}