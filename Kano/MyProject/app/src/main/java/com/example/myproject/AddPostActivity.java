package com.example.myproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class AddPostActivity extends AppCompatActivity implements View.OnClickListener {

    // Views
    AutoCompleteTextView spinnerType, spinnerGovernorate;
    TextInputEditText etPhone, etPrice, etDetails;
    ImageView image1, image2, image3;
    Button addBTN;
    ProgressBar progressBar;
    MaterialButtonToggleGroup mBTGNewOLd, mBTGSaleRent, mBDollarOrDinar;

    // Others
    public final static int IMAGE_REQUEST_CODE = 1;
    ArrayList<Uri> uriArrayList;
    ArraysLists arraysLists;
    AdapterSpinnerText typeAdapter, governorateAdapter;

    // Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Iraq");
    FirebaseStorage storage;
    StorageReference storageRef;
    ArrayList<String> imagePathsInFirebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        findViews();
        spinnerType.setOnItemClickListener((adapterView, view, i, l) -> spinnerType.setError(null, null));
        spinnerGovernorate.setOnItemClickListener((adapterView, view, i, l) -> spinnerGovernorate.setError(null, null));


    }

    public void findViews() {
        // images
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        uriArrayList = new ArrayList<>();

        // material toggle button group
        mBTGNewOLd = findViewById(R.id.Add_Toggle_New_Old);
        mBTGSaleRent = findViewById(R.id.Add_Toggle_Sale_Rent);
        mBDollarOrDinar = findViewById(R.id.Add_Toggle_Dollar_Dinar);

        // add button
        addBTN = findViewById(R.id.add_ad_button);
        addBTN.setOnClickListener(this);

        //get arraylists class
        arraysLists = new ArraysLists(this);

        //prepare type spinner
        typeAdapter = new AdapterSpinnerText(this, R.layout.add, arraysLists.typesArrayLists);
        spinnerType = findViewById(R.id.Add_Spinner_Type);
        spinnerType.setAdapter(typeAdapter);

        //prepare governorates spinner
        spinnerGovernorate = findViewById(R.id.Add_Spinner_Governorate);
        governorateAdapter = new AdapterSpinnerText(this, R.layout.add, arraysLists.governorates);
        spinnerGovernorate.setAdapter(governorateAdapter);

        // Edit text
        etPhone = findViewById(R.id.Add_EditText_Phone);
        etPrice = findViewById(R.id.Add_EditText_Price);
        etDetails = findViewById(R.id.Add_EditText_Details);

        // firebase
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        imagePathsInFirebaseStorage = new ArrayList<>();

        //progress bar
        progressBar = findViewById(R.id.add_ad_progress_bar);
    }

    @Override
    public void onClick(View view) {
        if (view == image1 || view == image2 || view == image3) {
            selectImageIntent();
        }
        if (view == addBTN) {
            try {
                prepareProgressbar(true);
                uploadProfileImageToFirebase();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    // Images methods

    public void selectImageIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent chooser = Intent.createChooser(intent, "Select pictures: ");
        //noinspection deprecation
        startActivityForResult(chooser, IMAGE_REQUEST_CODE);
    }

    public void setImagesInViews() {
        image1.setImageURI(null);
        image2.setImageURI(null);
        image3.setImageURI(null);
        while (uriArrayList.size() > 3) {
            uriArrayList.remove(0);
        }
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

        }
    }

    // Firebase methods

    public void addAdToFirebase(Ad ad) {

        prepareProgressbar(true);
        DatabaseReference myRef1 = myRef.child(ad.getType()).child(Objects.requireNonNull(ad.getKey()));
        DatabaseReference myRefAll = myRef.child("All").child(Objects.requireNonNull(ad.getKey()));
        DatabaseReference myRefMyAds = myRef.child("MyAds").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(Objects.requireNonNull(ad.getKey()));

        myRefAll.setValue(ad);
        myRefMyAds.setValue(ad);
        myRef1.setValue(ad).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                finish();
            } else {
                prepareProgressbar(false);
                Toast.makeText(AddPostActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadProfileImageToFirebase() throws IOException {
        String key = myRef.push().getKey();
        prepareProgressbar(true);
        if (!isEverythingOk()) {
            prepareProgressbar(false);
            return;
        }
        for (int i = 0; i < uriArrayList.size(); i++) {
            Uri uri = uriArrayList.get(i);
            Bitmap selectedImageBitMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            selectedImageBitMap.compress(Bitmap.CompressFormat.JPEG, 40, outputStream);
            byte[] selectedImageBytes = outputStream.toByteArray();
            StorageReference currentStorageRef = storageRef.child(getUserUID()).child(Objects.requireNonNull(key)).child(UUID.nameUUIDFromBytes(selectedImageBytes).toString());
            int finalI = i;
            currentStorageRef.putBytes(selectedImageBytes).addOnCompleteListener(task -> {
                prepareProgressbar(false);
                if (task.isSuccessful()) {
                    imagePathsInFirebaseStorage.add(currentStorageRef.getPath());
                    if (finalI == uriArrayList.size() - 1 && imagePathsInFirebaseStorage.size() > 0) {

                        Ad ad = new Ad(key,
                                getUserUID(),
                                getPhoneNumber(),
                                getType(),
                                getSaleOrRent(),
                                getNewOrUsed(),
                                getLocation(),
                                getInformation(),
                                getPrice(),
                                getDate(),
                                getTime(),
                                imagePathsInFirebaseStorage);
                        prepareProgressbar(false);
                        addAdToFirebase(ad);
                    }
                } else {
                    Toast.makeText(this, getString(R.string.sorry_something_went_wrong) + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        }


    }

    // post's arguments

    public String getUserUID() {
        String UID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        if (UID.trim().equals("")) return null;
        return UID;
    }

    public String getPhoneNumber() {
        String phone = Objects.requireNonNull(etPhone.getText()).toString();
        if (phone.trim().equals("")) return null;
        return phone;
    }

    public String getType() {

        return translateTypeToEnglish(spinnerType.getText().toString());
    }

    public String getSaleOrRent() {
        MaterialButton mB = findViewById(mBTGSaleRent.getCheckedButtonId());
        String SaleOrRent = Objects.requireNonNull(mB.getText()).toString();
        if (SaleOrRent.trim().equals("")) return null;
        return SaleOrRent;
    }

    public String getNewOrUsed() {
        MaterialButton mB = findViewById(mBTGNewOLd.getCheckedButtonId());
        String NewOrUsed = Objects.requireNonNull(mB.getText()).toString();
        if (NewOrUsed.trim().equals("")) return null;
        return NewOrUsed;
    }

    public String getLocation() {


        String governorate = Objects.requireNonNull(spinnerGovernorate.getText()).toString();
        if (governorate.trim().equals("")) return null;
        return governorate;
    }

    public String getInformation() {
        String information = Objects.requireNonNull(etDetails.getText()).toString();
        if (information.trim().equals("")) return null;
        return information;
    }

    public String getPrice() {
        MaterialButton mB = findViewById(mBDollarOrDinar.getCheckedButtonId());
        String information = Objects.requireNonNull(etPrice.getText())
                + " " + mB.getText().toString();
        if (etPrice.getText().toString().trim().equals("")) return null;
        return information;
    }

    public String getDate() {
        return convertArabicDigitsToEnglish(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
    }

    public String getTime() {
        return convertArabicDigitsToEnglish(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
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

    // Secondary methods

    public String translateTypeToEnglish(String typeInOtherLanguage) {
        String typeInEnglish;

        if (typeInOtherLanguage.equals(getString(R.string.Cars))) typeInEnglish = Keys.CARS;

        else if (typeInOtherLanguage.equals(getString(R.string.Motorcycles)))
            typeInEnglish = Keys.MOTORCYCLE;

        else if (typeInOtherLanguage.equals(getString(R.string.Mobile)))
            typeInEnglish = Keys.MOBILE;

        else if (typeInOtherLanguage.equals(getString(R.string.Laptop_and_Pc)))
            typeInEnglish = Keys.LAPTOP_AND_PC;

        else if (typeInOtherLanguage.equals(getString(R.string.Electronics)))
            typeInEnglish = Keys.ELECTRONICS;

        else if (typeInOtherLanguage.equals(getString(R.string.Realstate)))
            typeInEnglish = Keys.REALSTATE;

        else if (typeInOtherLanguage.equals(getString(R.string.Pets)))
            typeInEnglish = Keys.PETS;

        else if (typeInOtherLanguage.equals(getString(R.string.Home)))
            typeInEnglish = Keys.HOME;

        else if (typeInOtherLanguage.equals(getString(R.string.Fashion)))
            typeInEnglish = Keys.FASHION;

        else if (typeInOtherLanguage.equals(getString(R.string.Sports)))
            typeInEnglish = Keys.FOOD;

        else if (typeInOtherLanguage.equals(getString(R.string.Sports)))
            typeInEnglish = Keys.SPORTS;
        else if (typeInOtherLanguage.equals(""))
            return null;

        else typeInEnglish = Keys.OTHER;

        return typeInEnglish;
    }

    public String convertArabicDigitsToEnglish(String dateInArabic) {
        return dateInArabic.replace('١', '1')
                .replace('٢', '2')
                .replace('٣', '3')
                .replace('٤', '4')
                .replace('٥', '5')
                .replace('٦', '6')
                .replace('٧', '7')
                .replace('٨', '8')
                .replace('٩', '9')
                .replace('٠', '0');
    }

    public boolean isEverythingOk() {
        if (uriArrayList.size() <= 0) {
            Toast.makeText(this, "Please upload one image at least!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (getLocation() == null) {
            spinnerGovernorate.setError("Required");
            spinnerGovernorate.requestFocus();
            return false;
        }
        if (getType() == null) {
            spinnerType.setError("Required");
            spinnerType.requestFocus();
            return false;
        }
        if (getPrice() == null) {
            etPrice.setError("Required");
            etPrice.requestFocus();
            return false;
        }
        if (getPhoneNumber() == null) {
            etPhone.setError("Required");
            etPhone.requestFocus();
            return false;
        }
        if (getUserUID() == null) {
            Toast.makeText(this, "Unfortunately, something went wrong!", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    public void prepareProgressbar(boolean showProgressbar) {
        if (showProgressbar) {
            progressBar.setVisibility(View.VISIBLE);
            addBTN.setText(null);
        } else {
            progressBar.setVisibility(View.GONE);
            addBTN.setText(getString(R.string.publish));


        }
        addBTN.setEnabled(!showProgressbar);

    }
}