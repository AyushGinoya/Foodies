package com.example.foodies.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodies.MainActivity;
import com.example.foodies.R;
import com.example.foodies.userDatabase.DBLogin;
import com.example.foodies.userDatabase.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reg_Activity extends AppCompatActivity {
    private EditText email;
    private EditText name;
    private EditText password;
    private EditText dob;
    private EditText num;
    private EditText id;
    private DBLogin login;
    private Button registerButton;
   private TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        email = findViewById(R.id.r_email);
        name = findViewById(R.id.r_name);
        password = findViewById(R.id.r_password);
        registerButton = findViewById(R.id.r_registerButton);
        loginButton = findViewById(R.id.btn);
        num = findViewById(R.id.p_number);
        dob = findViewById(R.id.dob);
        id = findViewById(R.id.sid);

        login = new DBLogin(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Reg_Activity.this, Login_Activity.class);
               boolean t =  login.isTableExists("profile");
                Log.d("Table","Table - "+t);
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
                    String number = num.getText().toString();
                    String dOb = dob.getText().toString();
                    String sid = id.getText().toString();

                    String mobileRegExp = "[6-9][0-9]{9}";
                    Matcher matcher;
                    Pattern mobilePatterns = Pattern.compile(mobileRegExp);
                    matcher = mobilePatterns.matcher(number);

                    //dob
                    if(dOb.isEmpty()){
                        dob.setError("Please Enter Date of Birth");
                        dob.requestFocus();
                        return;
                    }

                    //id
                    if(sid.length() != 10){
                        id.setError("Enter valid id");
                        id.requestFocus();
                        return;
                    }

                    //number
                    if(number.length() != 10){
                        num.setError("Enter 10 digit number");
                        num.requestFocus();
                        return;
                    }

                    if(!matcher.find()){
                        num.setError("Enter valid Number");
                        num.requestFocus();
                        return;
                    }

                    //name
                    if(uname.isEmpty()){
                        name.setError("Please Enter Name");
                        name.requestFocus();
                        return;
                    }

                    //email
                    if(uemail.isEmpty()){
                        email.setError("Please Enter Email");
                        email.requestFocus();
                        return;
                    }
                    if(!Patterns.EMAIL_ADDRESS.matcher(uemail).matches()){
                        email.setError("Valid email require");
                        email.requestFocus();
                        return;
                    }

                    //password
                    if(upassword.isEmpty()){
                        password.setError("Please Enter Password");
                        password.clearComposingText();
                        password.requestFocus();
                        return;
                    }

                    User user = new User(uemail, upassword, uname);

                    login.addProduct();
                    login.addUser(user);
                    login.saveDetails(uemail,sid,number,dOb);
                        Intent intent = new Intent(Reg_Activity.this, MainActivity.class);
                        intent.putExtra("user_model",user);
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
