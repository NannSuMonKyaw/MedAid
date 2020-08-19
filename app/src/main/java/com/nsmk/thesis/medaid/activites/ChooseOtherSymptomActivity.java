package com.nsmk.thesis.medaid.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.adapter.BodySymptomAdapter;
import com.nsmk.thesis.medaid.adapter.OtherSymptomAdapter;
import com.nsmk.thesis.medaid.common.BaseActivity;
import com.nsmk.thesis.medaid.custom_control.SymptomSharePreferenceHelper;
import com.nsmk.thesis.medaid.model.Symptom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class ChooseOtherSymptomActivity extends BaseActivity implements ChooseSymptom{
    private  String otherSymptomPath="othersymptoms.txt";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_back)
    ImageButton btnBack;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.rv_other_symptoms)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    AlertDialog.Builder builder;
    private SymptomSharePreferenceHelper sharePreferenceHelper;
    private ArrayList<Symptom> symptomArrayList;
    private OtherSymptomAdapter otherSymptomAdapter;
    private List<String> otherSymptomList;

    private ArrayList<String> checkedHeadSymptomList=new ArrayList<>();
    private ArrayList<String> checkedBodySymptomList=new ArrayList<>();
    private ArrayList<String> checkedSkinSymptomList=new ArrayList<>();
    private ArrayList<String> checkedOtherSymptomList=new ArrayList<>();

    // private String[] otherSymptomList={"shivering","chills","fatigue","weight_gain","mood_swings","weight_loss","restlessness","lethagy","irregular_sugar_level","high_fever","sweating","dehydration","loss_of_appetite","mild_fever","malaise","obesity","excessive_hunger","loss_of_balance","unsteadiness","depression","irritability","increased_appetite","family_history","lack_of_concentration","receiving_blood_transfusion","receiving_unsterile_injections","histroy_of_alcohol_consumption"};


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_choose_other_symptom;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Choose Symptoms!");

        sharePreferenceHelper=new SymptomSharePreferenceHelper(getApplicationContext());

        otherSymptomList=loadDataFromAssest(this.getAssets(),otherSymptomPath);

        symptomArrayList=getModel();
        otherSymptomAdapter=new OtherSymptomAdapter(this,symptomArrayList);
        recyclerView.setAdapter(otherSymptomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));


        builder = new AlertDialog.Builder(this);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> checkedSymptomList=getSelectedValues();
                sharePreferenceHelper.stroeCheckedOtherSymptoms("other",checkedSymptomList );
                finish();
                Intent intent=new Intent(getApplicationContext(), ChooseSkinSymptomActivity.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in_left,R.anim.slide_in_left);
                startActivity(intent,options.toBundle());
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> checkedSymptomList=getSelectedValues();
                sharePreferenceHelper.stroeCheckedOtherSymptoms("other",checkedSymptomList);

                // custom dialog
                ViewGroup viewGroup = findViewById(android.R.id.content);

                //then we will inflate the custom alert dialog xml that we created

                View dialogView = LayoutInflater.from(ChooseOtherSymptomActivity.this).inflate(R.layout.custom_symptom_confirmation_dialog,viewGroup,false);

                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseOtherSymptomActivity.this);

                //setting the view of the builder to our custom view that we already inflated
                builder.setView(dialogView);

                //finally creating the alert dialog and displaying it
                AlertDialog alertDialog = builder.create();

                LinearLayout symtomLayout=(LinearLayout)dialogView.findViewById(R.id.symptom_layout);
                LinearLayout nosymtomLayout=(LinearLayout)dialogView.findViewById(R.id.no_symptom_layout);
                LinearLayout headLayout=(LinearLayout)dialogView.findViewById(R.id.ly_head_title);
                LinearLayout bodyLayout=(LinearLayout)dialogView.findViewById(R.id.ly_body_title);
                LinearLayout skinLayout=(LinearLayout)dialogView.findViewById(R.id.ly_skin_title);
                LinearLayout otherLayout=(LinearLayout)dialogView.findViewById(R.id.ly_other_title);
                TextView tvHead=(TextView)dialogView.findViewById(R.id.tv_head_symptoms);
                TextView tvBody=(TextView)dialogView.findViewById(R.id.tv_body_symptoms);
                TextView tvSkin=(TextView)dialogView.findViewById(R.id.tv_skin_symptoms);
                TextView tvOther=(TextView)dialogView.findViewById(R.id.tv_other_symptoms);
                ImageButton btnClose=dialogView.findViewById(R.id.img_btn_close);


                //Adding checked symptoms to four arraylist to show message in dialog
                getCheckedSymptomsForDialog();

                if(!checkedHeadSymptomList.isEmpty()){
                    headLayout.setVisibility(View.VISIBLE);
                    tvHead.setText(getDialogMessage(checkedHeadSymptomList));
                } else {
                    headLayout.setVisibility(View.GONE);
                }

                if (!checkedBodySymptomList.isEmpty()) {
                    bodyLayout.setVisibility(View.VISIBLE);
                    tvBody.setText(getDialogMessage(checkedBodySymptomList));
                }else{
                    bodyLayout.setVisibility(View.GONE);
                }

                if(!checkedSkinSymptomList.isEmpty()){
                    skinLayout.setVisibility(View.VISIBLE);
                    tvSkin.setText(getDialogMessage(checkedSkinSymptomList));
                }else{
                    skinLayout.setVisibility(View.GONE);
                }

                if (!checkedOtherSymptomList.isEmpty()) {
                    otherLayout.setVisibility(View.VISIBLE);
                    tvOther.setText(getDialogMessage(checkedOtherSymptomList));
                }else{
                    otherLayout.setVisibility(View.GONE);
                }

                if(checkedHeadSymptomList.isEmpty() & checkedBodySymptomList.isEmpty() & checkedSkinSymptomList.isEmpty() & checkedOtherSymptomList.isEmpty()){
                    symtomLayout.setVisibility(View.GONE);
                    nosymtomLayout.setVisibility(View.VISIBLE);
                }


                Button btnCancel=dialogView.findViewById(R.id.btn_cancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                Button btnOk=dialogView.findViewById(R.id.btn_ok);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        finish();
                        Intent intent=new Intent(getApplicationContext(), DiagnoisActivity.class);

                        startActivity(intent);
                    }
                });

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
                InsetDrawable inset = new InsetDrawable(back, 24);
                alertDialog.getWindow().setBackgroundDrawable(inset);
                alertDialog.show();
            }
        });
    }




    @Override
    public String getDialogMessage(List<String> list) {
        String string="";
        for (int i=0;i<list.size();i++){
            string+=". "+list.get(i)+"\n";
        }
        return string;
    }

    @Override
    public ArrayList<Symptom> getModel() {
        ArrayList<Symptom> list=new ArrayList<>();
        ArrayList<String> checkedSymptomListFromPref=sharePreferenceHelper.getCheckedOtherSymptoms("other" );

        if(checkedSymptomListFromPref==null){
            for(int i=0;i<otherSymptomList.size();i++){
                Symptom symptom=new Symptom();
                symptom.setChecked(false);
                symptom.setSymptomName(otherSymptomList.get(i));
                list.add(symptom);
            }
        }
        else{
            for(int i=0;i<otherSymptomList.size();i++){
                Symptom symptom=new Symptom();
                if(checkedSymptomListFromPref.contains(otherSymptomList.get(i))){
                    symptom.setChecked(true);
                }
                else {
                    symptom.setChecked(false);
                }
                symptom.setSymptomName(otherSymptomList.get(i));
                list.add(symptom);
            }
        }
        return list;
    }

    @Override
    public ArrayList<String> getSelectedValues() {
        ArrayList<String> arrayList=new ArrayList<>();
        for(int i=0;i<otherSymptomAdapter.otherSymptomList.size();i++){
            if(otherSymptomAdapter.otherSymptomList.get(i).isChecked()){
                arrayList.add(otherSymptomAdapter.otherSymptomList.get(i).getSymptomName());
            }
        }
        return arrayList;
    }

    @Override
    public List<String> loadDataFromAssest(AssetManager assetManager, String path) {
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

    @Override
    public void getCheckedSymptomsForDialog() {
        //must clear all arraylist because it will again add checked symptom after clicking fab,otherwise checked symptom will be doubled
        checkedHeadSymptomList.clear();
        checkedBodySymptomList.clear();
        checkedSkinSymptomList.clear();
        checkedOtherSymptomList.clear();

        List<String> headSymptomsList=sharePreferenceHelper.getCheckedHeadSymptoms("head");
        List<String> bodySymptomList=sharePreferenceHelper.getCheckedBodySymptoms("body");
        List<String> skinSymptomsList=sharePreferenceHelper.getCheckedSkinSymptoms("skin");
        List<String> otherSymptomsList=sharePreferenceHelper.getCheckedOtherSymptoms("other");

        if(headSymptomsList!=null){
            checkedHeadSymptomList.addAll(headSymptomsList);
        }

        if(bodySymptomList!=null){
            checkedBodySymptomList.addAll(bodySymptomList);
        }
        if(skinSymptomsList!=null){
            checkedSkinSymptomList.addAll(skinSymptomsList);
        }
        if(otherSymptomsList!=null){
            checkedOtherSymptomList.addAll(otherSymptomsList);
        }
    }



    @Override
    public void onBackPressed() {
        // custom dialog
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created

        View dialogView = LayoutInflater.from(ChooseOtherSymptomActivity.this).inflate(R.layout.cancel_diagnosing_dialog,viewGroup,false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(ChooseOtherSymptomActivity.this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();

        Button btnOk=dialogView.findViewById(R.id.btn_ok);
        Button btnNo=dialogView.findViewById(R.id.btn_cancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePreferenceHelper.clearPref();
                alertDialog.dismiss();
                finish();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 24);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(inset);
        alertDialog.show();

    }
}
