package com.example.liysuzy.classmatesresolver;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText etUri;
    private String uri;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            switch (button.getId()){
                case R.id.btnQuery:
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra("uri", uri);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUri = findViewById(R.id.etUri);
        uri = etUri.getText().toString();
        findViewById(R.id.btnQuery).setOnClickListener(listener);
        }

}
