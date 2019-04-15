package com.example.liysuzy.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MyContentProvider provider;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            switch (button.getId()){
                case R.id.insert:
                    provider.insert(null,null);
                    break;
                case R.id.delete:
                    int num = provider.delete(null, null, null);
                    ((TextView)findViewById(R.id.tvLog)).setText(num + "");
                    break;
                case R.id.read:
                    provider.query(null, null, null, null, null);
                    break;
                case R.id.update:
                    provider.update(null, null, null, null);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        provider = new MyContentProvider();
        findViewById(R.id.insert).setOnClickListener(listener);
        findViewById(R.id.read).setOnClickListener(listener);
        findViewById(R.id.update).setOnClickListener(listener);
        findViewById(R.id.delete).setOnClickListener(listener);
    }
}
