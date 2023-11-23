package com.example.foodies.tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.foodies.R;
import com.example.foodies.userDatabase.DBLogin;

public class SettingFragment extends Fragment {
    private EditText fn;
    private EditText ln;
    private EditText num;
    private EditText dob;
    private EditText address;
    private Button save,logout;
    private DBLogin dbDetails;
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

        String f_name = fn.getText().toString();
        String l_name = ln.getText().toString();
        String number = num.getText().toString();
        String e_mail = email.getText().toString();
        String u_address =  address.getText().toString();
        String u_dob = dob.getText().toString();

        dbDetails = new DBLogin(getContext());
       dbDetails.onCreate(dbDetails.getWritableDatabase());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbDetails.saveDetails(e_mail,f_name,l_name,u_address,number,u_dob);
                Toast.makeText(requireContext(), "Save Clicked", Toast.LENGTH_SHORT).show();
                dbDetails.printTableDetails();
                Toast.makeText(requireContext(), "Save Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             // dbDetails.addProduct();
               //dbDetails.printTable3();

                boolean isProductsTableExists =dbDetails.isTableExists("cart_products");
                Log.d("Table","Table - " + isProductsTableExists);
            }
        });

        return view;
    }
}