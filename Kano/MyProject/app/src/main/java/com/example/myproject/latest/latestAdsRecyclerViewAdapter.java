package com.example.myproject.latest;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.Ad;
import com.example.myproject.R;
import com.example.myproject.show.onShowRecyclerViewItemClick;

import java.util.ArrayList;

public class latestAdsRecyclerViewAdapter extends RecyclerView.Adapter<latestAdsRecyclerViewAdapter.ViewHolder> {
private final ArrayList<Ad> adsArray;
private final onLatestAdsRecyclerViewItemClick listener;

    public latestAdsRecyclerViewAdapter(ArrayList<Ad> adsArray, onLatestAdsRecyclerViewItemClick listener) {
        this.adsArray = adsArray;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_ads_recycle_item_layout,null,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Ad ad=adsArray.get(position);
        //holder.show_item_img.setImageDrawable());
        holder.latest_ads_item_tv_price.setText(ad.getTime());


    }

    @Override
    public int getItemCount() {
        return adsArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
TextView latest_ads_item_tv_price;
ImageView latest_ads_item_image_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            latest_ads_item_image_view= itemView.findViewById(R.id.latest_ads_item_image_view);
            latest_ads_item_tv_price= itemView.findViewById(R.id.latest_ads_item_tv_price);
            itemView.setOnClickListener(view -> listener.onLatestAdsItemClick(itemView));
        }
    }
}
