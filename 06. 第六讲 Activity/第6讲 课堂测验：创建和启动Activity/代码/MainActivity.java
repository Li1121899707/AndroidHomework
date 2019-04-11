package com.example.liysuzy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.parentButtonEnter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        Log.i("11","Create");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("11","Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("11","Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("11","Pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("11","Destroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("11","Stop");
    }
}
