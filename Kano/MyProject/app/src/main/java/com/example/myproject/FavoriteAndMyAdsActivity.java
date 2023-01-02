package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.myads.MyAdsRecyclerViewAdapter;
import com.example.myproject.show.ShowRecyclerViewAdapter;
import com.example.myproject.show.onShowRecyclerViewItemClick;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteAndMyAdsActivity extends AppCompatActivity {
    RecyclerView myAdsRV;
    TextView textViewLoadingNoAds;
    ArrayList<String> favoriteArray;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Iraq");
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_and_my_ads);

        findViews();
        readFromFirebase();
    }
    public void findViews() {
        myAdsRV = findViewById(R.id.favorite_RV_my_ads);
        textViewLoadingNoAds = findViewById(R.id.favorite_TV_loading_no_ads_yet);
        intent = getIntent();
    }
    public void fillMyAdsRecyclerView(ArrayList<Ad> ads) {

        MyAdsRecyclerViewAdapter adapter = new MyAdsRecyclerViewAdapter(this,ads, (showItem, ad) -> deleteDialog(ad));

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
        myAdsRV.setHasFixedSize(true);
        myAdsRV.setLayoutManager(layoutManager);
        myAdsRV.setAdapter(adapter);
    }

    public void readFromFirebase(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Iraq");
        ArrayList<Ad> ads = new ArrayList<>();
        if (intent.getStringExtra(Keys.MY_ADS_WHICH_ONE_SHOW).equals(Keys.MY_ADS_SHOW_MY_ADS)){
            String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            DatabaseReference myRefMyAds = myRef.child("MyAds").child(uid);
            myRefMyAds.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ads.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        ads.add(0,dataSnapshot1.getValue(Ad.class));
                    }
                    fillMyAdsRecyclerView(ads);
                    prepareTextView(ads);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                }
            });
        } else if(intent.getStringExtra(Keys.MY_ADS_WHICH_ONE_SHOW).equals(Keys.MY_ADS_SHOW_FAVORITE_ADS)) {
           getFavoriteAds();

        }




    }
    public void readFavoriteFromFirebase() {
        // Read from the database

        myRef.child("All").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Ad> ads = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (favoriteArray.contains(dataSnapshot1.getValue(Ad.class).getKey()))
                    ads.add(0, dataSnapshot1.getValue(Ad.class));
                }
                prepareTextView(ads);
                ShowRecyclerViewAdapter ShRV = new ShowRecyclerViewAdapter(getBaseContext(), ads,favoriteArray, new onShowRecyclerViewItemClick() {
                    @Override
                    public void onShowItemClick(View showItem, Ad ad) {
                        Intent intent = new Intent(FavoriteAndMyAdsActivity.this, ShowSpecificItemActivity.class);
                        intent.putExtra("AD", ad);
                        startActivity(intent);
                    }

                    @Override
                    public void onPhoneButtonClick(String phone) {
                    }

                    @Override
                    public void onChatButtonClick(String uid) {
                    }

                    @Override
                    public void onFavoriteCheckButtonClick(boolean isChecked, Ad ad) {
                        addAndRemoveToFromFavorite(isChecked, ad);
                    }
                });

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 1);
                myAdsRV.setHasFixedSize(true);
                myAdsRV.setLayoutManager(layoutManager);
                myAdsRV.setAdapter(ShRV);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(FavoriteAndMyAdsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void getFavoriteAds(){
        favoriteArray = new ArrayList<>();

        myRef.child("Favorite")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        favoriteArray.clear();
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            favoriteArray.add( dataSnapshot1.getValue(String.class));
                        }
                        readFavoriteFromFirebase();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    public void deleteDialog(Ad ad) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Iraq");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            DatabaseReference myRef1 = myRef.child(ad.getType()).child(Objects.requireNonNull(ad.getKey()));
            DatabaseReference myRefAll = myRef.child("All").child(Objects.requireNonNull(ad.getKey()));
            DatabaseReference myRefMyAds = myRef.child("MyAds").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(Objects.requireNonNull(ad.getKey()));
            if (checkWIFI()){
                myRef1.removeValue();
                myRefAll.removeValue();
                myRefMyAds.removeValue();

            } else{
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {

        });

        builder.setTitle("Remove!");
        builder.setMessage("Are you sure you want to this item?");
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialogInterface -> {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#C11732"));
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#A0A0A0"));
        });
        alertDialog.show();
    }
    public boolean checkWIFI(){
        return true;
    }
    public void prepareTextView(ArrayList<Ad> ads){

        if (ads.size() <= 0){

            try {
                myAdsRV.setVisibility(View.GONE);
                textViewLoadingNoAds.setVisibility(View.VISIBLE);
                textViewLoadingNoAds.setText(getString(R.string.no_ads_yet));
            }catch (Exception ignored){

            }

        }
        else{
            try {
                myAdsRV.setVisibility(View.VISIBLE);
                textViewLoadingNoAds.setVisibility(View.GONE);
            }catch (Exception ignored){

            }

        }
    }



    public void addAndRemoveToFromFavorite(boolean isChecked, Ad ad) {

        if (isChecked) {
            myRef.child("Favorite")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                    .child(ad.getKey())
                    .setValue(ad.getKey());
        } else {
            myRef.child("Favorite")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                    .child(ad.getKey())
                    .removeValue();
        }
    }
}