package com.test.a88petsmartsdnbhd;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPickupActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "AddPickupActivity";
    private EditText mPickupTime, mPickupLocation, mPickupLatitude, mPickupLongitude;
    private Button mAddButton, mSelectLocationButton, mSelectTimeButton, mButtonCancel;
    private FirebaseAuth mAuth;
    private DatabaseReference databasePickup;
    private static final int LOC_REQ_CODE = 1;
    private static final int PLACE_PICKER_REQ_CODE = 2;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pickup);

        //create instance
        databasePickup = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //link with xml
        mPickupTime = findViewById(R.id.pickupTime);
        mPickupLocation = findViewById(R.id.pickupLocation);
        mPickupLatitude = findViewById(R.id.pickupLatitude);
        mPickupLongitude = findViewById(R.id.pickupLongitude);

        mSelectLocationButton = findViewById(R.id.btnSelectLocation);
        mSelectTimeButton = findViewById(R.id.buttonTime);
        mAddButton = findViewById(R.id.buttonBook);
        mButtonCancel = findViewById(R.id.buttonCancel);

        //attach listener
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPickup();
            }
        });
        mSelectLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentPlaceItems();
            }
        });
        mSelectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new AddTimeDialog();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(AddPickupActivity.this, MainActivity.class);
                intent.putExtra("id", "pickup");
                startActivity(intent);
            }
        });

    }


    private void getCurrentPlaceItems() {
        if (isLocationAccessPermitted()){
            showPlacePicker();
        }else {
            requestLocationAccessPermission();
        }
    }

    private void requestLocationAccessPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOC_REQ_CODE);
    }

    private boolean isLocationAccessPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            return false;
        }else {
            return true;
        }
    }

    private void showPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try{
            startActivityForResult(builder.build(this), PLACE_PICKER_REQ_CODE);
        }catch (Exception e){
            Log.e(TAG, e.getStackTrace().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOC_REQ_CODE){
            if (resultCode == RESULT_OK){
                showPlacePicker();
            }
        }else if (requestCode == PLACE_PICKER_REQ_CODE){
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(this, data);
                LatLng latLng = place.getLatLng();
                String pickupLatitude = String.valueOf(latLng.latitude);
                String pickupLongitude = String.valueOf(latLng.longitude);
                String pickupLocation = place.getAddress().toString();

                mPickupLatitude.setText(pickupLatitude);
                mPickupLongitude.setText(pickupLongitude);
                mPickupLocation.setText(pickupLocation);
            }
        }
    }

    private void addPickup() {

        //get username and conver to string from edittextname
        String pickupTime = mPickupTime.getText().toString().trim();
        String pickupLocation = mPickupLocation.getText().toString().trim();
        String pickupLatitude = mPickupLatitude.getText().toString().trim();
        String pickupLongitude = mPickupLongitude.getText().toString().trim();
        String uid = databasePickup.child("posts").push().getKey();
        String userUid = mAuth.getCurrentUser().getUid();

        //check if the name is not empty
        if (!TextUtils.isEmpty(pickupTime)) {
            //if exit pust data to firebase database
            //store inside id in database
            //every time data stored the id will be unique
            //store
            Pickup pickup= new Pickup(uid, pickupTime, pickupLatitude, pickupLongitude, pickupLocation, userUid);
            //store artist inside unique id
            databasePickup.child("Pickup").child(userUid).child(uid).setValue(pickup).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AddPickupActivity.this, "Pickup Booked", Toast.LENGTH_LONG).show();
                        finish();

                      // FragmentManager fm = getSupportFragmentManager();
                        //Fragment fragment = new ReservePickupFragment();
                      //  fm.beginTransaction().add(R.id.nav_reserve_pickup, fragment).commit();
                        Intent intent = new Intent(AddPickupActivity.this, MainActivity.class);
                        intent.putExtra("id","pickup");
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(AddPickupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            //if name is empty
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mPickupTime.setText(String.format("%02d:%02d", hourOfDay ,minute));
    }
}
