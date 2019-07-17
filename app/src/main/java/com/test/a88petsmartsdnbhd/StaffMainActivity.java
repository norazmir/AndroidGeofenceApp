package com.test.a88petsmartsdnbhd;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StaffMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView textEmail, textName;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseUser;
    private String activity ="staffValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);


        //Create database reference
        mAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference();

        //get values from XML

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        textEmail = findViewById(R.id.email);
       // textName = findViewById(R.id.name);



        NavigationView navigationView = findViewById(R.id.nav_view);
        String email = mAuth.getCurrentUser().getEmail();
        textEmail = navigationView.getHeaderView(0).findViewById(R.id.email);
        textEmail.setText(email);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StaffProfileFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_profile);
        }

        Bundle extra = getIntent().getExtras();
        if(extra!=null){
            activity = extra.getString("uid");
        }

        else if (activity.equals("staffPickup")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new StaffReservePickupFragment(), "FRAG_RESERVE_PICKUP")
                    .commit();
            navigationView.setCheckedItem(R.id.nav_reserve_pickup);
        }
        else if (activity.equals("staffService")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new StaffReserveServiceFragment(), "FRAG_RESERVE_SERVICE")
                    .commit();
            navigationView.setCheckedItem(R.id.nav_reserve_service);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {

            case R.id.nav_profile:
                selectedFragment = new StaffProfileFragment();
                break;

            case R.id.nav_reserve_service:
                selectedFragment = new StaffReserveServiceFragment();
                break;
            case R.id.nav_reserve_pickup:
                selectedFragment = new StaffReservePickupFragment();
                break;
            case R.id.nav_map:
                selectedFragment = new MapFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
