package com.test.a88petsmartsdnbhd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUserActivity extends AppCompatActivity {


    private EditText mName, mAge, mAddress, mPhoneNum;
    private Button mAddButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //create instance
        databaseUser = FirebaseDatabase.getInstance().getReference("staff");
        mAuth = FirebaseAuth.getInstance();

        //link with xml
        mName = findViewById(R.id.name);
        mAge = findViewById(R.id.age);
        mAddress = findViewById(R.id.address);
        mPhoneNum = findViewById(R.id.phoneNum);
        mAddButton = findViewById(R.id.addUser);

        //attach listener
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });



    }

    private void addUser() {

        //get username and conver to string from edittextname
        String name = mName.getText().toString().trim();

        String age = mAge.getText().toString().trim();
        String address = mAddress.getText().toString().trim();
        String phoneNum = mPhoneNum.getText().toString().trim();


        //check if the name is not empty
        if (!TextUtils.isEmpty(name)) {
            //if exit pust data to firebase database
            //store inside id in database
            //every time data stored the id will be unique
            String id = databaseUser.push().getKey();

            //store
            Staff staff = new Staff(id, name, age, address, phoneNum);
            //store artist inside unique id
            databaseUser.child(id).setValue(staff).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AddUserActivity.this, "Staff added", Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(AddUserActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
