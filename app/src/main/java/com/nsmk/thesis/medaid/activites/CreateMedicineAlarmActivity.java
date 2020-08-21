package com.nsmk.thesis.medaid.activites;

import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nsmk.thesis.medaid.DB.InitializeDatabase;
import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.common.BaseActivity;
import com.nsmk.thesis.medaid.model.MedicineAlarm;

import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;

public class CreateMedicineAlarmActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_medicine_name)
    EditText etMedicineName;

    @BindView(R.id.spinner_medicine_type)
    Spinner spinnerMedicineType;

    @BindView(R.id.spinner_frequency)
    Spinner spinnerFrequency;

    @BindView(R.id.frequency_layout1)
    LinearLayout frequencyLayout1;

    @BindView(R.id.frequency_layout2)
    LinearLayout frequencyLayout2;

    @BindView(R.id.frequency_layout3)
    LinearLayout frequencyLayout3;

    @BindView(R.id.frequency_layout4)
    LinearLayout frequencyLayout4;

    @BindView(R.id.schedule_dayOfWeek_layout)
    LinearLayout scheduleLayoutDayOfWeek;

    @BindView(R.id.schedule_days_interval_layout)
    LinearLayout scheduleLayoutDaysInterval;

    @BindView(R.id.et_time_layout1)
    EditText etTime1;

    @BindView(R.id.et_time_layout2)
    EditText etTime2;

    @BindView(R.id.et_time_layout3)
    EditText etTime3;

    @BindView(R.id.et_time_layout4)
    EditText etTime4;

    @BindView(R.id.et_dose_layout1)
    EditText etDose1;

    @BindView(R.id.et_dose_layout2)
    EditText etDose2;

    @BindView(R.id.et_dose_layout3)
    EditText etDose3;

    @BindView(R.id.et_dose_layout4)
    EditText etDose4;

    @BindView(R.id.chb_mon)
    CheckBox chbMon;

    @BindView(R.id.chb_tue)
    CheckBox chbTue;

    @BindView(R.id.chb_wed)
    CheckBox chbWed;

    @BindView(R.id.chb_thu)
    CheckBox chbThu;

    @BindView(R.id.chb_fri)
    CheckBox chbFri;

    @BindView(R.id.chb_sat)
    CheckBox chbSat;

    @BindView(R.id.chb_sun)
    CheckBox chbSun;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.np_days_interval)
    NumberPicker numberPicker;

    @BindView(R.id.spinner_repeat_schedule)
    Spinner spinnerSchedule;

//    @BindView(R.id.et_start_date)
//    EditText etStartDate;
//
//    @BindView(R.id.et_end_date)
//    EditText etEndDate;
    InitializeDatabase dbHelper;
    private String medicineName,medicineType, frequency,scheduleType;
    ArrayAdapter<CharSequence> medicineTypeAdapter, medicationFrequencyAdapter,scheduleTypeAdapter;
    private int daysInterval=0;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int et1Hour=-1,et2Hour=-1,et3Hour=-1,et4Hour=-1;
    private int et1Minute=-1,et2Minute=-1,et3Minute=-1,et4Minute=-1;
    private boolean recurring=false;
    private float doseOfet1,doseOfet2,doseOfet3,doseOfet4;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_create_medicine_alarm;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("New Medicine");

        dbHelper= InitializeDatabase.getInstance(this);

