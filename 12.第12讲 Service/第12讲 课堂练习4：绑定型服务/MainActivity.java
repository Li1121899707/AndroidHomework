package com.example.liysuzy.homework1203;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MyService.MyBinder myBinder;
    private Intent intent;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder)service;
            myBinder.started();
            System.out.println(myBinder.getString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, MyService.class);

        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startService(intent);
                bindService(intent, connection, BIND_AUTO_CREATE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        int i = 1;
                        message.what = i;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

//        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stopService(intent);
//            }
//        });
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            ((TextView)findViewById(R.id.tv)).setText("Nice to meet you!");
        }
    };
}
