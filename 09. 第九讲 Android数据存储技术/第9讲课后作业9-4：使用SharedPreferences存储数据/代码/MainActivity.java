package com.example.liysuzy.homework9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonInput = findViewById(R.id.buttonInput);
        Button buttonRead = findViewById(R.id.buttonRead);
        buttonRead.setEnabled(false);
        buttonInput.setEnabled(true);

        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                Log.i("infos", sp.getString("id", "0"));
                Intent intent3 = new Intent(MainActivity.this, ReadActivity.class);
                startActivity(intent3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Button buttonInput = findViewById(R.id.buttonInput);
        Button buttonRead = findViewById(R.id.buttonRead);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    buttonRead.setEnabled(true);
                    buttonInput.setEnabled(false);
                }
        }
    }
}