//        toolbar.setTitle("New medication");
//        toolbar.setNavigationIcon(R.drawable.icon_back_48);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), CreateMedicineAlarmActivity.class);
//                startActivity(intent);
//            }
//        });
        //MedicineType spinner
        medicineTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.medicine_type, android.R.layout.simple_spinner_item);

        medicineTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMedicineType.setAdapter(medicineTypeAdapter);

        spinnerMedicineType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                medicineType = parent.getItemAtPosition(position).toString();
                if(medicineType.equals("Inhaler")){
                    medicineType="puff";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Medication Frequency Adapter
        medicationFrequencyAdapter = ArrayAdapter.createFromResource(this,
                R.array.medication_frequency, android.R.layout.simple_spinner_item);

        medicationFrequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrequency.setAdapter(medicationFrequencyAdapter);

        spinnerFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                frequency = parent.getItemAtPosition(position).toString();
                Log.i("frequency", frequency);

                //appear TimePicker layout based on frequency
                if (frequency.equals(getString(R.string.am))) {
                    setLayoutVisibilty(true, false, false, false);
                    et1Hour=8;
                    et1Minute=0;
                    et2Hour=-1;
                    et3Hour=-1;
                    et4Hour=-1;
                    et2Minute=-1;
                    et3Minute=-1;
                    et4Minute=-1;
                    etTime1.setText("8:00 AM");
                } else if (frequency.equals(getString(R.string.od))) {
                    setLayoutVisibilty(true, false, false, false);
                    et1Hour=12;
                    et1Minute=0;
                    et2Hour=-1;
                    et3Hour=-1;
                    et4Hour=-1;
                    et2Minute=-1;
                    et3Minute=-1;
                    et4Minute=-1;
                    etTime1.setText("12:00 PM");
                } else if (frequency.equals(getString(R.string.bd))) {
                    setLayoutVisibilty(true, true, false, false);
                    et1Hour=12;
                    et1Minute=0;
                    etTime1.setText("12:00 PM");
                    et2Hour=18;
                    et2Minute=0;
                    et3Hour=-1;
                    et4Hour=-1;
                    et3Minute=-1;
                    et4Minute=-1;
                    etTime2.setText("6:00 PM");
                } else if (frequency.equals(getString(R.string.tds))) {
                    setLayoutVisibilty(true, true, true, false);
                    et1Hour=8;
                    et1Minute=0;
                    etTime1.setText("8:00 AM");
                    et2Minute=0;
                    et2Hour=12;
                    etTime2.setText("12:00 PM");
                    et3Hour=18;
                    et3Minute=0;
                    etTime3.setText("6:00 PM");
                    et4Hour=-1;
                    et4Minute=-1;
                } else if (frequency.equals(getString(R.string.fourTimes))) {
                    setLayoutVisibilty(true, true, true, true);
                    et1Hour=8;
                    et2Hour=12;
                    et3Hour=18;
                    et4Hour=22;
                    et1Minute=0;
                    et2Minute=0;
                    et3Minute=0;
                    et4Minute=0;
                    etTime1.setText("8:00 AM");
                    etTime2.setText("12:00 PM");
                    etTime3.setText("6:00 PM");
                    etTime4.setText("10:00 PM");
                } else if (frequency.equals(getString(R.string.hs))) {
                    setLayoutVisibilty(true, false, false, false);
                    et1Hour=22;
                    et1Minute=0;
                    et2Hour=-1;
                    et3Hour=-1;
                    et4Hour=-1;
                    et2Minute=-1;
                    et3Minute=-1;
                    et4Minute=-1;
                    etTime1.setText("10:00 PM");
                } else if (frequency.equals(getString(R.string.adlib))) {
                    setLayoutVisibilty(true, false, false, false);
                    et1Hour=12;
                    et1Minute=0;
                    et2Hour=-1;
                    et3Hour=-1;
                    et4Hour=-1;
                    et2Minute=-1;
                    et3Minute=-1;
                    et4Minute=-1;
                    etTime1.setText("12:00 PM");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Schedule Type Adapter
        scheduleTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.schedule_type, android.R.layout.simple_spinner_item);

        scheduleTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSchedule.setAdapter(scheduleTypeAdapter);

        spinnerSchedule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scheduleType = parent.getItemAtPosition(position).toString();
                Log.i("frequency", scheduleType);

                if(scheduleType.equals(getString(R.string.once))){
                    daysInterval=0;
                    recurring=false;
                    scheduleLayoutDayOfWeek.setVisibility(View.GONE);
                    scheduleLayoutDaysInterval.setVisibility(View.GONE);
                }
                else if(scheduleType.equals(getString(R.string.selectDays))){
                    daysInterval=1;
                    recurring=true;
                    scheduleLayoutDayOfWeek.setVisibility(View.VISIBLE);
                    scheduleLayoutDaysInterval.setVisibility(View.GONE);
                }
                else if(scheduleType.equals(getString(R.string.daysInterval))){
                    daysInterval=numberPicker.getValue();
                    recurring=false;
                    scheduleLayoutDayOfWeek.setVisibility(View.GONE);
                    scheduleLayoutDaysInterval.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(365);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                daysInterval=numberPicker.getValue();
                Toast.makeText(CreateMedicineAlarmActivity.this,
                        "selected number "+daysInterval, Toast.LENGTH_SHORT).show();
            }
        });




        etTime1.setOnClickListener(this);
        etTime2.setOnClickListener(this);
        etTime3.setOnClickListener(this);
        etTime4.setOnClickListener(this);



        //Schedule alarm and add data to database
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scheduleType.equals(getString(R.string.selectDays)) && NotCheckedAnyDays()){
                    Log.i("alarm","btnsave warning message");
                    Toast.makeText(CreateMedicineAlarmActivity.this,"Select at least one Day of Week",Toast.LENGTH_LONG).show();
                }
                else{
                    if(et1Hour!=-1 && et1Minute!=-1){
                        scheduleAlarm(et1Hour,et1Minute,Float.parseFloat(etDose1.getText().toString()));
                    }
                    if(et2Hour!=-1 && et2Minute!=-1){
                        scheduleAlarm(et2Hour,et2Minute,Float.parseFloat(etDose2.getText().toString()));
                    }
                    if(et3Hour!=-1 && et3Minute!=-1){
                        scheduleAlarm(et3Hour,et3Minute,Float.parseFloat(etDose3.getText().toString()));
                    }
                    if(et4Hour!=-1 && et4Minute!=-1){
                        scheduleAlarm(et4Hour,et4Minute,Float.parseFloat(etDose4.getText().toString()));
                    }
                    finish();
                }


            }
        });

    }

    private boolean NotCheckedAnyDays(){
        if(recurring) {
          if(chbMon.isChecked() || chbTue.isChecked() || chbWed.isChecked() || chbThu.isChecked() || chbFri.isChecked() || chbSat.isChecked() || chbSun.isChecked()){
                return false;
            }
            else{
                return true;
            }
        }

        return true;
    }

    private void setLayoutVisibilty(boolean layout1Visibilty, boolean layout2Visibility, boolean layout3Visibility, boolean layout4Visibility) {
        frequencyLayout1.setVisibility(View.GONE);
        frequencyLayout2.setVisibility(View.GONE);
        frequencyLayout3.setVisibility(View.GONE);
        frequencyLayout4.setVisibility(View.GONE);

        if (layout1Visibilty) {
            frequencyLayout1.setVisibility(View.VISIBLE);
        }
        if (layout2Visibility) {
            frequencyLayout2.setVisibility(View.VISIBLE);
        }
        if (layout3Visibility) {
            frequencyLayout3.setVisibility(View.VISIBLE);
        }
        if (layout4Visibility) {
            frequencyLayout4.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        // Get Current Time
//        final Calendar c = Calendar.getInstance();
//        mHour = c.get(Calendar.HOUR);
//        mMinute = c.get(Calendar.MINUTE);
//        Toast.makeText(CreateMedicineAlarmActivity.this, mHour+":"+mMinute,Toast.LENGTH_SHORT).show();
        if (v == etTime1) {

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        String result;
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            et1Hour=hourOfDay;
                            et1Minute=minute;
                            etTime1.setText(change12HourFormat(hourOfDay,minute));
                        }
                    }, et1Hour, et1Minute, false);
            timePickerDialog.show();
        }
        if(v==etTime2){
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        String result;
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            et2Hour=hourOfDay;
                            et2Minute=minute;
                            etTime2.setText(change12HourFormat(hourOfDay,minute));
                        }
                    }, et2Hour, et2Minute, false);
            timePickerDialog.show();
        }
        if(v==etTime3){
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        String result;
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            et3Hour=hourOfDay;
                            et3Minute=minute;
                            etTime3.setText(change12HourFormat(hourOfDay,minute));
                        }
                    }, et3Hour, et3Minute, false);
            timePickerDialog.show();
        }
        if(v==etTime4){
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        String result;
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            et4Hour=hourOfDay;
                            et4Minute=minute;
                            etTime4.setText(change12HourFormat(hourOfDay,minute));
                        }
                    }, et4Hour, et4Minute, false);
            timePickerDialog.show();
        }


