package com.example.liysuzy.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifation();
            }
        });
    }

    public void notifation() {
        // 安卓版本高于8.0需要使用 “渠道”
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建通知渠道样例
            Toast.makeText(this, "Android 8+", Toast.LENGTH_SHORT).show();
            CharSequence name = "C1";
            String description = "desc1";
            String channelId = "channelId1";//渠道id
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);
            mChannel.setDescription(description);//渠道描述
            mChannel.enableLights(true);//是否显示通知指示灯
            mChannel.enableVibration(true);//是否振动
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
            Notification.Builder builder =
                    new Notification.Builder(this,channelId);
            builder.setSmallIcon(android.R.mipmap.sym_def_app_icon).
                    setContentTitle("收到一条通知！").
            setContentText("通知测试详细内容！").
            setNumber(3);
            int id = 1;// 随机定义
            notificationManager.notify(id,builder.build());
        }else{
            Toast.makeText(this, "Android 8-", Toast.LENGTH_SHORT).show();

            Notification notification =
                    new NotificationCompat.Builder(MainActivity.this).
                            setContentTitle("收到一条通知！").
                            setContentText("通知测试详细内容！").
                            setWhen(System.currentTimeMillis()).
                            setSmallIcon(R.drawable.ic_launcher_background).
                            setLargeIcon(BitmapFactory.
                                    decodeResource(getResources(),R.drawable.ic_launcher_foreground)).
                            build();
            NotificationManager manager =
                    (NotificationManager)getSystemService(MainActivity.NOTIFICATION_SERVICE);
            manager.notify(1,notification);
        }

    }
}
