package com.test.a88petsmartsdnbhd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

public class ReservePickupFragment extends Fragment {

    private TextView mPickupTimeView, mPickupLocationView;
    private Button mbuttonAdd;
    private DatabaseReference sDatabase;
    private FirebaseAuth mAuth;

    private List<Pickup> mPickupList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reserve_pickup, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create instance/initializes
        sDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        sDatabase.keepSynced(true);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //link with xml
        mPickupTimeView = view.findViewById(R.id.pickupTime);
        mPickupLocationView = view.findViewById(R.id.pickupLocation);

        mbuttonAdd = view.findViewById(R.id.buttonAdd);
        mRecyclerView = view.findViewById(R.id.rvPickup);

        //attach listener
        mbuttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(new Intent(getActivity(), AddPickupActivity.class));
            }
        });

        sDatabase.child("Pickup").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPickupList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Pickup pickup = postSnapshot.getValue(Pickup.class);
                    mPickupList.add(pickup);
                }
                //creating adapter
                mAdapter = new PickupAdapter(getActivity(), mPickupList);

                //adding adapter to recyclerview
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRecyclerView.setHasFixedSize(true); //set fixed size for element inn recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
