package com.nsmk.thesis.medaid.fragment;

import android.os.Bundle;

import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.common.BaseFragment;

public class ProfileFragment extends BaseFragment {

    private static final String TAG = "ProflieFragment";
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        init();
    }

    private void init() {

    }

    public ProfileFragment() {
    }
}
