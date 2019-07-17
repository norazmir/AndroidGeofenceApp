package com.test.a88petsmartsdnbhd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StaffLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailView, mPasswordView;
    private Button mStaffLoginButton;
    private TextView mRegisterView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        //create instance
        mAuth = FirebaseAuth.getInstance();

        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mStaffLoginButton = findViewById(R.id.login);
        mRegisterView = findViewById(R.id.tvRegister);

        mStaffLoginButton.setOnClickListener(this);
        mRegisterView.setOnClickListener(this);
    }
    protected void onStart()
    {
        super.onStart();
        if(mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(StaffLoginActivity.this, StaffMainActivity.class);
            finish();
            startActivity(intent);
        }
    }

    private void login()
    {
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(StaffLoginActivity.this, StaffMainActivity.class));
                }else{
                    Toast.makeText(StaffLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v == mStaffLoginButton){
            login();
        }else if(v == mRegisterView){
            finish();
            startActivity(new Intent(StaffLoginActivity.this, StaffRegisterActivity.class));
        }
    }
}
