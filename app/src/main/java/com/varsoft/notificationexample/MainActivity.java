package com.varsoft.notificationexample;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button
            withClick,
            withTime;

    private NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        withClick = findViewById(R.id.withClick);
        withTime = findViewById(R.id.withTime);


        withClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withClickNotification();
            }
        });

        withTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withTimeNotification();
            }
        });
    }

    public void withClickNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(MainActivity.this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chanelID";
            String channelName = "chanelName";
            String channelDesc = "channelDesc";
            int channelPriority = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);
            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(channelId, channelName, channelPriority);
                notificationChannel.setDescription(channelDesc);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            builder = new NotificationCompat.Builder(this, channelId);
            builder.setContentTitle("Title");
            builder.setContentText("Content");
            builder.setSmallIcon(R.drawable.notifications);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
        } else {

            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Title");
            builder.setContentText("Content");
            builder.setSmallIcon(R.drawable.notifications);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        notificationManager.notify(1, builder.build());

    }

    public void withCloseNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(MainActivity.this, MainActivity.class);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chanelID";
            String channelName = "chanelName";
            String channelDesc = "channelDesc";
            int channelPriority = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);

            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(channelId, channelName, channelPriority);
                notificationChannel.setDescription(channelDesc);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            builder = new NotificationCompat.Builder(this, channelId);
            builder.setContentTitle("Title");
            builder.setContentText("Content");
            builder.setSmallIcon(R.drawable.notifications);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
        } else {

            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Title");
            builder.setContentText("Content");
            builder.setSmallIcon(R.drawable.notifications);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }

        Intent broadcastIntent = new Intent(MainActivity.this, NotificationReceiver.class);
        broadcastIntent.putExtra("object", builder.build());

        PendingIntent objectPendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long countDown = SystemClock.elapsedRealtime() + 10000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,countDown,objectPendingIntent);

    }


    public void withTimeNotification() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent ıntent = new Intent(MainActivity.this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, ıntent, PendingIntent.FLAG_UPDATE_CURRENT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = "channelId";
            String channelName = "channelName";
            String channelDescription = "channelDescription";
            int channelPriority = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);
            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(channelId, channelName, channelPriority);
                notificationChannel.setDescription(channelDescription);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            builder = new NotificationCompat.Builder(this, channelId);
            builder.setContentTitle("Title");
            builder.setContentText("Content");
            builder.setSmallIcon(R.drawable.notifications);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);

        } else {
            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Title");
            builder.setContentText("Content");
            builder.setSmallIcon(R.drawable.notifications);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            builder.setPriority(Notification.PRIORITY_HIGH);

        }

        Intent ıntentBroadcast = new Intent(MainActivity.this, NotificationReceiver.class);
        ıntentBroadcast.putExtra("object", builder.build());
        PendingIntent pendingIntentBroadcast = PendingIntent.getBroadcast(this, 0, ıntentBroadcast, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,21);
        calendar.set(Calendar.MINUTE,00);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,pendingIntentBroadcast);


    }

    public void cancelNotification(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent broadcastIntent = new Intent(MainActivity.this, NotificationReceiver.class);
        PendingIntent objectPendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(objectPendingIntent);


    }


    @Override
    protected void onStop() {
        super.onStop();
        withCloseNotification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cancelNotification();
    }
}
