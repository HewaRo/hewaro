package com.example.myproject.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myproject.LoginPages.authenitcation_activty;
import com.example.myproject.R;
import com.google.firebase.auth.FirebaseAuth;


public class MyAccountFragment extends Fragment {
Button btnLogOut;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        findViews(view);
        btnLogOut.setOnClickListener(view1 -> logOut());
        return view;
    }
    public void findViews(View view){
        btnLogOut = view.findViewById(R.id.fragment_my_accounts_btn_log_out);
    }

    public void logOut(){
        FirebaseAuth fbA = FirebaseAuth.getInstance();
        fbA.signOut();
        Intent intent=new Intent(getContext(), authenitcation_activty.class);
        startActivity(intent);
        requireActivity().finish();
    }

}