package com.test.a88petsmartsdnbhd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity {

    private EditText uName, uAddress, uPhoneNum, uEmail;
    private Button updateButton;
    private DatabaseReference uDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

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
                updateStaff();
            }
        });

    }


    private void updateStaff() {

        String uid = mAuth.getCurrentUser().getUid();
        String name = uName.getText().toString().trim();
        String email = mAuth.getCurrentUser().getEmail();
        String address = uAddress.getText().toString().trim();
        String phoneNum = uPhoneNum.getText().toString().trim();

        Staff staff = new Staff(uid, name, email, address, phoneNum);

        uDatabase.child("User").child(uid).setValue(staff).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(UpdateActivity.this, StaffMainActivity.class));
                }
            }
        });



    }

}
