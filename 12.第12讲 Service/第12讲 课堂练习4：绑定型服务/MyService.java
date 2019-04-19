package com.example.liysuzy.homework1203;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    class MyBinder extends Binder{

        public void started(){
            System.out.println("Binder started!");
        }

        public String getString(){
            return "Binder服务的数据为：This is Binder!";
        }
    }

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        MyBinder mBinder = new MyBinder();
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service is Created!");
        Toast.makeText(this, "Service is Created!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Service is running!");
        Toast.makeText(this, "Service is running!", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service is Destoryed!");
        Toast.makeText(this, "Service is Destoryed!", Toast.LENGTH_SHORT).show();
    }
}

