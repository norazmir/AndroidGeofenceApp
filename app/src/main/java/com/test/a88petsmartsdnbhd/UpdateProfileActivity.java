package com.test.a88petsmartsdnbhd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText uName, uAddress, uPhoneNum, uEmail;
    private Button updateButton;
    private DatabaseReference uDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_profile);

        //data from fragmentt
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String phoneNum = getIntent().getStringExtra("phoneNum");

        uDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        uName = findViewById(R.id.name);
        uAddress = findViewById(R.id.address);
        uPhoneNum = findViewById(R.id.phoneNum);
        updateButton = findViewById(R.id.update);


        //display current data in update form
        uName.setText(name);
        uAddress.setText(address);
        uPhoneNum.setText(phoneNum);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

    }


    private void updateUser() {

        String id = mAuth.getCurrentUser().getUid();
        String name = uName.getText().toString().trim();
        String email = mAuth.getCurrentUser().getEmail();
        String address = uAddress.getText().toString().trim();
        String phoneNum = uPhoneNum.getText().toString().trim();

        User user = new User(id, name, email, address, phoneNum);

        uDatabase.child("User").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateProfileActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(UpdateProfileActivity.this, MainActivity.class));
                }
            }
        });



    }
    /*
    private void deleteStaff() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("uid");
        uDatabase.child("staff").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateProfileActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(UpdateProfileActivity.this, MainActivity.class));
                }
            }
        });
    }
    */

}
