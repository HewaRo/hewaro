package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ShowSpecificItemActivity extends AppCompatActivity {
    ImageSlider imageSlider;
    Button btnCal, btnPhone, btnChat, btnFavorite, btnShare;
    TextView tvPrice, tvType, tvGovernorate;
    TextInputEditText tvDetails;
    Ad ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_specific_item);
        findViews();
        prepareOtherViews();
        prepareImageSlider();
    }
    public void findViews(){
        Intent intent= getIntent();
        ad = (Ad) intent.getSerializableExtra("AD");
        imageSlider = findViewById(R.id.show_specific_item_image);
        btnCal = findViewById(R.id.show_specific_item_btn_calculate_your_loan);
        btnPhone = findViewById(R.id.show_specific_item_btn_phone_number);
        btnChat = findViewById(R.id.show_specific_item_btn_chat);
        btnFavorite = findViewById(R.id.show_specific_item_btn_favorite);
        btnShare = findViewById(R.id.show_specific_item_btn_share);
        tvPrice = findViewById(R.id.show_specific_item_tv_price);
        tvType = findViewById(R.id.show_specific_item_tv_type);
        tvGovernorate = findViewById(R.id.show_specific_item_tv_governorate);
        tvDetails = findViewById(R.id.show_specific_item_tv_details);

    }


    public void prepareOtherViews(){
        tvType.setText(ad.getType());
        btnPhone.setText(ad.getPhoneNumber());
        tvGovernorate.setText(ad.getLocation());
        tvDetails.setText(ad.getInformation());
        tvPrice.setText(ad.getPrice());
    }
    public void prepareImageSlider(){

        ArrayList<SlideModel> arrayListImages = new ArrayList<>();
        String prePath ="https://firebasestorage.googleapis.com/v0/b/myproject-249fb.appspot.com/o/";
        String surPath ="?alt=media&token=39022a83-1eda-40fb-b5de-3d927a2a20ce";

        for (String path : ad.getImagesPaths()){
            String path1 = path.substring(1).replace("/","%2F");
            SlideModel slideModel0 = new SlideModel(prePath+path1+surPath,null,ScaleTypes.CENTER_CROP);
            arrayListImages.add(slideModel0);
        }

        imageSlider.setImageList(arrayListImages);
        imageSlider.startSliding(3000);

    }
}