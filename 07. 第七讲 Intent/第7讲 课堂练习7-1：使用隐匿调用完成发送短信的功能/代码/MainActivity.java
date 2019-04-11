package com.example.liysuzy.sms;

import android.content.Intent;
import android.net.Uri;
import android.os.health.PackageHealthStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editPhone = findViewById(R.id.editPhone);
        final EditText editContent = findViewById(R.id.editContent);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String phone = editPhone.getText().toString();
                String content = editContent.getText().toString();
                Log.i("123", phone);
                Log.i("123", content);
                intent.setAction(intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + phone));
                intent.putExtra("sms_body", content);
                startActivity(intent);
            }
        });
    }
}
