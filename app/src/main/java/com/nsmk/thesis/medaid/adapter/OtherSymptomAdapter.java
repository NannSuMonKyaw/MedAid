package com.nsmk.thesis.medaid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.model.Symptom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherSymptomAdapter extends RecyclerView.Adapter<OtherSymptomAdapter.MyViewHolder> {


    private Context mcontext;
    public static ArrayList<Symptom> otherSymptomList;

    public OtherSymptomAdapter(Context mcontext, ArrayList<Symptom> symptomList) {
        this.mcontext = mcontext;
        this.otherSymptomList = symptomList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.symptom_list_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Symptom symptom = otherSymptomList.get(position);
        holder.bindView(symptom);
    }

    @Override
    public int getItemCount() {
        return otherSymptomList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.symptomName)
        TextView textView;
        @BindView(R.id.chbContent)
        CheckBox checkBox;
        private Symptom symptom;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindView(Symptom symptom) {
            this.symptom=symptom;
            textView.setText(symptom.getSymptomName());
            checkBox.setChecked(symptom.isChecked());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (symptom.isChecked()) {
                        symptom.setChecked(false);
                    } else {
                        symptom.setChecked(true);
                    }
                }
            });







        }
    }


}
