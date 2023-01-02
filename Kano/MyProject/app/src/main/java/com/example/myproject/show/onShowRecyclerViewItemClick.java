package com.example.myproject.show;

import android.view.View;

import com.example.myproject.Ad;

import java.util.ArrayList;

public interface onShowRecyclerViewItemClick {
    void onShowItemClick(View showItem, Ad ad);
    void onPhoneButtonClick(String phone);
    void onChatButtonClick(String uid);
    void onFavoriteCheckButtonClick(boolean isChecked, Ad ad);
}
