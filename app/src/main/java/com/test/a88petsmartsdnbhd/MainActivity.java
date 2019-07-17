package com.test.a88petsmartsdnbhd;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView textEmail, textName;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseUser, nameDatabase;
    private String activity ="value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Create database reference
        mAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference();
        //get values from XML

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        textEmail = findViewById(R.id.email);
        textName = findViewById(R.id.name);


        NavigationView navigationView = findViewById(R.id.nav_view);
        String email = mAuth.getCurrentUser().getEmail();
        textEmail = navigationView.getHeaderView(0).findViewById(R.id.email);
        textEmail.setText(email);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_profile);
        }

        Bundle extra = getIntent().getExtras();
        if(extra!=null){
            activity = extra.getString("id");
        }

        if (activity.equals("pet")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new PetFragment(), "FRAG_PET")
                    .commit();
            navigationView.setCheckedItem(R.id.nav_pet);
        }
        else if (activity.equals("pickup")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ReservePickupFragment(), "FRAG_RESERVE_PICKUP")
                    .commit();
            navigationView.setCheckedItem(R.id.nav_reserve_pickup);
        }
        else if (activity.equals("service")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ReserveServiceFragment(), "FRAG_RESERVE_SERVICEC")
                    .commit();
            navigationView.setCheckedItem(R.id.nav_reserve_service);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {

            case R.id.nav_profile:
                selectedFragment = new ProfileFragment();
                break;

            case R.id.nav_reserve_service:
                selectedFragment = new ReserveServiceFragment();
                break;
            case R.id.nav_reserve_pickup:
                selectedFragment = new ReservePickupFragment();
                break;
            case R.id.nav_pet:
                selectedFragment = new PetFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            /*

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ProfileFragment(), "FRAG_PROFILE")
                        .commit();
                Toast.makeText(this, "1 successful", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_reserve_service:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ReserveServiceFragment(), "FRAG_RESERVE_SERVICE")
                        .commit();
                Toast.makeText(this, "3 successful", Toast.LENGTH_SHORT).show();
                break;




            case R.id.nav_reserve_pickup:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ReservePickupFragment(), "FRAG_RESERVE_PICKUP")
                        .commit();
                Toast.makeText(this, "4 successful", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_map:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MapFragment(), "FRAG_MAP")
                        .commit();
                Toast.makeText(this, "5 successful", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_pet:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new PetFragment(), "FRAG_PET")
                        .commit();
                Toast.makeText(this,"2 successful", Toast.LENGTH_SHORT).show();
                break;

                */

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
