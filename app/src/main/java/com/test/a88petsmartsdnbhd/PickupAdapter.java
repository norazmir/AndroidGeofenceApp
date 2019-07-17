package com.test.a88petsmartsdnbhd;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PickupAdapter extends RecyclerView.Adapter<PickupAdapter.PickupViewHolder> {

    private Context mContext;
    private List<Pickup> pickupList;

    public PickupAdapter(Context mContext, List<Pickup> pickupList) {
        this.mContext = mContext;
        this.pickupList = pickupList;
    }



    @NonNull
    @Override
    public PickupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.pickup_layout, parent, false);

        return new PickupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PickupViewHolder holder, int position) {
        final Pickup pickup = pickupList.get(position);
        holder.pickupTimeView.setText(pickup.getPickupTime());
        holder.pickupLocationView.setText(pickup.getPickupLocation());

        holder.pickupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdatePickupActivity.class);
                intent.putExtra("uid", pickup.getUid());
                intent.putExtra("pickupTime", pickup.getPickupTime());
                intent.putExtra("pickupLocation", pickup.getPickupLocation());
                intent.putExtra("pickupLatitude", pickup.getPickupLatitude());
                intent.putExtra("pickupLongitude", pickup.getPickupLongitude());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pickupList.size();
    }

    class PickupViewHolder extends RecyclerView.ViewHolder{

        TextView pickupTimeView, pickupLocationView, pickupLatitudeView, pickupLongitudeView;
        CardView pickupLayout;

        public PickupViewHolder(View itemView){
            super(itemView);

            pickupTimeView = itemView.findViewById(R.id.pickupTime);
            pickupLocationView= itemView.findViewById(R.id.pickupLocation);
            pickupLatitudeView = itemView.findViewById(R.id.pickupLatitude);
            pickupLongitudeView = itemView.findViewById(R.id.pickupLongitude);
            pickupLayout = itemView.findViewById(R.id.cvPickup);
        }
    }
}
