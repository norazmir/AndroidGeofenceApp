package com.test.a88petsmartsdnbhd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPetActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private EditText mPetName, mPetAge;
    private Spinner mPetType;
    private Button mAddButton, mButtonCancel;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);



        //create instance
        databaseUser = FirebaseDatabase.getInstance().getReference("Pet");
        mAuth = FirebaseAuth.getInstance();

        //link with xml
        mPetName = findViewById(R.id.petName);
        mPetType = findViewById(R.id.petType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPetType.setAdapter(adapter);
        mPetAge = findViewById(R.id.petAge);
        mAddButton = findViewById(R.id.addUser);
        mButtonCancel = findViewById(R.id.buttonCancel);

        //attach listener
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(AddPetActivity.this, MainActivity.class);
                intent.putExtra("id", "pet");
                startActivity(intent);
            }
        });
    }


    private void addUser() {

        //get username and conver to string from edittextname
        String petName = mPetName.getText().toString().trim();
        String userUid = mAuth.getCurrentUser().getUid();
        String petType = mPetType.getSelectedItem().toString();
        String petAge = mPetAge.getText().toString().trim();
        //every time data stored the id will be unique
        String uid = databaseUser.child("posts").push().getKey();
        String userUID = mAuth.getCurrentUser().getUid();

        //check if the name is not empty
        if (!TextUtils.isEmpty(petName)) {
            //if exit pust data to firebase database
            //store inside id in database

            //store
            Pet pet = new Pet(uid, petName, petType, petAge, userUID);
            //store artist inside unique id
            databaseUser.child(userUid).child(uid).setValue(pet).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AddPetActivity.this, "Pet Added", Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent(AddPetActivity.this, MainActivity.class);
                        intent.putExtra("id","pet");
                        startActivity(intent);
                        //FragmentManager fm = getSupportFragmentManager();
                      //  Fragment fragment = new PetFragment();
                        //fm.beginTransaction().add(R.id.nav_view, fragment).commit();
                    }
                    else {
                        Toast.makeText(AddPetActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            //if name is empty
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();

        }
    }
}
