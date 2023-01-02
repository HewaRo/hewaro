package com.example.myproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.AddPostActivity;
import com.example.myproject.ArraysLists;
import com.example.myproject.FavoriteAndMyAdsActivity;
import com.example.myproject.Keys;
import com.example.myproject.R;
import com.example.myproject.listMyAds.ListMyAdsRecyclerViewAdapter;

import java.util.ArrayList;


public class MyAdsFragment extends Fragment {
    RecyclerView myRV;
    CardView cardViewMyAds, cardViewAddPost;

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
        fillMyAdsRecyclerView();
        cardViewMyAds.setOnClickListener(view1 -> {
            Intent intent= new Intent(requireContext(), FavoriteAndMyAdsActivity.class);
            intent.putExtra(Keys.MY_ADS_WHICH_ONE_SHOW,Keys.MY_ADS_SHOW_MY_ADS);
            startActivity(intent);
        });
        cardViewAddPost.setOnClickListener(view1 -> {
            Intent intent= new Intent(requireContext(), AddPostActivity.class);
            startActivity(intent);
        });
        return view;
    }

    public void findViews(View view) {
        myRV = view.findViewById(R.id.fragment_my_ads_recycler_view);
        cardViewMyAds = view.findViewById(R.id.fragment_my_ads_card_my_ads);
        cardViewAddPost = view.findViewById(R.id.fragment_my_ads_card_add_post);
    }

    public void fillMyAdsRecyclerView() {
        ArrayList<Integer> counterArraylist = new ArrayList<>();
        counterArraylist.add(0);
        counterArraylist.add(0);
        counterArraylist.add(0);
        ListMyAdsRecyclerViewAdapter adapter = new
                ListMyAdsRecyclerViewAdapter
                (new ArraysLists(requireContext()).listMyAds,
                        counterArraylist, showItem -> {
                    TextView textView = showItem.findViewById(R.id.list_my_ads_tv_name);
                    if (textView.getText().toString().equals(getString(R.string.favorite_ads))){
                        Intent intent= new Intent(requireContext(), FavoriteAndMyAdsActivity.class);
                        intent.putExtra(Keys.MY_ADS_WHICH_ONE_SHOW,Keys.MY_ADS_SHOW_FAVORITE_ADS);
                        startActivity(intent);
                    }
        });

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false);
        myRV.setHasFixedSize(true);
        myRV.setLayoutManager(layoutManager);
        myRV.setAdapter(adapter);
    }


}