package com.example.foodies.tabs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodies.R;
import com.example.foodies.userDatabase.DBLogin;
import com.example.foodies.userDatabase.UpdateProfile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingFragment extends Fragment {

    private TextView email, number, sid, dob;
    private Button u_profile;
    ImageButton button;
    private final static int PICK_IMAGE_REQUEST_CODE = 1;
    CircleImageView circleImageView;
    String my_Email="";
    DBLogin dbLogin;

    public SettingFragment() {
    }
    @Override
    public void onResume() {
        super.onResume();

        Bitmap bitmap = dbLogin.getImageFroProfile(my_Email);
        if(bitmap!=null) {
            circleImageView.setImageBitmap(bitmap);
        }

        Bundle bundle1 = getArguments();
        if (bundle1 != null) {
            my_Email = bundle1.getString("email");
        }
        email.setText(my_Email);
        HashMap<String, String> retrievedDetails = dbLogin.retrieveDetails(my_Email);

        if (retrievedDetails != null && !retrievedDetails.isEmpty()) {
            String emailValue = retrievedDetails.get("email");
            String dobValue = retrievedDetails.get("dob");
            String numberValue = retrievedDetails.get("phone_number");
            String idValue = retrievedDetails.get("student_id");


            email.setText(emailValue);
            dob.setText(dobValue);
            number.setText(numberValue);
            sid.setText(idValue);

        } else {
            Log.d("Retrieved Details", "No details found for the email: " + my_Email);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        dbLogin = new DBLogin(requireContext());
        email = view.findViewById(R.id.email1);
        sid = view.findViewById(R.id.my_id);
        number = view.findViewById(R.id.number);
        dob = view.findViewById(R.id.dob2);
        u_profile = view.findViewById(R.id.update_profile);
        button = view.findViewById(R.id.take_photo);
        circleImageView = view.findViewById(R.id.user_img_profile);

        Bundle bundle1 = getArguments();
        if (bundle1 != null) {
            my_Email = bundle1.getString("email");
        }
        email.setText(my_Email);
        HashMap<String, String> retrievedDetails = dbLogin.retrieveDetails(my_Email);

        if (retrievedDetails != null && !retrievedDetails.isEmpty()) {
            String emailValue = retrievedDetails.get("email");
            String dobValue = retrievedDetails.get("dob");
            String numberValue = retrievedDetails.get("number");
            String idValue = retrievedDetails.get("student_id");


            email.setText(emailValue);
            dob.setText(dobValue);
            number.setText(numberValue);
            sid.setText(idValue);

        } else {
            Log.d("Retrieved Details", "No details found for the email: " + my_Email);
        }


        u_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateProfile.class);
                intent.putExtra("email",my_Email);
                intent.putExtra("number", number.getText().toString());
                intent.putExtra("id", sid.getText().toString());
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");

                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    //noinspection deprecation
                    startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            byte[] img;
            try {
                InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

                int bufferSize;
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    byteBuffer.write(buffer, 0, len);
                }

                img = byteBuffer.toByteArray();
                inputStream.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            dbLogin.addImageToProfile(my_Email, img);
            circleImageView.setImageURI(uri);
        }
    }
}
