package com.example.foodies.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.foodies.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    public HomeFragment() {
    }

    AutoCompleteTextView autoComplete;
    String[] foodItems;
    ImageSlider imageSlider;
    ListView listView;

    ArrayAdapter<String> arrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        autoComplete = view.findViewById(R.id.autoComplete);
         imageSlider= view.findViewById(R.id.image_slider);
         listView = view.findViewById(R.id.food_list);

        foodItems = new String[]{
                "Biryani", "Gujrati Thali", "Paneer Tikka", "Masala Dosa", "Chole Bhature",
                "Samosa", "Palak Paneer", "Idli Sambhar",
                "Pani Puri", "Aloo Paratha", "Vada Pav", "Panjabi",
                "Puff", "Thums Up", "Bhajiya", "Gathiya"
        };

        arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,foodItems);
        autoComplete.setAdapter(arrayAdapter);

        ArrayList<SlideModel> imageList = new ArrayList<>(); // Create image list

        imageList.add(new SlideModel(R.drawable.chole_bhature, "Chole Bhature", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.khaman, "Khaman", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.panjabi, "Panjabi", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.vada_pav, "Vada Pav", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.puff, "Puff ", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(imageList);

        int[] images = {R.drawable.chole_bhature, R.drawable.khaman, R.drawable.puff, R.drawable.vada_pav, R.drawable.panjabi};
        String[] foodName = {"Chole Bhature", "Khaman", "Puff", "Vada Pav", "Panjabi"};
        String[] foodPrice = {"50 ₹", "40 ₹", "25 ₹", "60 ₹", "45 ₹"};

        List<HashMap<String, String>> aList = new ArrayList<>();
        for (int i = 0; i < foodName.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("image", Integer.toString(images[i]));
            hashMap.put("name", foodName[i]);
            hashMap.put("price", foodPrice[i]);
            aList.add(hashMap);
        }

        String[] from = {"image", "name", "price"};
        int[] to = {R.id.img_home_food_list, R.id.food_name, R.id.food_prize};

        ListAdapter adapter = new SimpleAdapter(
                requireContext(),
                aList,
                R.layout.home_food_list,
                from,
                to
        );

        listView.setAdapter(adapter);


        // Inflate the layout for this fragment
        return view;
    }
}