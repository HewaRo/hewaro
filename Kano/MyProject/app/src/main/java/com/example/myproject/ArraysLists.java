package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ArraysLists extends AppCompatActivity{
    public ArrayList<String> typesArrayLists = new ArrayList<>();
    public ArrayList<Drawable> typesImagesArrayLists = new ArrayList<>();
    public ArrayList<String> afterChooseTypeCars = new ArrayList<>();
    public ArrayList<String> afterChooseTypeMotor = new ArrayList<>();
    public ArrayList<String> governorates = new ArrayList<>();
    public ArrayList<String> listMyAds = new ArrayList<>();


    @SuppressLint("UseCompatLoadingForDrawables")
    public ArraysLists(Context context) {
        //typesArrayLists
        typesArrayLists.add(context.getString(R.string.Cars));
        typesArrayLists.add(context.getString(R.string.Motorcycles));
        typesArrayLists.add(context.getString(R.string.Mobile));
        typesArrayLists.add(context.getString(R.string.Laptop_and_Pc));
        typesArrayLists.add(context.getString(R.string.Electronics));
        typesArrayLists.add(context.getString(R.string.Realstate));
        typesArrayLists.add(context.getString(R.string.Pets));
        typesArrayLists.add(context.getString(R.string.Home));
        typesArrayLists.add(context.getString(R.string.Fashion));
        typesArrayLists.add(context.getString(R.string.Sports));
        typesArrayLists.add(context.getString(R.string.Food));
        typesArrayLists.add(context.getString(R.string.all));


        //typesImagesArraylists

        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.car_png,null));
        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.motorcycel_image,null));
        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.mobile_image,null));
        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.laptop_image,null));
        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.electronics_image,null));
        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.realstate_png,null));
        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.pet_png,null));
        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.home_couch_png,null));
        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.fashion_png,null));
        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.sport_png,null));
        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.food_png,null));
        typesImagesArrayLists.add(context.getResources().getDrawable(R.drawable.all_png,null));

        //afterChooseTypeCars
        afterChooseTypeCars.add(context.getString(R.string.Cars));
        afterChooseTypeCars.add(context.getString(R.string.wheels));
        afterChooseTypeCars.add(context.getString(R.string.spare_parts_of_cars));
        afterChooseTypeCars.add(context.getString(R.string.heavy_machinery));
        afterChooseTypeCars.add(context.getString(R.string.boats));

        //afterChooseTypeMotor
        afterChooseTypeMotor.add(context.getString(R.string.Motorcycles));
        afterChooseTypeMotor.add(context.getString(R.string.wheels));
        afterChooseTypeMotor.add(context.getString(R.string.spare_parts_of_cars));

        // Governorates
        governorates.add(context.getString(R.string.duhok));
        governorates.add(context.getString(R.string.erbil));
        governorates.add(context.getString(R.string.sulaymaniyah));
        governorates.add(context.getString(R.string.kirkuk));
        governorates.add(context.getString(R.string.halabja));
        governorates.add(context.getString(R.string.al_Anbar));
        governorates.add(context.getString(R.string.babil));
        governorates.add(context.getString(R.string.baghdad));
        governorates.add(context.getString(R.string.basra));
        governorates.add(context.getString(R.string.dhi_Qar));
        governorates.add(context.getString(R.string.al_qadisiyyah));
        governorates.add(context.getString(R.string.diyala));
        governorates.add(context.getString(R.string.karbala));
        governorates.add(context.getString(R.string.muthanna));
        governorates.add(context.getString(R.string.maysan));
        governorates.add(context.getString(R.string.najaf));
        governorates.add(context.getString(R.string.ninawa));
        governorates.add(context.getString(R.string.salah_al_din));
        governorates.add(context.getString(R.string.wasit));

        //List my ads
        listMyAds.add(context.getString(R.string.drafts));
        listMyAds.add(context.getString(R.string.favorite_ads));
        listMyAds.add(context.getString(R.string.favorite_searches));
        listMyAds.add(context.getString(R.string.recently_viewed));
        listMyAds.add(context.getString(R.string.recently_searches));
        listMyAds.add(context.getString(R.string.following_ads));
        listMyAds.add(context.getString(R.string.job_applications));

    }


}
