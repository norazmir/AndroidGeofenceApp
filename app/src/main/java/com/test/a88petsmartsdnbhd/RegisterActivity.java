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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText mEmailView, mPasswordView, mNameView, mAdress, mPhoneNum, mUserType;
    private Button mRegisterButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseUser, jLoginDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference();
        mAdress = findViewById(R.id.etAddress);
        mPhoneNum = findViewById(R.id.etPhoneNum);
        mEmailView = findViewById(R.id.etEmail);
        mPasswordView = findViewById(R.id.etPassword);
        mNameView = findViewById(R.id.etName);


        mRegisterButton = findViewById(R.id.btnRegister);

        mRegisterButton.setOnClickListener(this);



    }


    private void createUser() {
        String userId = mAuth.getCurrentUser().getUid();
        String userEmail = mEmailView.getText().toString().trim();
        String userName = mNameView.getText().toString().trim();
        String userAddress = mAdress.getText().toString().trim();
        String userPhoneNum = mPhoneNum.getText().toString().trim();

        User user = new User(userId,userName, userEmail, userAddress, userPhoneNum);
        databaseUser.child("User").child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register()
    {
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        String name = mNameView.getText().toString().trim();
        String address = mAdress.getText().toString().trim();
        String phoneNum = mPhoneNum.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneNum)) {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    createUser();
                    finish();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(RegisterActivity.this,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == mRegisterButton)
        {
            register();
        }
    }
}
