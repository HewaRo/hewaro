package com.example.myproject.myads;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject.Ad;
import com.example.myproject.R;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MyAdsRecyclerViewAdapter extends RecyclerView.Adapter<MyAdsRecyclerViewAdapter.ViewHolder> {
    private final ArrayList<Ad> adsArray;
    private final onMyAdsRecyclerViewItemClick listener;
    private final Context context;

    public MyAdsRecyclerViewAdapter(Context context, ArrayList<Ad> adsArray, onMyAdsRecyclerViewItemClick listener) {
        this.adsArray = adsArray;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_ads_recycle_items_layout, null, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Ad ad = adsArray.get(position);
        //holder.show_item_img.setImageDrawable());
        if (!ad.getImagesPaths().get(0).equals("")) {

            Glide.with(context).load(storage.getReference(ad.getImagesPaths().get(0))).placeholder(R.drawable.car_image).into(holder.my_ads_item_img);
        }

        holder.my_ads_item_tv_type.setText(ad.getType());
        holder.my_ads_item_tv_details.setText(ad.getInformation());
        holder.my_ads_item_tv_governorate.setText(ad.getLocation());
        if (ad.getPhoneNumber() != null && ad.getPhoneNumber().length() >= 7) {
            holder.my_ads_item_btn_phone_number.setText(ad.getPhoneNumber().substring(0, 6) + "XX");
        } else {
            holder.my_ads_item_btn_phone_number.setText("No phone");
        }
        holder.my_ads_item_tv_price.setText(ad.getPrice());
        holder.my_ads_item_tv_images_number.setText(String.valueOf(ad.getImagesPaths().size()));
        holder.position = holder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return adsArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        int position;
        TextView my_ads_item_tv_type,
                my_ads_item_tv_details,
                my_ads_item_tv_governorate,
                my_ads_item_tv_images_number,
                my_ads_item_tv_price;
        Button my_ads_item_btn_phone_number,
                my_ads_item_btn_chat;
        ImageView my_ads_item_img;
        CheckBox my_ads_item_check_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            my_ads_item_img = itemView.findViewById(R.id.my_ads_item_image_view);
            my_ads_item_tv_type = itemView.findViewById(R.id.my_ads_item_tv_type);
            my_ads_item_tv_details = itemView.findViewById(R.id.my_ads_item_tv_details);
            my_ads_item_tv_governorate = itemView.findViewById(R.id.my_ads_item_tv_governorate);
            my_ads_item_tv_images_number = itemView.findViewById(R.id.my_ads_item_tv_image_number);
            my_ads_item_tv_price = itemView.findViewById(R.id.my_ads_item_tv_price);
            my_ads_item_btn_phone_number = itemView.findViewById(R.id.my_ads_item_btn_phone_number);
            my_ads_item_btn_chat = itemView.findViewById(R.id.my_ads_item_btn_chat);
            my_ads_item_check_delete = itemView.findViewById(R.id.my_ads_item_check_delete);
            my_ads_item_check_delete.setOnClickListener(view -> listener.onDeleteButtonItemClick(itemView,adsArray.get(position)));
        }
    }
}
