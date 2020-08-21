package com.nsmk.thesis.medaid.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver;
import com.nsmk.thesis.medaid.util.DayUtil;

import java.util.Calendar;

import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.DOSE;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.FRIDAY;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.MONDAY;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.RECURRING;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.SATURDAY;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.SUNDAY;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.THURSDAY;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.TITLE;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.TUESDAY;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.TYPE;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.WEDNESDAY;

@Entity(tableName = "MedicineAlarm")
public class MedicineAlarm {
    @PrimaryKey
    @NonNull
    private int alarmId;

    private int hour, minute ;
    private int daysInterval;
    private boolean started, recurring;
    private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private String title,type;
    private float dose;

    public MedicineAlarm(int alarmId, String title, String type, float dose, int hour, int minute, int daysInterval, boolean started, boolean recurring, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        this.alarmId = alarmId;
        this.hour = hour;
        this.minute = minute;
        this.daysInterval = daysInterval;
        this.started = started;
        this.recurring = recurring;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.title = title;
        this.type = type;
        this.dose = dose;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isStarted() {
        return started;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public boolean isMonday() {
        return monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public String getTitle() {
        return title;
    }

    public int getDaysInterval() {
        return daysInterval;
    }

    public void setDaysInterval(int daysInterval) {
        this.daysInterval = daysInterval;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getDose() {
        return dose;
    }

    public void setDose(float dose) {
        this.dose = dose;
    }

    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(RECURRING, recurring);
        intent.putExtra(MONDAY, monday);
        intent.putExtra(TUESDAY, tuesday);
        intent.putExtra(WEDNESDAY, wednesday);
        intent.putExtra(THURSDAY, thursday);
        intent.putExtra(FRIDAY, friday);
        intent.putExtra(SATURDAY, saturday);
        intent.putExtra(SUNDAY, sunday);
        intent.putExtra(TYPE,type);
        intent.putExtra(DOSE,dose);
        intent.putExtra(TITLE, title);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        String toastText = String.format("Alarm %s scheduled for %s at %02d:%02d", title, DayUtil.toDay(calendar.get(Calendar.DAY_OF_WEEK)), hour, minute, alarmId);


        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();


        if(daysInterval==0 && !recurring){
            //alarm only once
            Log.i("alarm", "repeat only once    set exact");
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent
            );
        }
        else if(daysInterval!=0){
            final long INTERVAL_MILLIS;
            if(!recurring){
                Log.i("alarm", "repeat alarm at N-interval days  setrepeating");
                //repeat alarm at N-interval days
                INTERVAL_MILLIS = 24 * 60 * 60 * 1000 * daysInterval;
            }
            else {
                Log.i("alarm", "repeat alarm at selected days of week  setrepeating");
                //repeat alarm at selected days of week
                INTERVAL_MILLIS = 24 * 60 * 60 * 1000;
            }
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    INTERVAL_MILLIS,
                    alarmPendingIntent
            );
        }



        this.started = true;

        Log.i("schedule", toastText);

    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        this.started = false;

        String toastText = String.format("Alarm cancelled for %02d:%02d with id %d", hour, minute, alarmId);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        Log.i("cancel", toastText);
    }
}
