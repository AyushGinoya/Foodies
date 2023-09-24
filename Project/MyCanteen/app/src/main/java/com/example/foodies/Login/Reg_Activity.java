package com.example.foodies.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodies.MainActivity;
import com.example.foodies.R;
import com.example.foodies.userDatabase.DBLogin;
import com.example.foodies.userDatabase.User;

public class Reg_Activity extends AppCompatActivity {
    private EditText email;
    private EditText name;
    private EditText password;
    private DBLogin login;
    private Button registerButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        login = new DBLogin(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Reg_Activity.this, Login_Activity.class);
                startActivity(i);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uname = name.getText().toString();
                    String uemail = email.getText().toString();
                    String upassword = password.getText().toString();

                    User user = new User(uemail, upassword, uname);

                    // Assuming addUser can throw an exception, catch it and log it.
                    login.addUser(user);

                    Intent intent = new Intent(Reg_Activity.this, MainActivity.class);
                    intent.putExtra("uname", uname);
                    intent.putExtra("uemail", uemail);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Reg_Activity", "Registration failed: " + e.getMessage());
                }
            }
        });
    }
}
