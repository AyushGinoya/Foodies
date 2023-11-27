package com.example.foodies.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodies.R;
import com.example.foodies.userDatabase.DBLogin;

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
                boolean flag = login.isUserExists(uemail, password);

                Intent i = new Intent(Login_Activity.this, com.example.foodies.MainActivity.class);
                i.putExtra("email",uemail);

                if(uemail.equals("") || password.equals("")){
                    Toast.makeText(Login_Activity.this, "Please Enter All The Fields", Toast.LENGTH_LONG).show();
                    return;
                }else if(!Patterns.EMAIL_ADDRESS.matcher(uemail).matches()){
                    email.setError("Valid email require");
                    email.requestFocus();
                    return;
                } else {
                    if(flag){
                        startActivity(i);
                    }
                    else {
                        pass.clearComposingText();
                        Toast.makeText(Login_Activity.this, "Email or Password Incorrect", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });
    }
}
