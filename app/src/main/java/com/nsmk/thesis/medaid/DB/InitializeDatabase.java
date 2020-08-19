package com.nsmk.thesis.medaid.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nsmk.thesis.medaid.DAO.MedicineDAO;
import com.nsmk.thesis.medaid.model.Medicine;

import java.util.concurrent.Executors;


@Database(entities = {Medicine.class}, version = 1,exportSchema = false)
public abstract class InitializeDatabase extends RoomDatabase {

    static String DB_NAME = "my-database";
    private static InitializeDatabase INSTANCE;

    public abstract MedicineDAO getMedicineDAO();

    public static InitializeDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static InitializeDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                InitializeDatabase.class, DB_NAME)
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).getMedicineDAO().insertAll(Medicine.populateData());
                            }
                        });
                    }
                })
                .build();
    }


}