//        if (v ==etStartDate) {
//
//
//
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                    new DatePickerDialog.OnDateSetListener() {
//
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                            etStartDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
//                        }
//
//                    }, mYear, mMonth, mDay);
//            datePickerDialog.show();
//        }
//        if (v ==etEndDate) {
//
//
//
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                    new DatePickerDialog.OnDateSetListener() {
//
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                            etEndDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
//                        }
//
//                    }, mYear, mMonth, mDay);
//            datePickerDialog.show();
//        }
    }



    private void scheduleAlarm(int hour,int minute,float dose) {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);

        MedicineAlarm alarm = new MedicineAlarm(
                alarmId,
                etMedicineName.getText().toString(),
                medicineType,
                dose,
                hour,
                minute,
                daysInterval,
                true,
                recurring,
                chbMon.isChecked(),
                chbTue.isChecked(),
                chbWed.isChecked(),
                chbThu.isChecked(),
                chbFri.isChecked(),
                chbSat.isChecked(),
                chbSun.isChecked()
        );

        dbHelper.getMedicineAlarmDAO().insertMedicineAlarm(alarm);
//        createAlarmViewModel.insert(alarm);
//
        alarm.schedule(this);
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


//    public void timePickerDialog(Context context,int mHour,int mMinute,boolean is24HourView,TimeForDialog time1){
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
//                new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
////                        etTime1.setText(hourOfDay + ":" + minute);
//
//                         time1.setHourOfDay(hourOfDay);
//                         time1.setMinute(minute);
//                        String result=time1.getHourOfDay()+":"+time1.getMinute();
//                        Toast.makeText(CreateMedicineAlarmActivity.this, result,Toast.LENGTH_SHORT).show();
//                    }
//                }, mHour, mMinute, is24HourView);
//        timePickerDialog.show();
//
//    }

//    public class TimeForDialog{
//        int hourOfDay;
//        int minute;
//
//        public TimeForDialog(int hourOfDay, int minute) {
//            this.hourOfDay = hourOfDay;
//            this.minute = minute;
//        }
//
//        public TimeForDialog() {
//        }
//
//        public int getHourOfDay() {
//            return hourOfDay;
//        }
//
//        public void setHourOfDay(int hourOfDay) {
//            this.hourOfDay = hourOfDay;
//        }
//
//        public int getMinute() {
//            return minute;
//        }
//
//        public void setMinute(int minute) {
//            this.minute = minute;
//        }
//    }

}