package com.nsmk.thesis.medaid.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nsmk.thesis.medaid.model.Medicine;

import java.util.List;
import java.util.MissingFormatArgumentException;

@Dao
public interface MedicineDAO {
    @Query("SELECT * FROM Medicine where diseaseName=:diseaseName")
    abstract List<Medicine> getMedicineList(String diseaseName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Medicine... medicines);

}
