package com.test.a88petsmartsdnbhd;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class AddServiceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText mServiceDate, mServiceStatus;
    private Spinner spinnerStatus, spinnerService;
    private Button mAddButton, buttonSelectDate, buttonCancel;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);


        //create instance
        databaseUser = FirebaseDatabase.getInstance().getReference("Service");
        mAuth = FirebaseAuth.getInstance();

        //link with xml
        spinnerService = findViewById(R.id.serviceName);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.service, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerService.setAdapter(adapter2);
        mAddButton = findViewById(R.id.buttonAdd);
        buttonSelectDate = findViewById(R.id.buttonCalendar);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
        buttonCancel = findViewById(R.id.buttonCancel);

        //attach listener
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new AddCalendarDialog();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String text = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(AddServiceActivity.this, MainActivity.class);
                intent.putExtra("id", "service");
                startActivity(intent);
            }
        });

    }

    private void addUser() {

        //get username and conver to string from edittextname
        String serviceName = spinnerService.getSelectedItem().toString();
        String userUID = mAuth.getCurrentUser().getUid();
        String serviceDate = mServiceDate.getText().toString().trim();
        String serviceStatus = spinnerStatus.getSelectedItem().toString();


        //check if the name is not empty
        if (!TextUtils.isEmpty(serviceName)) {
            //if exit pust data to firebase database
            //store inside id in database
            //every time data stored the id will be unique
            String uid = databaseUser.child("posts").push().getKey();
            //store
            Service service = new Service(uid, serviceName, serviceStatus, serviceDate, userUID);
            //store artist inside unique id
            databaseUser.child(userUID).child(uid).setValue(service).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AddServiceActivity.this, "Service Booked", Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent(AddServiceActivity.this, MainActivity.class);
                        intent.putExtra("id","service");
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(AddServiceActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            //if name is empty
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        mServiceDate = findViewById(R.id.serviceDate);
        mServiceDate.setText(currentDateString);
    }
}
