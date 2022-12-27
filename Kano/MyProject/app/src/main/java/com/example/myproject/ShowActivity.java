package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.show.ShowRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Intent intent;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Iraq");
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        findViews();
        readFromFirebase();


    }

    public void findViews(){
        recyclerView = findViewById(R.id.show_recycler_view);
        intent  = getIntent();
        type = intent.getStringExtra(Keys.TYPE_KEY);
        setTitle(type);
    }
    public void readFromFirebase(){
        // Read from the database
        myRef.child(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Ad> ads= new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ads.add(dataSnapshot1.getValue(Ad.class));
                }
                ShowRecyclerViewAdapter ShRV = new ShowRecyclerViewAdapter(getBaseContext(),ads, showItem -> {
                });
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 1);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(ShRV);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(ShowActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}