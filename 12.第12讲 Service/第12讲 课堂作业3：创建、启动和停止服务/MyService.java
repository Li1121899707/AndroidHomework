package com.example.liysuzy.homework1203;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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
