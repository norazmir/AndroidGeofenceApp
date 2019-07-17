package com.test.a88petsmartsdnbhd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateServiceActivity extends AppCompatActivity {

    private EditText  uServiceDate;
    private Spinner uServiceStatus, uServiceName;
    private Button buttonUpdate, buttonDelete;
    private DatabaseReference uDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_service);

        //data from fragment
        Intent intent = getIntent();
        String serviceName = intent.getStringExtra("serviceName");
        String serviceDate = intent.getStringExtra("serviceDate");
        String serviceStatus = intent.getStringExtra("serviceStatus");

        uDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        uServiceName = findViewById(R.id.serviceName);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.service, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uServiceName.setAdapter(adapter2);
        uServiceDate = findViewById(R.id.serviceDate);
        uServiceStatus = findViewById(R.id.spinnerStatus);
        buttonUpdate = findViewById(R.id.update);
        buttonDelete = findViewById(R.id.delete);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uServiceStatus.setAdapter(adapter);


        //display sepatutnyaa
        if (serviceName != null)
        {
            int spinnerPosition2 = adapter2.getPosition(serviceName);
            uServiceName.setSelection(spinnerPosition2);
        }
        uServiceDate.setText(serviceDate);
        if (serviceStatus != null)
        {
            int spinnerPosition = adapter.getPosition(serviceStatus);
            uServiceStatus.setSelection(spinnerPosition);
        }

        //listener attached
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateService();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteService();
            }
        });
        uServiceStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void updateService() {

        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        String serviceName = uServiceName.getSelectedItem().toString();
        String serviceDate = uServiceDate.getText().toString().trim();
        String serviceStatus = uServiceStatus.getSelectedItem().toString();
        String userUid = mAuth.getCurrentUser().getUid();

        Service service = new Service(uid, serviceName, serviceStatus, serviceDate, userUid);
        uDatabase.child("Service").child(userUid).child(uid).setValue(service).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateServiceActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent  = new Intent(UpdateServiceActivity.this, MainActivity.class);
                    intent.putExtra("id","service");
                    startActivity(intent);
                }
            }
        });

    }

    private void deleteService() {

        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        String userUid = mAuth.getCurrentUser().getUid();
        uDatabase.child("Service").child(userUid).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateServiceActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent  = new Intent(UpdateServiceActivity.this, MainActivity.class);
                    intent.putExtra("id","service");
                    startActivity(intent);
                }
            }
        });
    }
}
