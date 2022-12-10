package com.example.myproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddPostActivity extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView spinnerType, spinnerSecond, spinnerGovernorate, CarTypeSpinner, CarSecondTypeSpinner;
    ImageView image1, image2, image3;
    public final static int IMAGE_REQUEST_CODE = 1;
    ArrayList<Uri> uriArrayList;
    Button addBTN;
    ArraysLists arraysLists;
    AdapterSpinnerText typeAdapter, secondAdapter, governorateAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Iraq");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        findViews();
        spinnerType.setOnItemClickListener((adapterView, view, i, l) -> {
            TextView spinnerItemView = (TextView) view;
            String spinnerItemText = spinnerItemView.getText().toString();
            selectNextSpinnerAfterType(spinnerItemText);
        });


    }


    @Override
    public void onClick(View view) {
        if (view == image1 || view == image2 || view == image3) {
            selectImageIntent();
        }
        if (view == addBTN) {
            ArrayList<String> images = new ArrayList<>();
            images.add("image1");
            images.add("image2");
            images.add("image3");
            Ad ad = new Ad("Key",
                    Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),
                    "phoneNumber",
                    translateTypeToEnglish(spinnerType.getText().toString()),
                    "Location",
                    "description",
                    "information",
                    "price",
                    getDate(),
                    getTime(),
                    images);
            addAdToFirebase(ad);


        }
    }

    public void findViews() {
        CarTypeSpinner = findViewById(R.id.Add_Spinner_Governorate);
        CarSecondTypeSpinner = findViewById(R.id.Ads_CarSecondTypeSpinner);

        spinnerSecond = findViewById(R.id.Add_Spinner_Second);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        uriArrayList = new ArrayList<>();
        addBTN = findViewById(R.id.add_ad_button);
        addBTN.setOnClickListener(this);
        arraysLists = new ArraysLists(this);
        typeAdapter = new AdapterSpinnerText(this, R.layout.add, arraysLists.typesArrayLists);
        spinnerType = findViewById(R.id.Add_Spinner_Type);
        spinnerType.setAdapter(typeAdapter);

        //prepare governorates spinner
        spinnerGovernorate = findViewById(R.id.Ads_AddCitySpinner);
        governorateAdapter = new AdapterSpinnerText(this, R.layout.add, arraysLists.governorates);
        spinnerGovernorate.setAdapter(governorateAdapter);


    }

    public void selectImageIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent chooser = Intent.createChooser(intent, "Select pictures: ");
        startActivityForResult(chooser, IMAGE_REQUEST_CODE);
    }

    public void setImagesInViews() {
        image1.setImageURI(null);
        image2.setImageURI(null);
        image3.setImageURI(null);
        switch (uriArrayList.size()) {
            case 1:
                image1.setImageURI(uriArrayList.get(0));
                break;
            case 2:
                image1.setImageURI(uriArrayList.get(0));
                image2.setImageURI(uriArrayList.get(1));
                break;
            case 3:
                image1.setImageURI(uriArrayList.get(0));
                image2.setImageURI(uriArrayList.get(1));
                image3.setImageURI(uriArrayList.get(2));
                break;
            default:
                image1.setImageURI(uriArrayList.get(uriArrayList.size() - 3));
                image2.setImageURI(uriArrayList.get(uriArrayList.size() - 2));
                image3.setImageURI(uriArrayList.get(uriArrayList.size() - 1));
        }
    }

    public void addAdToFirebase(Ad ad) {
        String key = myRef.push().getKey();

        DatabaseReference myRef1 = myRef.child(ad.getType()).child(Objects.requireNonNull(key));
        DatabaseReference myRefAll = myRef.child("All").child(Objects.requireNonNull(key));
        DatabaseReference myRefMyAds = myRef.child("MyAds").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(Objects.requireNonNull(key));
        ad.setKey(key);
        myRefAll.setValue(ad);
        myRefMyAds.setValue(ad);
        myRef1.setValue(ad).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                //Toast.makeText(AddPostActivity.this, "Your ad has been published successfully!", Toast.LENGTH_SHORT).show();
                Toast.makeText(AddPostActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void selectNextSpinnerAfterType(String spinnerSelectedItemText) {
        ArrayList<String> afterType;
        if (spinnerSelectedItemText.equals(getString(R.string.Cars))) {
            afterType = arraysLists.afterChooseTypeCars;
        } else if (spinnerSelectedItemText.equals(getString(R.string.Motorcycles))) {
            afterType = arraysLists.afterChooseTypeMotor;
        } else {
            afterType = new ArrayList<>();
        }
        secondAdapter = new AdapterSpinnerText(this, R.layout.add, afterType);
        spinnerSecond.setAdapter(secondAdapter);
        if (!afterType.contains(spinnerSecond.getText().toString())) {
            spinnerSecond.setText("");
        }


    }

    public String translateTypeToEnglish(String typeInOtherLanguage) {
        String typeInEnglish = null;

        if (typeInOtherLanguage.equals(getString(R.string.Cars))) typeInEnglish = Keys.CARS;
        else if (typeInOtherLanguage.equals(getString(R.string.Motorcycles)))
            typeInEnglish = Keys.MOTORCYCLE;
        else if (typeInOtherLanguage.equals(getString(R.string.Mobile)))
            typeInEnglish = Keys.MOBILE;
        else if (typeInOtherLanguage.equals(getString(R.string.LaptopandPc)))
            typeInEnglish = Keys.LAPTOP_AND_PC;
        else if (typeInOtherLanguage.equals(getString(R.string.Elctronics)))
            typeInEnglish = Keys.ELECTRONICS;
        else if (typeInOtherLanguage.equals(getString(R.string.Realstate)))
            typeInEnglish = Keys.REALSTATE;

        return typeInEnglish;
    }

    public String getDate() {
        return convertArabicDigitsToEnglish(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
    }

    public String getTime() {
        return convertArabicDigitsToEnglish( new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
    }

    public String convertArabicDigitsToEnglish(String dateInArabic){
        return dateInArabic.replace('١','1')
                .replace('٢','2')
                .replace('٣','3')
                .replace('٤','4')
                .replace('٥','5')
                .replace('٦','6')
                .replace('٧','7')
                .replace('٨','8')
                .replace('٩','9')
                .replace('٠','0');
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {
            assert data != null;
            if (data.getClipData() != null) {
                int imagesCount = data.getClipData().getItemCount();
                for (int i = 0; i < imagesCount; i++) {
                    uriArrayList.add(data.getClipData().getItemAt(i).getUri());
                }
            } else if (data.getData() != null) {
                uriArrayList.add(data.getData());
            }
            setImagesInViews();
        }
    }
}