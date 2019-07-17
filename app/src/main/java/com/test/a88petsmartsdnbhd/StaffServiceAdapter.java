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

public class StaffServiceAdapter extends RecyclerView.Adapter<StaffServiceAdapter.ServiceViewHolder> {

    private Context mContext;
    private List<Service> serviceList;

    public StaffServiceAdapter(Context mContext, List<Service> serviceList) {
        this.mContext = mContext;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.staff_service_layout, parent, false);

        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        final Service service = serviceList.get(position);
        holder.serviceNameView.setText(service.getServiceName());
        holder.serviceDateView.setText(service.getServiceDate());
        holder.serviceStatusView.setText(service.getServiceStatus());

        holder.serviceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StaffUpdateServiceActivity.class);
                intent.putExtra("uid", service.getUid());
                intent.putExtra("serviceName", service.getServiceName());
                intent.putExtra("serviceDate", service.getServiceDate());
                intent.putExtra("serviceStatus", service.getServiceStatus());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder{
        TextView serviceNameView, serviceDateView, serviceStatusView;
        CardView serviceLayout;

        public ServiceViewHolder(View itemView){
            super(itemView);

            serviceNameView = itemView.findViewById(R.id.serviceName);
            serviceDateView = itemView.findViewById(R.id.serviceDate);
            serviceStatusView = itemView.findViewById(R.id.serviceStatus);
            serviceLayout = itemView.findViewById(R.id.cvService);
        }
    }
}
