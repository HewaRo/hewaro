package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterSpinnerText extends ArrayAdapter<String> {


    public AdapterSpinnerText(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }
}
