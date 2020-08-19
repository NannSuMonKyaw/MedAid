package com.nsmk.thesis.medaid.fragment;

import android.os.Bundle;


import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.common.BaseFragment;

public class TreatmentFragment extends BaseFragment {

    private static final String TAG = "TreatmentFragment";
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

    }



}
