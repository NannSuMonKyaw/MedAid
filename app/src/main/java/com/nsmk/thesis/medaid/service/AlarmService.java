package com.nsmk.thesis.medaid.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.nsmk.thesis.medaid.R;
import com.nsmk.thesis.medaid.activites.RingActivity;

import static com.nsmk.thesis.medaid.App.CHANNEL_ID;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.DOSE;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.TITLE;
import static com.nsmk.thesis.medaid.broadcastreceiver.AlarmBroadcastReceiver.TYPE;

public class AlarmService extends Service {

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, RingActivity.class);
//        notificationIntent.putExtra(TITLE,intent.getStringExtra(TITLE));
//        notificationIntent.putExtra(DOSE,intent.getFloatExtra(DOSE, (float) 1.0));
//        notificationIntent.putExtra(TYPE,intent.getStringExtra(TYPE));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,0);

        String alarmTitle = String.format("%s Alarm", intent.getStringExtra(TITLE));
        String medicineType=intent.getStringExtra(TYPE);
        float dose=intent.getFloatExtra(DOSE, (float) 1.0);
//        sendMedicineAlarmData(context,alarmTitle,medicineType,dose);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText(dose +" "+medicineType)
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentIntent(pendingIntent)
                .build();

        mediaPlayer.start();

        long[] pattern = { 0, 100, 1000 };
        vibrator.vibrate(pattern, 0);

        startForeground(1, notification);

        return START_STICKY;
    }

//    private void sendMedicineAlarmData(Context context,String title,String medicineType,float dose)
//    {
//        Log.i("alarm","pass data to ring activity");
//        Intent intent = new Intent();
//        intent.setAction("MedicineAlarmData");
//        intent.putExtra(TITLE,title);
//        intent.putExtra(DOSE,dose);
//        intent.putExtra(TYPE,medicineType);
//        sendBroadcast(intent);
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
