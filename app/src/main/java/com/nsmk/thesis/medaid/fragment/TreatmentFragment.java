package com.nsmk.thesis.medaid.fragment;

import android.os.Bundle;
import android.util.Log;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nsmk.thesis.medaid.DB.InitializeDatabase;
import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.adapter.MedicineAdapter;
import com.nsmk.thesis.medaid.adapter.MedicineAlarmAdapter;
import com.nsmk.thesis.medaid.common.BaseFragment;
import com.nsmk.thesis.medaid.model.MedicineAlarm;

import java.util.List;

import butterknife.BindView;

public class TreatmentFragment extends BaseFragment {
    private static final String TAG = "TreatmentFragment";

    @BindView(R.id.rv_medicine_alarm_list)
    RecyclerView medicineAlarmRecyclerView;

    MedicineAlarmAdapter medicineAlarmAdapter;

    List<MedicineAlarm> medicineAlarmList;
    InitializeDatabase dbHelper;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_treatment;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        init();
    }

    public TreatmentFragment() {
    }

    private void init() {

        dbHelper = InitializeDatabase.getInstance(getContext());
        medicineAlarmList = dbHelper.getMedicineAlarmDAO().getAllMedicineAlarm();
        Log.i("alarm",medicineAlarmList.toString());

        medicineAlarmAdapter = new MedicineAlarmAdapter(getContext(), medicineAlarmList);
        medicineAlarmRecyclerView.setAdapter(medicineAlarmAdapter);
        medicineAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onResume() {
        super.onResume();
//        dbHelper = InitializeDatabase.getInstance(getContext());
//        medicineAlarmList = medicineAlarmList = dbHelper.getMedicineAlarmDAO().getAllMedicineAlarm();
//        if(medicineAlarmAdapter!=null){
//            medicineAlarmList.clear();
//            medicineAlarmList=dbHelper.getMedicineAlarmDAO().getAllMedicineAlarm();
//            medicineAlarmAdapter=new MedicineAlarmAdapter(getContext(),medicineAlarmList);
//            medicineAlarmAdapter.notifyDataSetChanged();
//            Log.i("alarm","refresh data ");
//        }


    }
}

