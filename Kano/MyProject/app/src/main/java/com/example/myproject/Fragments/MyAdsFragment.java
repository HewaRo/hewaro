package com.example.myproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.Ad;
import com.example.myproject.R;
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


public class MyAdsFragment extends Fragment {
    RecyclerView myAdsRV;

    public MyAdsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_ads, container, false);
        findViews(view);
        readFromFirebase(view);
        return view;
    }

    public void findViews(View view) {
        myAdsRV = view.findViewById(R.id.fragment_my_ads_RV_my_ads);
    }

    public void fillMyAdsRecyclerView(ArrayList<Ad> ads) {

        ShowRecyclerViewAdapter adapter = new ShowRecyclerViewAdapter(ads, new onShowRecyclerViewItemClick() {
            @Override
            public void onShowItemClick(View showItem) {
                TextView textView = showItem.findViewById(R.id.show_item_tv_date);
                Toast.makeText(getContext(), "The date is: " + textView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
        myAdsRV.setHasFixedSize(true);
        myAdsRV.setLayoutManager(layoutManager);
        myAdsRV.setAdapter(adapter);
    }

    public void readFromFirebase(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Iraq");

        // Read from the database
        ArrayList<Ad> ads = new ArrayList<>();
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        myRef.child("MyAds").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ads.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ads.add(0,dataSnapshot1.getValue(Ad.class));
                }
                fillMyAdsRecyclerView(ads);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
    }
}