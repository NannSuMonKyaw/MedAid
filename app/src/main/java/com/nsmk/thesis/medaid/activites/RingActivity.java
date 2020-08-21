package com.nsmk.thesis.medaid.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.common.BaseActivity;
import com.nsmk.thesis.medaid.service.AlarmService;

import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;

import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.DOSE;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.TITLE;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.TYPE;

public class RingActivity extends BaseActivity {
    @BindView(R.id.activity_ring_dismiss)
    Button dismiss;
//    @BindView(R.id.activity_ring_snooze)
//    Button snooze;
    @BindView(R.id.activity_ring_clock)
    ImageView clock;

    @BindView(R.id.textView)
    TextView textView;


    String medicineType,title;
    float dose;
    Context context;
    BroadcastReceiver dataReceiver;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_ring;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

//        medicineType=getIntent().getStringExtra(TYPE);
//        title=getIntent().getStringExtra(TITLE);
//        dose=getIntent().getFloatExtra(DOSE, (float) 1.0);



//        Log.i("alarm"," ring activity");
//        Intent intent = new Intent(getApplicationContext(), AlarmService.class);
//        medicineType=intent.getStringExtra(TYPE);
//        title=intent.getStringExtra(TITLE);
//        dose=intent.getFloatExtra(DOSE, (float) 1.0);
//        textView.setText(title+"\n "+dose +"  "+medicineType);


//        LocalBroadcastManager.getInstance(RingActivity.this).registerReceiver(
//                mMessageReceiver, new IntentFilter("MedicineAlarmData"));

//        context = this;
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("MedicineAlarmData");
//        dataReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                //UI update here
//                if (intent != null){
//                    Log.i("alarm","received data to ring activity");
//                    medicineType=intent.getStringExtra(TYPE);
//                    title=intent.getStringExtra(TITLE);
//                    dose=intent.getFloatExtra(DOSE, (float) 1.0);
//                    textView.setText(title+"\n "+dose +"  "+medicineType);
//                }
//                else if(intent==null){
//                    Log.i("alarm","received null intent ring activity");
//                }
//
//            }
//        };
//        registerReceiver(dataReceiver, filter);


        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });

//        snooze.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.add(Calendar.MINUTE, 10);
//
//                Alarm alarm = new Alarm(
//                        new Random().nextInt(Integer.MAX_VALUE),
//                        calendar.get(Calendar.HOUR_OF_DAY),
//                        calendar.get(Calendar.MINUTE),
//                        "Snooze",
//                        true,
//                        false,
//                        false,
//                        false,
//                        false,
//                        false,
//                        false,
//                        false,
//                        false
//                );
//
//                alarm.schedule(getApplicationContext());
//
//                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
//                getApplicationContext().stopService(intentService);
//                finish();
//            }
//        });

        animateClock();
    }

    @Override
    protected void onPause() {

//        unregisterReceiver(dataReceiver);
        super.onPause();
    }

    private void animateClock() {
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f);
        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.start();
    }


}