package com.test.a88petsmartsdnbhd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView mNameView, mEmailView, mPhoneNumView, mAddressView;
    private Button mLogout, mUpdate;
    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        context = getActivity().getApplicationContext();
        mNameView = view.findViewById(R.id.name);
        mEmailView = view.findViewById(R.id.email);
        mAddressView = view.findViewById(R.id.address);
        mPhoneNumView = view.findViewById(R.id.phoneNum);
        mLogout = view.findViewById(R.id.logout);
        mUpdate = view.findViewById(R.id.update);

        mLogout.setOnClickListener(this);
        mUpdate.setOnClickListener(this);

        String userId = mAuth.getCurrentUser().getUid();

        mUserRef.child("User").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    User user = dataSnapshot.getValue(User.class);
                    mNameView.setText(user.userName);
                    mEmailView.setText(user.userEmail);
                    mAddressView.setText(user.userAddress);
                    mPhoneNumView.setText(user.userPhoneNum);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == mLogout)
        {
            mAuth.signOut();
            getActivity().finish();
            startActivity(new Intent(getActivity(), HomeActivity.class));
        }
        else if(v == mUpdate){
            String uid = mAuth.getUid();
            String name = mNameView.getText().toString().trim();
            String address = mAddressView.getText().toString().trim();
            String phoneNum = mPhoneNumView.getText().toString().trim();

            Intent intent = new Intent(context, UpdateProfileActivity.class);
            intent.putExtra("uid", uid);
            intent.putExtra("name", name);
            intent.putExtra("address", address);
            intent.putExtra("phoneNum", phoneNum);

            getActivity().finish();
            context.startActivity(intent);
            //startActivity(new Intent(getActivity(), UpdateProfileActivity.class));
        }
    }
}
