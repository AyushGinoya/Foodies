package com.example.foodies.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.foodies.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public HomeFragment() {
    }
    AutoCompleteTextView autoComplete;
    String[] foodItems;
    ImageSlider imageSlider;
    RecyclerView recyclerView;
    ArrayList<HomeModel> foodArray ;
    ArrayAdapter<String> arrayAdapter;
    String email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        View view1 = inflater.inflate(R.layout.home_food_list, container, false);
        autoComplete = view.findViewById(R.id.autoComplete);
        imageSlider = view.findViewById(R.id.image_slider);
        recyclerView = view.findViewById(R.id.recycler_food_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        foodArray = new ArrayList<>();

        //image slider
        foodItems = new String[]{
                "Biryani", "Gujrati Thali", "Paneer Tikka", "Masala Dosa", "Chole Bhature",
                "Samosa", "Palak Paneer", "Idli Sambhar",
                "Pani Puri", "Aloo Paratha", "Vada Pav", "Punjabi",
                "Puff", "Thums Up", "Bhajiya", "Gathiya"
        };

        arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, foodItems);
        autoComplete.setAdapter(arrayAdapter);

        ArrayList<SlideModel> imageList = new ArrayList<>();

        imageList.add(new SlideModel(R.drawable.chole_bhature, "Chole Bhature", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.khaman, "Khaman", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.panjabi, "Panjabi", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.vada_pav, "Vada Pav", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.puff, "Puff ", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(imageList);


        //recyclerview -  list
        int[] images = {R.drawable.chole_bhature, R.drawable.khaman, R.drawable.puff, R.drawable.vada_pav, R.drawable.panjabi};
        String[] foodName = {"Chole Bhature", "Khaman", "Puff", "Vada Pav", "Panjabi"};
        String[] foodPrice = {"50 ₹", "40 ₹", "25 ₹", "60 ₹", "45 ₹"};


        for (int i = 0; i < foodName.length; i++) {
            HomeModel model = new HomeModel(images[i], foodName[i], foodPrice[i]);
            foodArray.add(model);
        }


        RecycleHomeAdapter adapter = new RecycleHomeAdapter(getContext(), foodArray);
        Bundle bundle = getArguments();
        if (bundle != null) {
            email = bundle.getString("email");
            adapter.setEmail(email);
            RecycleCartAdapter adapter1 = new RecycleCartAdapter(email);
            adapter1.setEmail(email);
            recyclerView.setAdapter(adapter);
        }

            // Inflate the layout for this fragment
            return view;
        }
}