package com.nsmk.thesis.medaid.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nsmk.thesis.medaid.DB.InitializeDatabase;
import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.adapter.BodySymptomAdapter;
import com.nsmk.thesis.medaid.adapter.MedicineAdapter;
import com.nsmk.thesis.medaid.classifier.Classifier;
import com.nsmk.thesis.medaid.common.BaseActivity;
import com.nsmk.thesis.medaid.custom_control.SymptomSharePreferenceHelper;
import com.nsmk.thesis.medaid.model.Medicine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DiagnoisActivity extends BaseActivity {
    private String symptomPath="symptoms.txt";
    private String modelPath="converted_model.tflite";
    private String labelPath="labels.txt";

    @BindView(R.id.tv_disease)
    TextView tvDisease;


    @BindView(R.id.rv_medicine_list)
    RecyclerView recyclerView;

    @BindView(R.id.layout_disease)
    LinearLayout layoutDisease;

    @BindView(R.id.layout_no_disease)
    RelativeLayout layoutNoDisease;

    @BindView(R.id.btn_diagnose)
    Button btnDiagnose;


    Classifier classifier;

    SymptomSharePreferenceHelper sharePreferenceHelper;

    List<String> checkedSymptomList;

    float[] input_data;

    InitializeDatabase dbHelper;

    MedicineAdapter medicineAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_diagnois;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        dbHelper=InitializeDatabase.getInstance(this);
        sharePreferenceHelper=new SymptomSharePreferenceHelper(getApplicationContext());
        classifier=new Classifier(this.getAssets(),modelPath,labelPath);
        checkedSymptomList=getCheckedSymptom();

        input_data=prepareDataforPredict(checkedSymptomList);

        List<Classifier.Recognition> recognitions=classifier.diagnose(input_data);

        if(!recognitions.isEmpty()){
//            get disease with maximum prediction percentage and check again if it is over 80%
            if(recognitions.get(0).getConfidence() >= 0.8){
                layoutDisease.setVisibility(View.VISIBLE);
                layoutNoDisease.setVisibility(View.GONE);

                tvDisease.setText(recognitions.get(0).getDisease());
                List<Medicine> medicineList = dbHelper.getMedicineDAO().getMedicineList(recognitions.get(0).getDisease());
                medicineAdapter=new MedicineAdapter(this,medicineList);
                recyclerView.setAdapter(medicineAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
            }
            else {
                layoutNoDisease.setVisibility(View.VISIBLE);
                layoutDisease.setVisibility(View.GONE);
            }
        }
        else{
            layoutNoDisease.setVisibility(View.VISIBLE);
            layoutDisease.setVisibility(View.GONE);
        }


        //if user want to diagnose agian.when no result showed
        btnDiagnose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(getApplicationContext(), ChooseHeadSymptomActivity.class);
                startActivity(intent);
            }
        });

//        String result="";
//        if(!recognitions.isEmpty()) {
//
////                    for(int i=0;i<recognitions.size();i++){
////                         result+=recognitions.get(i).getResult()+"\n";
////                    }
//
//            if(recognitions.get(0).getConfidence()  >= 0.8) {
//                result = recognitions.get(0).getResult() + "\n";
//                List<Medicine> medicineList = dbHelper.getMedicineDAO().getMedicineList(recognitions.get(0).getDisease());
//                result += "You may be suffering " + recognitions.get(0).getDisease()
//                        + "\nYou can take the following medicine to get better\n";
//                for (int i = 0; i < medicineList.size(); i++) {
//                    Medicine medicine = medicineList.get(i);
//                    result += medicine.medicineName + "\t"
//                            + medicine.noOfDosage + "\t"
//                            + medicine.medicineType + "\n";
//                }
//            }
//            else{
//                result="You may not added enough symptom or we may not be provided diagnosis of your suffering disease.";
//            }
//        }
//        else {
//            result="no disease is predicted";
//        }
//       // tvResult.setText(result);


        //To clear all checked
        sharePreferenceHelper.clearPref();


    }



    private float[] prepareDataforPredict(List<String> checkedSymptomlist){

        List<String> symptomList=loadDataFromAssest( this.getAssets(),symptomPath);
        float[] inputData = new float[symptomList.size()];
        for(int i=0;i<symptomList.size();i++){
            String symptomName=symptomList.get(i);
            for(int j=0;j<checkedSymptomlist.size();j++){
                if(checkedSymptomlist.contains(symptomName)){
                    inputData[i]= (float) 1.0;
                }
            }


        }
        return inputData;
    }


    private List<String> loadDataFromAssest(AssetManager assetManager, String path) {
        List<String> list=new ArrayList<String>();

        try {
            InputStream is = assetManager.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line=reader.readLine() )!= null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }


    private  List<String> getCheckedSymptom(){

        List<String> checkedSymtomList=new ArrayList<>();
        List<String> headSymptomsList=sharePreferenceHelper.getCheckedHeadSymptoms("head");
        List<String> bodySymptomList=sharePreferenceHelper.getCheckedBodySymptoms("body");
        List<String> skinSymptomsList=sharePreferenceHelper.getCheckedSkinSymptoms("skin");
        List<String> otherSymptomsList=sharePreferenceHelper.getCheckedOtherSymptoms("other");

         if(headSymptomsList!=null){
            for(int i=0;i<headSymptomsList.size();i++){
                checkedSymtomList.add(headSymptomsList.get(i));
            }
        }

        if(bodySymptomList!=null){
            for(int i=0;i<bodySymptomList.size();i++){
                checkedSymtomList.add(bodySymptomList.get(i));
            }
        }
        if(skinSymptomsList!=null){
            for(int i=0;i<skinSymptomsList.size();i++){
                checkedSymtomList.add(skinSymptomsList.get(i));
            }
        }
        if(otherSymptomsList!=null){
            for(int i=0;i<otherSymptomsList.size();i++){
                checkedSymtomList.add(otherSymptomsList.get(i));
            }
        }
        return checkedSymtomList;
    }
}
