package com.example.liysuzy.threadhomework;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    public int UPDATE_TEXT = 1;
    private TextView tv;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            tv.setText("" + UPDATE_TEXT);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message message = new Message();
                            UPDATE_TEXT ++;
                            message.what = UPDATE_TEXT;
                            handler.sendMessage(message);
                        }

                    }
                }).start();
            }
        });

    }
}
