package com.nsmk.thesis.medaid.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.nsmk.thesis.medaid.model.MedicineAlarm;

import java.util.List;

@Dao
public interface MedicineAlarmDAO {
//    @Query("Update MedicineAlarm set isRemark=:boolValue where id=:movieId AND accountID=:accountID")
//    int updateMedicineAlarmById(int alarmId);

    @Update
    void updateMedicineAlarm(MedicineAlarm medicineAlarm);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMedicineAlarm(MedicineAlarm medicineAlarm);

    @Query("Delete from MedicineAlarm where alarmId=:alarmId")
    void deleteById(int alarmId);

    @Query("SELECT * from MedicineAlarm ORDER BY hour ASC")
    List<MedicineAlarm> getAllMedicineAlarm();

}
