package com.example.myproject.show;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject.Ad;
import com.example.myproject.R;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ShowRecyclerViewAdapter extends RecyclerView.Adapter<ShowRecyclerViewAdapter.ViewHolder> {
    private final ArrayList<Ad> adsArray;
    private final onShowRecyclerViewItemClick listener;
    private final Context context;

    public ShowRecyclerViewAdapter(Context context,ArrayList<Ad> adsArray, onShowRecyclerViewItemClick listener) {
        this.adsArray = adsArray;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_recycle_items_layout, null, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Ad ad = adsArray.get(position);
        //holder.show_item_img.setImageDrawable());
        if (!ad.getImagesPaths().get(0).equals("")) {

            Glide.with(context).load(storage.getReference(ad.getImagesPaths().get(0))).placeholder(R.drawable.car_image).into(holder.show_item_img);
        }
        holder.show_item_tv_date.setText(ad.getDate());
        holder.show_item_tv_time.setText(ad.getTime());

    }

    @Override
    public int getItemCount() {
        return adsArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView show_item_tv_date;
        TextView show_item_tv_time;
        ImageView show_item_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_item_img = itemView.findViewById(R.id.show_item_image_view);
            show_item_tv_date = itemView.findViewById(R.id.show_item_tv_image_number);
            show_item_tv_time = itemView.findViewById(R.id.show_item_tv_price);
            itemView.setOnClickListener(view -> listener.onShowItemClick(itemView));
        }
    }
}
