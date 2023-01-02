package com.example.myproject.listMyAds;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.R;

import java.util.ArrayList;

public class ListMyAdsRecyclerViewAdapter extends RecyclerView.Adapter<ListMyAdsRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<String> itemNameArray;
    private final ArrayList<Integer> itemCounterArray;
    private final onListMyAdsRecyclerViewItemClick listener;


    public ListMyAdsRecyclerViewAdapter(  ArrayList<String> itemNameArray,ArrayList<Integer> itemCounterArray, onListMyAdsRecyclerViewItemClick listener) {
        this.itemNameArray = itemNameArray;
        this.itemCounterArray = itemCounterArray;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_my_ads_rv_item, null, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position <3){

            holder.list_my_ads_item_tv_counter.setText(String.valueOf(itemCounterArray.get(position)));
        }else{
            holder.list_my_ads_item_tv_counter.setText("");
        }
        holder.list_my_ads_item_tv_name.setText(itemNameArray.get(position));



    }

    @Override
    public int getItemCount() {
        return itemNameArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView list_my_ads_item_tv_name,
         list_my_ads_item_tv_counter;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            list_my_ads_item_tv_name = itemView.findViewById(R.id.list_my_ads_tv_name);
            list_my_ads_item_tv_counter = itemView.findViewById(R.id.list_my_ads_tv_counter);
            itemView.setOnClickListener(view -> listener.onListItemClick(itemView));

        }
    }


}
