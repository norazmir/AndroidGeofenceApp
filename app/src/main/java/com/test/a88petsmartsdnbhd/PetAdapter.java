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

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private Context mContext;
    private List<Pet> petList;

    public PetAdapter(Context mContext, List<Pet> petList) {
        this.mContext = mContext;
        this.petList =  petList;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.pet_layout, parent, false);

        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetAdapter.PetViewHolder holder, int position) {
        final Pet pet = petList.get(position);
        holder.petNameView.setText(pet.getPetName());
        holder.petTypeView.setText(pet.getPetType());
        holder.petAgeView.setText(pet.getPetAge());

        holder.petLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdatePetActivity.class);
                intent.putExtra("uid", pet.getUid());
                intent.putExtra("petName", pet.getPetName());
                intent.putExtra("petType", pet.getPetType());
                intent.putExtra("petAge", pet.getPetAge());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    class PetViewHolder extends RecyclerView.ViewHolder{
        TextView petNameView, petTypeView, petAgeView;
        CardView petLayout;

        public PetViewHolder(View itemView){
            super(itemView);

            petNameView = itemView.findViewById(R.id.petName);
            petTypeView = itemView.findViewById(R.id.petType);
            petAgeView = itemView.findViewById(R.id.petAge);
            petLayout = itemView.findViewById(R.id.cvPet);
        }
    }
}
