package com.example.myproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ShowActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textViewLoadingNoAds;
    Intent intent;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Iraq");
    String type;
    ArrayList<String> favoriteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        findViews();
        getFavoriteAds();



    }

    public void findViews() {
        recyclerView = findViewById(R.id.show_recycler_view);
        intent = getIntent();
        type = intent.getStringExtra(Keys.TYPE_KEY);
        setTitle(type);
        textViewLoadingNoAds = findViewById(R.id.show_TV_loading_no_ads_yet);

    }

    public void prepareTextView(ArrayList<Ad> ads) {

        if (ads.size() <= 0) {
            recyclerView.setVisibility(View.GONE);
            textViewLoadingNoAds.setVisibility(View.VISIBLE);
            textViewLoadingNoAds.setText(getString(R.string.no_ads_in_this_action_yet));
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textViewLoadingNoAds.setVisibility(View.GONE);
        }
    }

    public void readFromFirebase() {
        // Read from the database
        if (type.equals(Keys.ALL)) type = "All";
        myRef.child(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Ad> ads = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ads.add(0, dataSnapshot1.getValue(Ad.class));
                }
                prepareTextView(ads);
                ShowRecyclerViewAdapter ShRV = new ShowRecyclerViewAdapter(getBaseContext(), ads,favoriteArray, new onShowRecyclerViewItemClick() {
                    @Override
                    public void onShowItemClick(View showItem, Ad ad) {
                        Intent intent = new Intent(ShowActivity.this, ShowSpecificItemActivity.class);
                        intent.putExtra("AD", ad);
                        startActivity(intent);
                    }

                    @Override
                    public void onPhoneButtonClick(String phone) {
                        if (phone != null && phone.length() >= 7) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_DIAL);
                            Uri uri = Uri.parse("tel:"+ phone);
                            intent.setData(uri);
                            startActivity(intent);
                        }


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
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(ShRV);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(ShowActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
                        readFromFirebase();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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