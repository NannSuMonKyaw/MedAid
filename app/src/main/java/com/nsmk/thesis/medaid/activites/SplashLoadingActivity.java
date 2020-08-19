package com.nsmk.thesis.medaid.activites;

import android.bluetooth.BluetoothAssignedNumbers;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nsmk.thesis.medaid.DB.InitializeDatabase;
import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.common.BaseActivity;

import butterknife.BindView;

public class SplashLoadingActivity extends BaseActivity {
    private static int SPLASH_TIME_OUT=5000;
    @BindView(R.id.logo_id)
    ImageView ivLogo;

    private Animation myAnim;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash_loading;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity

            InitializeDatabase.getInstance(SplashLoadingActivity.this);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
            ivLogo.startAnimation(myAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent=new Intent(SplashLoadingActivity.this,MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            },SPLASH_TIME_OUT);




        }
        else{
            //Intent homeIntent=new Intent(SplashLoadingActivity.this,MainActivity.class);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
            ivLogo.startAnimation(myAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent=new Intent(SplashLoadingActivity.this,MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            },2500);

        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

    }



}

