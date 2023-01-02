package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myproject.Fragments.ChatFragment;
import com.example.myproject.Fragments.HomeFragment;
import com.example.myproject.Fragments.MyAccountFragment;
import com.example.myproject.Fragments.MyAdsFragment;
import com.example.myproject.LoginPages.authenitcation_activty;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView btm;
    FirebaseAuth fbA;
    FloatingActionButton floatingBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        floatingBTN.setOnClickListener(view -> {
            floatingBTN.setEnabled(false);
            Intent intent = new Intent(this, AddPostActivity.class);
            startActivity(intent);
            floatingBTN.setEnabled(true);

        });

        btm.setOnItemSelectedListener(item -> {
            int item_id = item.getItemId();
            Fragment fragment = new HomeFragment();
            if (item_id == R.id.menu_bottom_navigation_home) {
                fragment = new HomeFragment();
            } else if (item_id == R.id.menu_bottom_navigation_chats) {
                fragment = new ChatFragment();
            } else if (item_id == R.id.menu_bottom_navigation_camera) {
                Intent intent = new Intent(this, AddPostActivity.class);
                startActivity(intent);
                return false;
            } else if (item_id == R.id.menu_bottom_navigation_my_ads) {
                fragment = new MyAdsFragment();
            } else if (item_id == R.id.menu_bottom_navigation_account) {
                fragment = new MyAccountFragment();
            }
            replaceFragment(fragment);
            return true;
        });
    }

    public void findViews() {
        fbA = FirebaseAuth.getInstance();
        btm = findViewById(R.id.main_bottom_navigation_view);
        replaceFragment(new HomeFragment());
        floatingBTN = findViewById(R.id.main_floating_action_button);
        floatingBTN.setEnabled(true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.up_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Logout) {
            fbA.signOut();
            Intent intent = new Intent(getBaseContext(), authenitcation_activty.class);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
