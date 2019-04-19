package com.example.liysuzy.homework1205;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

public class MyService extends Service {
    private final String CHANNEL_ID = "TEST_SERVICE_ID";
    private final String CHANNEL_NAME = "TEST_SERVICE_NAME";
    private final String contentSub = "小标题";
    private final String contentTitle = "标题";
    private final String contentText = "测试前台服务";
    Notification notification;
    Notification.Builder builder;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel chan = new
                    NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            chan.enableLights(true);
            chan.setLightColor(Color.RED);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager!=null;
            manager.createNotificationChannel(chan);
            builder = new Notification.Builder(this, CHANNEL_ID);
            notification = builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(contentText)
                    .setSubText(contentSub)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentTitle(contentTitle)
                    .build();
        }
        else{
            Intent intent = new Intent();
            PendingIntent p = PendingIntent.getActivity(this, 1, intent, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notification = new Notification.Builder(getApplicationContext())
                        .setContentInfo(contentSub)
                        .setSubText(contentSub)
                        .setContentText(contentText)
                        .setContentTitle(contentTitle)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setContentIntent(p)
                        .build();
            }
        }
        System.out.println("Service is running!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, notification);
        return START_STICKY;
    }
}
