package com.nsmk.thesis.medaid.adapter;

import android.content.Context;
import android.text.TextDirectionHeuristic;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.model.Medicine;
import com.nsmk.thesis.medaid.model.MedicineAlarm;

import java.util.List;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicineAlarmAdapter extends RecyclerView.Adapter<MedicineAlarmAdapter.MyViewHolder> {

    private Context mcontext;
    public static List<MedicineAlarm> medicineAlarmList;

    public MedicineAlarmAdapter(Context mcontext, List<MedicineAlarm> medicineAlarmList) {
        this.mcontext = mcontext;
        this.medicineAlarmList = medicineAlarmList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_alarm_list_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MedicineAlarm medicineAlarm = medicineAlarmList.get(position);
        holder.bindView(medicineAlarm);
    }



    @Override
    public int getItemCount() {
        return medicineAlarmList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imv_medicineType)
        ImageView img_medicine;

        @BindView(R.id.tv_time)
        TextView tvTime;

        @BindView(R.id.tv_medicine_name)
        TextView tvMedicineName;

        @BindView(R.id.tv_dose)
        TextView tvDose;

        @BindView(R.id.tv_type)
        TextView tvType;

        @BindView(R.id.item_alarm_started)
        Switch alarmStarted;

        private Context context;
        private MedicineAlarm medicineAlarm;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }


        public void bindView(MedicineAlarm medicineAlarm) {
            this.medicineAlarm=medicineAlarm;

            String alarmTime=change12HourFormat(medicineAlarm.getHour(),medicineAlarm.getMinute());
            tvTime.setText(alarmTime);

            alarmStarted.setChecked(medicineAlarm.isStarted());

            tvMedicineName.setText(medicineAlarm.getTitle());

            tvDose.setText(Float.toString(medicineAlarm.getDose()));



            //set medicine type
            String medicineType=medicineAlarm.getType();
            tvType.setText(medicineType);
            if(medicineType.equals("tepidSponging")){
                tvType.setText("");
                Glide.with(context)
                        .load(R.drawable.sponge)
                        .into(img_medicine);
            }
            else if(medicineType.equals("spoon")){
                Glide.with(context)
                        .load(R.drawable.spoon)
                        .into(img_medicine);
            }
            else if(medicineType.equals("diet")){
                Glide.with(context)
                        .load(R.drawable.food)
                        .into(img_medicine);
            }
            else if(medicineType.equals("tablet")){
                Glide.with(context)
                        .load(R.drawable.pill)
                        .into(img_medicine);
            }
            else if(medicineType.equals("litre")){
                Glide.with(context)
                        .load(R.drawable.water_bottle)
                        .into(img_medicine);
            }
            else if(medicineType.equals("puff")){
                Glide.with(context)
                        .load(R.drawable.inhaler)
                        .into(img_medicine);
            }
            else if(medicineType.equals("ointment")){
                Glide.with(context)
                        .load(R.drawable.ointment)
                        .into(img_medicine);
            }

            alarmStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i("alarm","switch is "+ isChecked);
                }
            });



        }
    }

    public String change12HourFormat(int hourOfDay, int minute){
        String result="";
        if(hourOfDay<=12){
            result=hourOfDay+":"+(minute < 10 ? ("0" + minute) : minute)+ " " + ((hourOfDay >= 12) ? "PM" : "AM");
        }
        else if(hourOfDay>12){
            result=hourOfDay%12+":"+(minute < 10 ? ("0" + minute) : minute)+ " " + ((hourOfDay >= 12) ? "PM" : "AM");
        }
        return result;
    }

}
