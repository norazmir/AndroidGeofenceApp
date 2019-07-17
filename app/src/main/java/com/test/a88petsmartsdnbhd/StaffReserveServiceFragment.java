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

public class StaffReserveServiceFragment extends Fragment {

    private TextView mServiceNameView, mServiceDateView, mServiceStatusView;
    private Button mbuttonAdd;
    private DatabaseReference sDatabase, reserveService, service;
    private FirebaseAuth mAuth;

    private List<Service> mServiceList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.staff_fragment_reserve_service, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create instance/initializes
        sDatabase = FirebaseDatabase.getInstance().getReference("Service");
        mAuth = FirebaseAuth.getInstance();
        sDatabase.keepSynced(true);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //link with xml
        mServiceNameView = view.findViewById(R.id.serviceName);
        mServiceDateView = view.findViewById(R.id.serviceDate);
        mServiceStatusView = view.findViewById(R.id.serviceStatus);
       // mbuttonAdd = view.findViewById(R.id.buttonAdd);
        mRecyclerView = view.findViewById(R.id.rvService);

        //attach listener

        sDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mServiceList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot pSnapshot : postSnapshot.getChildren()){
                        Service service = pSnapshot.getValue(Service.class);
                        //System.out.println("Jalan " + service.uid);
                        mServiceList.add(service);
                    }

                }
                mAdapter = new StaffServiceAdapter(getActivity(), mServiceList);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        mRecyclerView.setHasFixedSize(true); //set fixed size for element inn recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*
        sDatabase.child("Service").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mServiceList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = postSnapshot.getValue(Service.class);
                    mServiceList.add(service);
                }
                //creating adapter
                mAdapter = new ServiceAdapter(getActivity(), mServiceList);

                //adding adapter to recyclerview
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
      //  mRecyclerView.setHasFixedSize(true); //set fixed size for element inn recycler view
       // mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
