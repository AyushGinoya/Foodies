package com.example.foodies.tabs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.foodies.R;
import com.example.foodies.userDatabase.DBLogin;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingFragment extends Fragment {
    private EditText fn;
    private EditText ln;
    private EditText num;
    private EditText dob;
    private EditText address;
    private Button save,logout;
    private DBLogin dbDetails;
    DatePickerDialog picker;
    public SettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        fn = view.findViewById(R.id.first_name);
        ln = view.findViewById(R.id.last_name);
        num = view.findViewById(R.id.e_phone_number);
        EditText email = view.findViewById(R.id.e_email);
        dob = view.findViewById(R.id.birth);
        address = view.findViewById(R.id.address);
        save = view.findViewById(R.id.save_btn);
        logout = view.findViewById(R.id.logout_btn);

        dbDetails = new DBLogin(getContext());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String f_name = fn.getText().toString();
                String l_name = ln.getText().toString();
                String number = num.getText().toString();
                String e_mail = email.getText().toString();
                String u_address =  address.getText().toString();
                String u_dob = dob.getText().toString();

                dob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH);
                        int year = calendar.get(Calendar.YEAR);

                        picker = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dob.setText(dayOfMonth + "/"+(month+1)+"/"+year);
                            }
                        },year,month,day);
                    }
                });

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

                dbDetails.saveDetails(e_mail,f_name,l_name,u_address,number,u_dob);
                Toast.makeText(requireContext(), "Save Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             // dbDetails.addProduct();
              // dbDetails.deleteTable2();

                boolean isProductsTableExists =dbDetails.isTableExists("cart_products");
                Log.d("Table","Table - " + isProductsTableExists);
            }
        });

        return view;
    }
}