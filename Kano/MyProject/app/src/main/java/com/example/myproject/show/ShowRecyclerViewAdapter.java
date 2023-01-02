package com.example.myproject.show;

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

public class ShowRecyclerViewAdapter extends RecyclerView.Adapter<ShowRecyclerViewAdapter.ViewHolder> {
    private final ArrayList<Ad> adsArray;
    private final ArrayList<String> favoriteArray;
    private final onShowRecyclerViewItemClick listener;
    private final Context context;

    public ShowRecyclerViewAdapter(Context context, ArrayList<Ad> adsArray, ArrayList<String> favoriteArray, onShowRecyclerViewItemClick listener) {
        this.adsArray = adsArray;
        this.favoriteArray = favoriteArray;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_recycle_items_layout, null, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Ad ad = adsArray.get(position);
        //holder.show_item_img.setImageDrawable());
        if (!ad.getImagesPaths().get(0).equals("")) {

            Glide.with(context).load(storage.getReference(ad.getImagesPaths().get(0))).placeholder(R.drawable.car_image).into(holder.show_item_img);
        }

        holder.show_item_tv_type.setText(ad.getType());
        holder.show_item_tv_details.setText(ad.getInformation());
        holder.show_item_tv_governorate.setText(ad.getLocation());
        if (ad.getPhoneNumber() != null && ad.getPhoneNumber().length() >= 7) {
            holder.show_item_btn_phone_number.setText(ad.getPhoneNumber().substring(0, 6) + "XX");
        } else {
            holder.show_item_btn_phone_number.setText("No phone");
        }
        holder.show_item_tv_price.setText(ad.getPrice());
        holder.show_item_tv_images_number.setText(String.valueOf(ad.getImagesPaths().size()));
        holder.position = holder.getAdapterPosition();
        if (favoriteArray.contains(ad.getKey())) holder.show_item_check_favorite.setChecked(true);

    }

    @Override
    public int getItemCount() {
        return adsArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        int position;
        TextView show_item_tv_type,
                show_item_tv_details,
                show_item_tv_governorate,
                show_item_tv_images_number,
                show_item_tv_price;
        Button show_item_btn_phone_number,
                show_item_btn_chat;
        ImageView show_item_img;
        CheckBox show_item_check_favorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_item_img = itemView.findViewById(R.id.show_item_image_view);
            show_item_btn_phone_number = itemView.findViewById(R.id.show_item_btn_phone_number);
            show_item_btn_chat = itemView.findViewById(R.id.show_item_btn_chat);
            show_item_tv_type = itemView.findViewById(R.id.show_item_tv_type);
            show_item_tv_details = itemView.findViewById(R.id.show_item_tv_details);
            show_item_tv_governorate = itemView.findViewById(R.id.show_item_tv_governorate);
            show_item_tv_price = itemView.findViewById(R.id.show_item_tv_price);
            show_item_check_favorite = itemView.findViewById(R.id.show_item_check_favorite);
            show_item_tv_images_number = itemView.findViewById(R.id.show_item_tv_image_number);
            itemView.setOnClickListener(view -> listener.onShowItemClick(itemView, adsArray.get(position)));



            show_item_btn_phone_number.setOnClickListener(view -> {
                String phoneNumber = adsArray.get(position).getPhoneNumber();
                listener.onPhoneButtonClick(phoneNumber);
            });


            show_item_btn_chat.setOnClickListener(view -> listener.onChatButtonClick(adsArray.get(position).getUid()));
            show_item_check_favorite.setOnClickListener(view -> listener.onFavoriteCheckButtonClick(show_item_check_favorite.isChecked(), adsArray.get(position)));

        }
    }


}
