package com.nsmk.thesis.medaid.activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.common.BaseActivity;
import com.nsmk.thesis.medaid.fragment.DiagnoseFragment;
import com.nsmk.thesis.medaid.fragment.ProfileFragment;
import com.nsmk.thesis.medaid.fragment.TreatmentFragment;
import butterknife.BindView;


public class MainActivity extends BaseActivity {
    @BindView(R.id.nav_view)
    BottomNavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Animation fab_show, fab_hide;



    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        init(savedInstanceState);
    }
    private void init(Bundle savedInstanceState){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fab_show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_show);
        fab_hide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_hide);

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState != null) { // saved instance state, fragment may exist
            // just show the current instance
        } else {
            // only create fragment if they haven't been instantiated already
            loadFragment(new TreatmentFragment());
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(v.getContext(),CreateMedicineAlarmActivity.class);
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()  {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_treatment:
                    fragment = new TreatmentFragment();
                    loadFragment(fragment);
                    showFAB();
                    return true;
                case R.id.navigation_diagnose:
                    fragment = new DiagnoseFragment();
                    loadFragment(fragment);
                    hideFAB();
                    return true;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    hideFAB();
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_up,R.anim.slide_up);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    private void showFAB() {
        fab.show();
        fab.startAnimation(fab_show);
        fab.setClickable(true);
    }

    private void hideFAB() {
        fab.hide();
        fab.startAnimation(fab_hide);
        fab.setClickable(false);
    }
}
