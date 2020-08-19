package com.nsmk.thesis.medaid.activites;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.adapter.BodySymptomAdapter;
import com.nsmk.thesis.medaid.adapter.HeadSymptomAdapter;
import com.nsmk.thesis.medaid.classifier.Classifier;
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

public class ChooseBodySymptomActivity extends BaseActivity implements ChooseSymptom{
    private String bodySymptomPath="bodysymptoms.txt";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_back)
    ImageButton btnBack;

    @BindView(R.id.iv_next)
    ImageButton btnNext;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.rv_body_symptoms)
    RecyclerView recyclerView;
    AlertDialog.Builder builder;
    private SymptomSharePreferenceHelper sharePreferenceHelper;

    private ArrayList<Symptom> symptomArrayList;
    private BodySymptomAdapter bodySymptomAdapter;
    private List<String> bodySymptomList;
    private ArrayList<String> checkedHeadSymptomList=new ArrayList<>();
    private ArrayList<String> checkedBodySymptomList=new ArrayList<>();
    private ArrayList<String> checkedSkinSymptomList=new ArrayList<>();
    private ArrayList<String> checkedOtherSymptomList=new ArrayList<>();

    //  private String[] bodySymptomList={"joint_pain","stomach_pain","acidity","vomiting","burning_micturition","cold_hands_and_feet","indigestion","dark_urine","nausea","back_pain","constipation","abdominal_pain","diarrhoea","yellow_urine","swelling_of_stomach","phlegm","throat_irritation","chest_pain","weakness_in_limbs","fast_heart_rate","neck_pain","enlarged_thyroid","swollen_extremeties","knee_pain","hip_joint_pain","muscle_weakness","stiff_neck","swelling_joints","movement_stiffness","bladder_discomfort","foul_smell_of_urine","continuous_feel_of_urine","passage_of_gases","internal_itching","muscle_pain","belly_pain","abnormal_menstruation","polyuria","mucoid_sputum","rusty_sputum","distention_of_abdomen","fluid_overload","painful_walking"};


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_choose_body_symptom;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
    // TODO : clear checked when backpressed(asked user whether they want to really exit

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Choose Symptoms!");

        sharePreferenceHelper=new SymptomSharePreferenceHelper(getApplicationContext());

        bodySymptomList=loadDataFromAssest(this.getAssets(),bodySymptomPath);

        symptomArrayList=getModel();

        bodySymptomAdapter=new BodySymptomAdapter(this,symptomArrayList);
        recyclerView.setAdapter(bodySymptomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));


        builder = new AlertDialog.Builder(this);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> checkedSymptomList=getSelectedValues();
                sharePreferenceHelper.stroeCheckedBodySymptoms("body",checkedSymptomList );

                finish();
                Intent intent=new Intent(getApplicationContext(), ChooseSkinSymptomActivity.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in_right,R.anim.slide_in_right);
                startActivity(intent,options.toBundle());

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> checkedSymptomList=getSelectedValues();
                sharePreferenceHelper.stroeCheckedBodySymptoms("body",checkedSymptomList );
                finish();
                Intent intent=new Intent(getApplicationContext(), ChooseHeadSymptomActivity.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in_left,R.anim.slide_in_left);
                startActivity(intent,options.toBundle());
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> checkedSymptomList=getSelectedValues();
                sharePreferenceHelper.stroeCheckedBodySymptoms("body",checkedSymptomList);

                // custom dialog
                ViewGroup viewGroup = findViewById(android.R.id.content);

                //then we will inflate the custom alert dialog xml that we created

                View dialogView = LayoutInflater.from(ChooseBodySymptomActivity.this).inflate(R.layout.custom_symptom_confirmation_dialog,viewGroup,false);

                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseBodySymptomActivity.this);

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
        ArrayList<String> checkedSymptomListFromPref=sharePreferenceHelper.getCheckedBodySymptoms("body" );

        if(checkedSymptomListFromPref==null){
            for(int i=0;i<bodySymptomList.size();i++){
                Symptom symptom=new Symptom();
                symptom.setChecked(false);
                symptom.setSymptomName(bodySymptomList.get(i));
                list.add(symptom);
            }
        }
        else{
            for(int i=0;i<bodySymptomList.size();i++){
                Symptom symptom=new Symptom();
                if(checkedSymptomListFromPref.contains(bodySymptomList.get(i))){
                    symptom.setChecked(true);
                }
                else {
                    symptom.setChecked(false);
                }
                symptom.setSymptomName(bodySymptomList.get(i));
                list.add(symptom);
            }
        }
        return list;
    }

    @Override
    public ArrayList<String> getSelectedValues() {
        ArrayList<String> arrayList=new ArrayList<>();
        for(int i=0;i<bodySymptomAdapter.bodySymptomList.size();i++){
            if(bodySymptomAdapter.bodySymptomList.get(i).isChecked()){
                arrayList.add(bodySymptomAdapter.bodySymptomList.get(i).getSymptomName());
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

        View dialogView = LayoutInflater.from(ChooseBodySymptomActivity.this).inflate(R.layout.cancel_diagnosing_dialog,viewGroup,false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(ChooseBodySymptomActivity.this);

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
