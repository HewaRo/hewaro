package com.example.myproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.myproject.Ad;
import com.example.myproject.ArraysLists;
import com.example.myproject.Class.HomeRecyclerClass;
import com.example.myproject.Class.HomeRecyclerViewAdapter;
import com.example.myproject.Keys;
import com.example.myproject.R;
import com.example.myproject.ShowActivity;
import com.example.myproject.latest.latestAdsRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView home_rv, latestAdsRV;
    ImageSlider imageSlider;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        fillTypesRecyclerView(view);
        readFromFirebase(view);
        prepareImageSlider();
        return view;
    }

    public void findViews(View view){

        imageSlider = view.findViewById(R.id.fragment_home_slider_image);
    }
    public void fillTypesRecyclerView(View view) {
        home_rv = view.findViewById(R.id.home_rv);
        ArrayList<HomeRecyclerClass> home_item_array = new ArrayList<>();

        ArraysLists arraysLists = new ArraysLists(requireActivity().getBaseContext());
        int size = Math.min(arraysLists.typesArrayLists.size(), arraysLists.typesImagesArrayLists.size()) - 1;
        for (int i = 0; i <= size; i++) {
            HomeRecyclerClass home_item_cars = new HomeRecyclerClass(arraysLists.typesImagesArrayLists.get(i), arraysLists.typesArrayLists.get(i));
            home_item_array.add(home_item_cars);
        }

        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(home_item_array, itemView -> {
            TextView textView = itemView.findViewById(R.id.home_item_tv);
            String type = translateTypeToEnglish(textView.getText().toString());
            onRecyclerViewItemClick(type);

        });
        // RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2,LinearLayoutManager.HORIZONTAL,false);
        home_rv.setHasFixedSize(true);
        home_rv.setLayoutManager(layoutManager);
        home_rv.setAdapter(adapter);
    }
    public void fillLatestAdsRecyclerView(View view, ArrayList<Ad> ads) {
        latestAdsRV = view.findViewById(R.id.fragment_home_RV_latest_ads);
        latestAdsRecyclerViewAdapter
         adapter = new latestAdsRecyclerViewAdapter(getContext(),ads, itemView -> {
            TextView textView = itemView.findViewById(R.id.latest_ads_item_tv_price);
            Toast.makeText(getContext(), "The price is: "+textView.getText().toString(), Toast.LENGTH_SHORT).show();

        });
        // RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3,RecyclerView.VERTICAL,false);
        latestAdsRV.setHasFixedSize(true);
        latestAdsRV.setLayoutManager(layoutManager);
        latestAdsRV.setAdapter(adapter);
    }

    public void onRecyclerViewItemClick(String type) {
        Intent intent = new Intent(requireActivity(), ShowActivity.class);
        intent.putExtra(Keys.TYPE_KEY, type);
        startActivity(intent);

    }

    public String translateTypeToEnglish(String typeInOtherLanguage) {
        String typeInEnglish = null;

        if (typeInOtherLanguage.equals(getString(R.string.Cars))) typeInEnglish = Keys.CARS;
        else if (typeInOtherLanguage.equals(getString(R.string.Motorcycles)))
            typeInEnglish = Keys.MOTORCYCLE;
        else if (typeInOtherLanguage.equals(getString(R.string.Mobile)))
            typeInEnglish = Keys.MOBILE;
        else if (typeInOtherLanguage.equals(getString(R.string.LaptopandPc)))
            typeInEnglish = Keys.LAPTOP_AND_PC;
        else if (typeInOtherLanguage.equals(getString(R.string.Elctronics)))
            typeInEnglish = Keys.ELECTRONICS;
        else if (typeInOtherLanguage.equals(getString(R.string.Realstate)))
            typeInEnglish = Keys.REALSTATE;

        return typeInEnglish;
    }

    public void readFromFirebase(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Iraq");

        // Read from the database
        ArrayList<Ad> ads = new ArrayList<>();

        myRef.child("All").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ads.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ads.add(0,dataSnapshot1.getValue(Ad.class));
                }
                // this code is to show just 21 items HewaRo
               while (ads.size()>21){
                    ads.remove(ads.size()-1);
                }
                fillLatestAdsRecyclerView(view,ads);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void prepareImageSlider(){
        SlideModel slideModel1 = new SlideModel(R.drawable.slider_image_3, null, ScaleTypes.CENTER_CROP);
        SlideModel slideModel2 = new SlideModel(R.drawable.slider_image_1, null, ScaleTypes.CENTER_CROP);
        SlideModel slideModel3 = new SlideModel(R.drawable.slider_image_2, null, ScaleTypes.CENTER_CROP);
        ArrayList<SlideModel> arrayList = new ArrayList<>();
        arrayList.add(slideModel1);
        arrayList.add(slideModel2);
        arrayList.add(slideModel3);
        imageSlider.setImageList(arrayList);
        imageSlider.startSliding(1500);
    }



}