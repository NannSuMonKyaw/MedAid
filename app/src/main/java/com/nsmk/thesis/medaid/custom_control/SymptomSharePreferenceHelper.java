package com.nsmk.thesis.medaid.custom_control;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SymptomSharePreferenceHelper {


    private  static String SHARE_PREFRENCE = "SYMPTOM";
    private static SharedPreferences sharedPreference;

    public SymptomSharePreferenceHelper(Context context) {
        sharedPreference = context.getSharedPreferences(SHARE_PREFRENCE, Context.MODE_PRIVATE);
    }

    public static void stroeCheckedHeadSymptoms(String arrayName, ArrayList<String> array) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(arrayName +"_size", array.size());
        for(int i=0;i<array.size();i++)
            editor.putString(arrayName + "_" + i, array.get(i));
        editor.apply();
    }

    public static ArrayList<String> getCheckedHeadSymptoms(String arrayName ) {
        int size = sharedPreference.getInt(arrayName + "_size", 0);
        ArrayList<String> array = new ArrayList<>(size);
        for(int i=0;i<size;i++)
            array.add(sharedPreference.getString(arrayName + "_" + i, null));
        return array;
    }

    public static void stroeCheckedBodySymptoms(String arrayName, ArrayList<String> array ) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(arrayName +"_size", array.size());
        for(int i=0;i<array.size();i++)
            editor.putString(arrayName + "_" + i, array.get(i));
        editor.apply();
    }

    public static ArrayList<String> getCheckedBodySymptoms(String arrayName ) {
        int size = sharedPreference.getInt(arrayName + "_size", 0);
        ArrayList<String> array = new ArrayList<>(size);
        for(int i=0;i<size;i++)
            array.add(sharedPreference.getString(arrayName + "_" + i, null));
        return array;
    }
    public static void stroeCheckedSkinSymptoms(String arrayName, ArrayList<String> array ) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(arrayName +"_size", array.size());
        for(int i=0;i<array.size();i++)
            editor.putString(arrayName + "_" + i, array.get(i));
        editor.apply();
    }

    public static ArrayList<String> getCheckedSkinSymptoms(String arrayName ) {
        int size = sharedPreference.getInt(arrayName + "_size", 0);
        ArrayList<String> array = new ArrayList<>(size);
        for(int i=0;i<size;i++)
            array.add(sharedPreference.getString(arrayName + "_" + i, null));
        return array;
    }
    public static void stroeCheckedOtherSymptoms(String arrayName, ArrayList<String> array ) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(arrayName +"_size", array.size());
        for(int i=0;i<array.size();i++)
            editor.putString(arrayName + "_" + i, array.get(i));
        editor.apply();
    }

    public static ArrayList<String> getCheckedOtherSymptoms(String arrayName ) {
        int size = sharedPreference.getInt(arrayName + "_size", 0);
        ArrayList<String> array = new ArrayList<>(size);
        for(int i=0;i<size;i++)
            array.add(sharedPreference.getString(arrayName + "_" + i, null));
        return array;
    }

    public static void clearPref(){

        sharedPreference.edit().clear().apply();
    }
  //

//    //Zg or MM3
//    public boolean isFontSetup() {
//        return sharedPreference.contains(FONT_KEY);
//    }
//    public String getFont() {
//        return sharedPreference.getString(FONT_KEY, "");
//    }
//
//    public void setFont(String language) {
//        SharedPreferences.Editor editor = sharedPreference.edit();
//        editor.putString(FONT_KEY, language);
//        editor.apply();
//    }
//
//    public String getLanguage()
//    {
//        return sharedPreference.getString(LANGUAGE_KEY, "");
//    }
//
//    public void setLanguage(String language)
//    {
//        SharedPreferences.Editor editor = sharedPreference.edit();
//        editor.putString(LANGUAGE_KEY, language);
//        editor.apply();
//    }
//
//    public boolean isLanguageFontSetup() {
//        if(sharedPreference.contains(FONT_KEY) && sharedPreference.contains(LANGUAGE_KEY))
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }

}
