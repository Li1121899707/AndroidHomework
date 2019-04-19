package com.example.liysuzy.broadcastsms;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    private static final String action = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(action)){
            Toast.makeText(context, "收到一条短信", Toast.LENGTH_LONG).show();
            notifation(context);
        }
    }

    public void notifation(Context context) {
        // 安卓版本高于8.0需要使用 “渠道”
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建通知渠道样例
            Toast.makeText(context, "Android 8+", Toast.LENGTH_SHORT).show();
            CharSequence name = "C1";
            String description = "desc1";
            String channelId = "channelId1";//渠道id
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);
            mChannel.setDescription(description);//渠道描述
            mChannel.enableLights(true);//是否显示通知指示灯
            mChannel.enableVibration(true);//是否振动
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
            Notification.Builder builder =
                    new Notification.Builder(context,channelId);
            builder.setSmallIcon(android.R.mipmap.sym_def_app_icon).
                    setContentTitle("收到一条短信！").
                    setContentText("短信详细内容请查看短信！").
                    setNumber(3);
            notificationManager.notify(0,builder.build());
        }else{
            Toast.makeText(context, "Android 8-", Toast.LENGTH_SHORT).show();

            Notification notification =
                    new NotificationCompat.Builder(context).
                            setContentTitle("收到一条短信！").
                            setContentText("短信详细内容请查看短信！").
                            setWhen(System.currentTimeMillis()).
                            setSmallIcon(R.drawable.ic_launcher_background).
                            build();
            NotificationManager manager =
                    (NotificationManager)context.getSystemService(MainActivity.NOTIFICATION_SERVICE);
            manager.notify(1,notification);
        }
    }
}
