package com.example.myproject.Class;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.R;

import java.util.ArrayList;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {
private final ArrayList<HomeRecyclerClass> itemsArray;
private final onHomeRVItemClickListener listener;

    public HomeRecyclerViewAdapter(ArrayList<HomeRecyclerClass> itemsArray, onHomeRVItemClickListener listener) {
        this.itemsArray = itemsArray;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_layout,null,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HomeRecyclerClass item=itemsArray.get(position);
        holder.home_item_img.setImageDrawable(item.getImage());
        holder.home_item_tv.setText(item.getName());

    }

    @Override
    public int getItemCount() {
        return itemsArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
TextView home_item_tv;
ImageView home_item_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            home_item_img= itemView.findViewById(R.id.home_item_img);
            home_item_tv= itemView.findViewById(R.id.home_item_tv);
            itemView.setOnClickListener(view -> listener.onItemClick(itemView));
        }
    }
}
