package com.example.myproject.Class;

import android.graphics.drawable.Drawable;

public class HomeRecyclerClass {
    private Drawable image;
    private String name;

    public HomeRecyclerClass(Drawable image, String name) {
        this.image = image;
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
