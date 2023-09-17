package com.example.foodies.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodies.userDatabase.DBLogin;
import com.example.foodies.MainActivity;
import com.example.foodies.R;
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

        login = new DBLogin(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(i);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = name.getText().toString();
                String uemail = email.getText().toString();
                String upassword = password.getText().toString();

                if (uname.length() < 4) {
                    Toast.makeText(Reg_Activity.this, "Enter a name with at least 4 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (upassword.length() < 7) {
                    Toast.makeText(Reg_Activity.this, "Enter a name with at least 7 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ValidEmail.isValidEmail(uemail)) {
                    Toast.makeText(Reg_Activity.this, "Enter Valid Email", Toast.LENGTH_LONG).show();
                    return;
                }

                User user = new User(uemail, upassword, uname);

                user.setName(uname);
                user.setEmail(uemail);
                user.setPassword(upassword);
                login.addUser(user);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
