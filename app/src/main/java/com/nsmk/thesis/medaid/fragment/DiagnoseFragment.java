package com.nsmk.thesis.medaid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.activites.ChooseHeadSymptomActivity;
import com.nsmk.thesis.medaid.common.BaseFragment;

import butterknife.BindView;

public class DiagnoseFragment extends BaseFragment {

    public DiagnoseFragment() {
    }

    @BindView(R.id.btnok)
    Button btnOk;
    private static final String TAG = "DiagnoseFragment";
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_diagnose;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), ChooseHeadSymptomActivity.class);
                startActivity(intent);
            }
        });

    }



}
