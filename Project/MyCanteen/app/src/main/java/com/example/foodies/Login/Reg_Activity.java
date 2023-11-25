package com.example.foodies.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        email = findViewById(R.id.r_email);
        name = findViewById(R.id.r_name);
        password = findViewById(R.id.r_password);
        registerButton = findViewById(R.id.r_registerButton);
        loginButton = findViewById(R.id.r_loginButton);

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

                    if(uname.isEmpty()){
                        name.setError("Please Enter Name");
                        return;
                    }
                    if(uemail.isEmpty()){
                        email.setError("Please Enter Email");
                        return;
                    }
                    if(upassword.isEmpty()){
                        password.setError("Please Enter Password");
                        return;
                    }
                    if(!uemail.contains("@")){
                        email.setError("Enter Valid Email");
                        return;
                    }
                    User user = new User(uemail, upassword, uname);

                    login.addUser(user);

                    Intent intent = new Intent(Reg_Activity.this, MainActivity.class);
//                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("user_email", uemail);
                    Toast.makeText(Reg_Activity.this, "Welcome " + uname, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Reg_Activity", "Registration failed: " + e.getMessage());
                }
            }
        });
    }
}
