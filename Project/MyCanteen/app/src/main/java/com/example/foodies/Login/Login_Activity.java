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

public class Login_Activity extends AppCompatActivity {

    private Button loginbtn;
    private DBLogin login;
    private EditText email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = new DBLogin(this);

        loginbtn = findViewById(R.id.loginbtn);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uemail = email.getText().toString();
                String password = pass.getText().toString();
                if (password.length() < 7) {
                    Toast.makeText(Login_Activity.this, "Password must be at least 7 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean flag = login.isUserExists(uemail, password);
                if (flag) {
                    Intent i = new Intent(Login_Activity.this, com.example.foodies.MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Login_Activity.this, "Email or Password Incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
