package com.example.liysuzy.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取所有元素
        final EditText editName = findViewById(R.id.editName);
        final EditText editPwd = findViewById(R.id.editPwd);

        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        Button buttonReset = findViewById(R.id.buttonReset);

        // 创建侦听器

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                str = editName.getText().toString()+ " , " + editPwd.getText().toString();
                Log.i("编辑框","结果"+ str);
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
                //str = str.substring(0, str.length()-1);
                //Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
            }

        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editName.setText("");
                editPwd.setText("");
                Toast.makeText(MainActivity.this, "已取消登录！", Toast.LENGTH_LONG).show();
            }
        });

    }
}
