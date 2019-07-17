package com.test.a88petsmartsdnbhd;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

public class UpdatePickupActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "UpdatePickupActivity";
    private EditText uPickupTime, uPickupLocation, uPickupLatitude, uPickupLongitude;
    private Button buttonUpdate, buttonDelete, uSelectButton, uSelectTimeButton;
    private DatabaseReference uDatabase;
    private FirebaseAuth mAuth;
    private static final int LOC_REQ_CODE = 1;
    private static final int PLACE_PICKER_REQ_CODE = 2;
    private Place place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_pickup);

        //data from fragment
        Intent intent = getIntent();
        String pickupTime = intent.getStringExtra("pickupTime");
        String pickupLocation = intent.getStringExtra("pickupLocation");
        String pickupLatitude = intent.getStringExtra("pickupLatitude");
        String pickupLongitude = intent.getStringExtra("pickupLongitude");


        uDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        uPickupTime = findViewById(R.id.pickupTime);
        uPickupLocation = findViewById(R.id.pickupLocation);
        uPickupLatitude = findViewById(R.id.pickupLatitude);
        uPickupLongitude = findViewById(R.id.pickupLongitude);


        buttonUpdate = findViewById(R.id.update);
        buttonDelete = findViewById(R.id.delete);
        uSelectButton = findViewById(R.id.btnSelectLocation);
        uSelectTimeButton = findViewById(R.id.buttonTime);

        //display sepatutnyaa
        uPickupTime.setText(pickupTime);
        uPickupLocation.setText(pickupLocation);
        uSelectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new AddTimeDialog();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePickup();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePickup();
            }
        });
        uSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentPlaceItems();
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

    private void showPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try{
            startActivityForResult(builder.build(this), PLACE_PICKER_REQ_CODE);
        }catch (Exception e){
            Log.e(TAG, e.getStackTrace().toString());
        }
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

    private void requestLocationAccessPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOC_REQ_CODE);
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

                uPickupLatitude.setText(pickupLatitude);
                uPickupLongitude.setText(pickupLongitude);
                uPickupLocation.setText(pickupLocation);
            }
        }
    }


    private void updatePickup() {

        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        String pickupTime = uPickupTime.getText().toString().trim();
        String pickupLocation = uPickupLocation.getText().toString().trim();
        String pickupLatitude = uPickupLatitude.getText().toString().trim();
        String pickupLongitude = uPickupLongitude.getText().toString().trim();
        String userUid = mAuth.getCurrentUser().getUid();

        Pickup pickup = new Pickup(uid, pickupTime, pickupLatitude, pickupLongitude, pickupLocation, userUid);
        uDatabase.child("Pickup").child(userUid).child(uid).setValue(pickup).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdatePickupActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(UpdatePickupActivity.this, MainActivity.class);
                    intent.putExtra("id","pickup");
                    startActivity(intent);
                }
            }
        });

    }

    private void deletePickup() {
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        String userUid = mAuth.getCurrentUser().getUid();
        uDatabase.child("Pickup").child(userUid).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdatePickupActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(UpdatePickupActivity.this, MainActivity.class);
                    intent.putExtra("id","pickup");
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        uPickupTime.setText(hourOfDay + ":" + minute);
    }
}
