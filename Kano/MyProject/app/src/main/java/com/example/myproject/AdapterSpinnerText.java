package com.example.myproject;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class AdapterSpinnerText extends ArrayAdapter<String> {

    public AdapterSpinnerText(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }
}
