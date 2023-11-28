package com.example.foodies.userDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodies.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfile extends AppCompatActivity {
    private EditText num;
    private EditText id;
    private EditText email;
    private Button save,logout;
    private DBLogin dbDetails;
   Intent intent;
   String my_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        num = findViewById(R.id.e_phone_number);
        email = findViewById(R.id.e_email);
        save = findViewById(R.id.save_btn);
        id = findViewById(R.id.esid);
        intent = getIntent();
        my_email = intent.getStringExtra("email");


        dbDetails = new DBLogin(getApplicationContext());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = num.getText().toString();
                String e_mail = email.getText().toString();
                String sid = id.getText().toString();

                if(e_mail.isEmpty()){
                    e_mail = my_email;
                }

                if(number.isEmpty()){
                    number = intent.getStringExtra("number");
                }

                if(sid.isEmpty()){
                    sid = intent.getStringExtra("id");
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(e_mail).matches()){
                    email.setError("Valid email require");
                    email.requestFocus();
                    return;
                }

                String mobileRegExp = "[6-9][0-9]{9}";
                Matcher matcher;
                Pattern mobilePatterns = Pattern.compile(mobileRegExp);
                matcher = mobilePatterns.matcher(number);

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

                dbDetails.updateDetails(my_email,sid,number,e_mail);

                Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}