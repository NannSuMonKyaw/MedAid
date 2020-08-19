package com.nsmk.thesis.medaid.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.common.BaseActivity;
import com.nsmk.thesis.medaid.service.AlarmService;

import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;

public class RingActivity extends BaseActivity {
    @BindView(R.id.activity_ring_dismiss)
    Button dismiss;
//    @BindView(R.id.activity_ring_snooze)
//    Button snooze;
    @BindView(R.id.activity_ring_clock)
    ImageView clock;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_ring;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

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

    private void animateClock() {
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f);
        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.start();
    }
}