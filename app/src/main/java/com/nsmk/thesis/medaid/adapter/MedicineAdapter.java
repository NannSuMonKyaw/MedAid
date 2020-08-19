package com.nsmk.thesis.medaid.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.model.Medicine;
import com.nsmk.thesis.medaid.model.Symptom;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MyViewHolder> {


    private Context mcontext;
    public static List<Medicine> medicineList;

    public MedicineAdapter(Context mcontext, List<Medicine> medicineList) {
        this.mcontext = mcontext;
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_list_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.bindView(medicine);
    }



    @Override
    public int getItemCount() {
        return medicineList.size();
    }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_medicine)
            ImageView imageView;

            @BindView(R.id.medicineName)
            TextView tvMedicineName;

            @BindView(R.id.medicineDosage)
            TextView tvMedicineDosage;

            @BindView(R.id.medicineType)
            TextView tvMedicineType;

            @BindView(R.id.medicineFrequency)
            TextView tvMedicineFrequency;

            @BindView(R.id.medicineConsecutiveDays)
            TextView tvMedicineConsecutiveDays;
            private Context context;
            private Medicine medicine;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                context = itemView.getContext();
                ButterKnife.bind(this, itemView);
            }


            public void bindView(Medicine medicine) {
                this.medicine=medicine;
                //set medicine Name
                tvMedicineName.setText(medicine.getMedicineName());

                //set medicine dosage
                if(medicine.getNoOfDosage()==0){
                    tvMedicineDosage.setText("");
                }else {
                    tvMedicineDosage.setText(Integer.toString(medicine.getNoOfDosage()));
                }

                //set medicine type
                String medicineType=medicine.getMedicineType();
                tvMedicineType.setText(medicineType);
                if(medicineType.equals("tepidSponging")){
                    tvMedicineType.setText("");
                    Glide.with(context)
                            .load(R.drawable.sponge)
                            .into(imageView);
                }
                else if(medicineType.equals("spoon")){
                    Glide.with(context)
                            .load(R.drawable.spoon)
                            .into(imageView);
                }
                else if(medicineType.equals("diet")){
                    Glide.with(context)
                            .load(R.drawable.food)
                            .into(imageView);
                }
                else if(medicineType.equals("tablet")){
                    Glide.with(context)
                            .load(R.drawable.pill)
                            .into(imageView);
                }
                else if(medicineType.equals("litre")){
                    Glide.with(context)
                            .load(R.drawable.water_bottle)
                            .into(imageView);
                }
                else if(medicineType.equals("puff")){
                    Glide.with(context)
                            .load(R.drawable.inhaler)
                            .into(imageView);
                }
                else if(medicineType.equals("ointment")){
                    Glide.with(context)
                            .load(R.drawable.ointment)
                            .into(imageView);
                }

                //set medicine frequency
                String frequency=medicine.getFrequency();
                if(frequency.equals("am")){
                    tvMedicineFrequency.setText(R.string.am);
                }
                else if(frequency.equals("od"))
                {
                    tvMedicineFrequency.setText(R.string.od);
                }
                else if(frequency.equals("bd")){
                    tvMedicineFrequency.setText(R.string.bd);
                }
                else if(frequency.equals("tds")){
                    tvMedicineFrequency.setText(R.string.tds);
                }
                else if(frequency.equals("hs")){
                    tvMedicineFrequency.setText(R.string.hs);
                }
                else if(frequency.equals("adlib")){
                    tvMedicineFrequency.setText(R.string.adlib);
                }

                //set consecutive days to take medicine
                String stringConsectiveDays=" x "+medicine.getConsecutiveDays()+" days";
                tvMedicineConsecutiveDays.setText(stringConsectiveDays);


            }
        }
}

