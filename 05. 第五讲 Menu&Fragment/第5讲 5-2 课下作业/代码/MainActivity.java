package com.example.liysuzy.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.menutest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.openMenu:
                Log.i("menu","文件已打开");
                break;
            case R.id.closeMenu:
                Log.i("menu","文件已关闭");
                break;
            case R.id.exitMenu:
                Log.i("menu","确定要退出吗？");
                Toast.makeText(this, "确定要退出吗？", Toast.LENGTH_SHORT).show();

        }
        //Toast.makeText(this, item.getItemId(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
