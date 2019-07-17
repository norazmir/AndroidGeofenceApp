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

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {

    private Context mContext;
    private List<Staff> staffList;

    public StaffAdapter(Context mContext, List<Staff> staffList) {
        this.mContext = mContext;
        this.staffList = staffList;
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_list_user, parent,false);
        return new StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StaffViewHolder holder, final int position) {
        final Staff staff = staffList.get(position);
        holder.nameView.setText(staff.getUserName());
        holder.ageView.setText(staff.getAge());
        holder.addressView.setText(staff.getAddress());
        holder.phoneNumView.setText(staff.getPhoneNum());

        holder.staffLayout.setOnClickListener(new View.OnClickListener(){
                                                  @Override
                                                  public void onClick(View v) {
                                                      Intent intent = new Intent(mContext, UpdateActivity.class);
                                                      intent.putExtra("uid", staff.getUserId());
                                                      intent.putExtra("name",staff.getUserName());
                                                      intent.putExtra("age",staff.getAge());
                                                      intent.putExtra("address",staff.getAddress());
                                                      intent.putExtra("phoneNum",staff.getPhoneNum());
                                                      mContext.startActivity(intent);
                                                  }
                                              });
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    class StaffViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, ageView, addressView, phoneNumView;
        CardView staffLayout;

         public StaffViewHolder(View itemView)
         {
             super(itemView);
             nameView = itemView.findViewById(R.id.tvName);
             ageView = itemView.findViewById(R.id.tvAge);
             addressView = itemView.findViewById(R.id.tvAddress);
             phoneNumView = itemView.findViewById(R.id.tvPhoneNum);
             staffLayout = itemView.findViewById(R.id.cvStaff);
         }
    }
}
