package com.test.a88petsmartsdnbhd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GeofencePickupActivity extends FragmentActivity implements OnMapReadyCallback,
GoogleMap.OnInfoWindowClickListener{

    private GoogleMap mMap;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofence_pickup);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mdatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //set listener to each marker
        mMap.setOnInfoWindowClickListener(this);
        //load marker from db to maps
        getMarkerFromDb();

        //default map position
        LatLng defaultPosition = new LatLng(2.273340, 102.445206);

        //move camera to selected position on maps
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, 17.0f));
    }

    private void getMarkerFromDb() {
        mdatabase.child("Pickup").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Pickup pickup = ds.getValue(Pickup.class);
                    String uid = pickup.uid;
                    String pickupTime = pickup.pickupTime;
                    Double latitude = Double.valueOf(pickup.pickupLatitude);
                    Double longitude = Double.valueOf(pickup.pickupLongitude);
                    String location = pickup.pickupLocation;

                    addMarkersTomap(uid, pickupTime, latitude, longitude, location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addMarkersTomap(String uid, String pickupTime, Double latitude, Double longitude, String location) {
        LatLng latLng = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(latLng)
                    .title(pickupTime)
                    .snippet(location))
                    .setTag(uid);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(GeofencePickupActivity.this, ReservePickupFragment.class);
        intent.putExtra("uid", marker.getTag().toString());
        startActivity(intent);
    }
}
