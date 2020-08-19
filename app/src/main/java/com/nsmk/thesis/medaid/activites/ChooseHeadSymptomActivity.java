package com.nsmk.thesis.medaid.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.Dialog;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.adapter.BodySymptomAdapter;
import com.nsmk.thesis.medaid.adapter.HeadSymptomAdapter;
import com.nsmk.thesis.medaid.adapter.OtherSymptomAdapter;
import com.nsmk.thesis.medaid.adapter.SkinSymptomAdapter;
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

public class ChooseHeadSymptomActivity extends BaseActivity implements ChooseSymptom{
    private String headSymptomPath="headsymptoms.txt";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_next)
    ImageButton btnNext;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.rv_head_symptoms)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;



    private ArrayList<Symptom> symptomArrayList;
    private HeadSymptomAdapter headSymptomAdapter;

    private  SymptomSharePreferenceHelper sharePreferenceHelper;
    private List<String> headSymptomList;

    private ArrayList<String> checkedHeadSymptomList=new ArrayList<>();
    private ArrayList<String> checkedBodySymptomList=new ArrayList<>();
    private ArrayList<String> checkedSkinSymptomList=new ArrayList<>();
    private ArrayList<String> checkedOtherSymptomList=new ArrayList<>();


    //private String[] headSymptomList={"continuous_sneezing","ulcers_on_tongue","cough","sunken_eyes","breathlessness","headache","yellowing_of_eyes","blurred_and_distored_vision","redness_of_eyes","sinus_pressure","runny_nose","congestion","puffy_face_and_eyes","loss_of_smell","watering_of_eyes","visual_disturbances","red_sore_around_nose","dizziness","spinning_movements"};

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_choose_head_symptom;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Choose Symptoms!");

        sharePreferenceHelper=new SymptomSharePreferenceHelper(getApplicationContext());

        headSymptomList=loadDataFromAssest(this.getAssets(),headSymptomPath);

        symptomArrayList=getModel();//get symtom data to setup
        headSymptomAdapter=new HeadSymptomAdapter(this,symptomArrayList);
        recyclerView.setAdapter(headSymptomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> checkedSymptomList=getSelectedValues();
                sharePreferenceHelper.stroeCheckedHeadSymptoms("head",checkedSymptomList);

                finish();
                Intent intent=new Intent(getApplicationContext(), ChooseBodySymptomActivity.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in_right,R.anim.slide_in_right);
                startActivity(intent,options.toBundle());

//                 //Set the dialog title
//                builder.setTitle("Your Symtoms are")
//                        .setMessage(getMessage(sharePreferenceHelper.getCheckedHeadSymptoms("head")))
//
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                //startActivity(intent);
//                              //  Toast.makeText(getApplicationContext(),  "clicked positive button", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                AlertDialog alert = builder.create();
//                alert.show();

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArrayList<String> checkedSymptomList=getSelectedValues();
                sharePreferenceHelper.stroeCheckedHeadSymptoms("head",checkedSymptomList);

                // custom dialog
                ViewGroup viewGroup = findViewById(android.R.id.content);

                //then we will inflate the custom alert dialog xml that we created

                View dialogView = LayoutInflater.from(ChooseHeadSymptomActivity.this).inflate(R.layout.custom_symptom_confirmation_dialog,viewGroup,false);

                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseHeadSymptomActivity.this);

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
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {//fun for menu
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        if(id == R.id.action_diagnose){
//            ArrayList<String> checkedSymptomList=getSelectedValues();
//            sharePreferenceHelper.stroeCheckedHeadSymptoms("head",checkedSymptomList);
//
//            finish();
//            Intent intent=new Intent(getApplicationContext(), DiagnoisActivity.class);
//
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


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
        ArrayList<String> checkedSymptomListfromPref=sharePreferenceHelper.getCheckedBodySymptoms("head" );

        if(checkedSymptomListfromPref==null){
            for(int i=0;i<headSymptomList.size();i++){
                Symptom symptom=new Symptom();
                symptom.setChecked(false);
                symptom.setSymptomName(headSymptomList.get(i));
                list.add(symptom);
            }
        }
        else{
            for(int i=0;i<headSymptomList.size();i++){
                Symptom symptom=new Symptom();
                if(checkedSymptomListfromPref.contains(headSymptomList.get(i))){
                    symptom.setChecked(true);
                }
                else {
                    symptom.setChecked(false);
                }
                symptom.setSymptomName(headSymptomList.get(i));
                list.add(symptom);
            }
        }
        return list;
    }

    @Override
    public ArrayList<String> getSelectedValues() {
        ArrayList<String> arrayList=new ArrayList<>();
        for(int i=0;i<headSymptomAdapter.headSymptomList.size();i++){
            if(headSymptomAdapter.headSymptomList.get(i).isChecked()){
                arrayList.add(headSymptomAdapter.headSymptomList.get(i).getSymptomName());
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

//    private void getCheckedSymptomsForDialog(){
//        if(sharePreferenceHelper.getCheckedHeadSymptoms("head")!=null) {
//            for (int i = 0; i < sharePreferenceHelper.getCheckedHeadSymptoms("head").size(); i++) {
//
//                    checkedHeadSymptomList.add(headSymptomAdapter.headSymptomList.get(i).getSymptomName());
//
//            }
//        }
//         if(sharePreferenceHelper.getCheckedBodySymptoms("body")!=null){
//            for(int i=0;i<sharePreferenceHelper.getCheckedBodySymptoms("body").size();i++){
//
//                    checkedBodySymptomList.add(bodySymptomAdapter.bodySymptomList.get(i).getSymptomName());
//            }
//        }
//         if(!skinSymptomAdapter.skinSymptomList.isEmpty()) {
//            for(int i=0;i<skinSymptomAdapter.skinSymptomList.size();i++){
//                if(skinSymptomAdapter.skinSymptomList.get(i).isChecked()){
//                    checkedSkinSymptomList.add(skinSymptomAdapter.skinSymptomList.get(i).getSymptomName());
//                }
//            }
//        }
//         if(!otherSymptomAdapter.otherSymptomList.isEmpty()){
//            for(int i=0;i<otherSymptomAdapter.otherSymptomList.size();i++){
//                if(otherSymptomAdapter.otherSymptomList.get(i).isChecked()){
//                    checkedOtherSymptomList.add(otherSymptomAdapter.otherSymptomList.get(i).getSymptomName());
//                }
//            }
//        }
//
//    }
    @Override
    public void getCheckedSymptomsForDialog(){
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

        View dialogView = LayoutInflater.from(ChooseHeadSymptomActivity.this).inflate(R.layout.cancel_diagnosing_dialog,viewGroup,false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(ChooseHeadSymptomActivity.this);

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
