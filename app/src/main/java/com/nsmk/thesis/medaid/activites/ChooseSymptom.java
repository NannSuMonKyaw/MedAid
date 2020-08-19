package com.nsmk.thesis.medaid.activites;

import android.content.res.AssetManager;

import com.nsmk.thesis.medaid.model.Symptom;

import java.util.ArrayList;
import java.util.List;

public interface ChooseSymptom {
    String getDialogMessage(List<String> list);
    ArrayList<Symptom> getModel();
    ArrayList<String> getSelectedValues();
    List<String> loadDataFromAssest(AssetManager assetManager, String path);
    void getCheckedSymptomsForDialog();
}
