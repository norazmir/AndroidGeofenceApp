package com.test.a88petsmartsdnbhd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class UpdatePetActivity extends AppCompatActivity {

    private EditText uPetName, uPetAge;
    private Button buttonUpdate, buttonDelete, buttonCancel;
    private Spinner uPetType;
    private DatabaseReference uDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_pet);

        //data from fragment
        Intent intent = getIntent();
        String petName = intent.getStringExtra("petName");
        String petType = intent.getStringExtra("petType");
        String petAge = intent.getStringExtra("petAge");

        uDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        uPetName = findViewById(R.id.petName);
        uPetType = findViewById(R.id.petType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uPetType.setAdapter(adapter);
        uPetAge = findViewById(R.id.petAge);
        buttonUpdate = findViewById(R.id.update);
        buttonDelete = findViewById(R.id.delete);
        buttonCancel = findViewById(R.id.buttonCancel);

        //display sepatutnyaa
        uPetName.setText(petName);
        if (petType != null)
        {
            int spinnerPosition2 = adapter.getPosition(petType);
            uPetType.setSelection(spinnerPosition2);
        }

        uPetAge.setText(petAge);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePet();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePet();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        finish();
                        Intent intent = new Intent(UpdatePetActivity.this, MainActivity.class);
                        intent.putExtra("id", "pet");
                        startActivity(intent);

            }
        });

    }


    private void updatePet() {

        Intent intent = getIntent();
        String userUid = mAuth.getCurrentUser().getUid();
        String uid = intent.getStringExtra("uid");
        String petName = uPetName.getText().toString().trim();
        String petType = uPetType.getSelectedItem().toString();
        String petAge = uPetAge.getText().toString().trim();
        String userUID = mAuth.getCurrentUser().getUid();

        Pet pet = new Pet(uid, petName, petType, petAge, userUID);
        uDatabase.child("Pet").child(userUid).child(uid).setValue(pet).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdatePetActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(UpdatePetActivity.this, MainActivity.class);
                    intent.putExtra("id","pet");
                    startActivity(intent);
                }
            }
        });



    }

    private void deletePet() {
        Intent intent = getIntent();
        String userUid = mAuth.getCurrentUser().getUid();
        String uid = intent.getStringExtra("uid");
        uDatabase.child("Pet").child(userUid).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdatePetActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(UpdatePetActivity.this, MainActivity.class);
                    intent.putExtra("id","pet");
                    startActivity(intent);
                }
            }
        });
    }
}
