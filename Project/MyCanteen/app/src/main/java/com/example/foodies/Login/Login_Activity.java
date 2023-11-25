package com.example.foodies.Login;

import android.content.Intent;
import android.os.Bundle;
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
//                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("user_email", uemail);
//                editor.apply();


                if(uemail.equals("") || password.equals("")){
                    Toast.makeText(Login_Activity.this, "Please Enter All The Fields", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    if(flag){
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(Login_Activity.this, "Email or Password Incorrect", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });
    }
}
