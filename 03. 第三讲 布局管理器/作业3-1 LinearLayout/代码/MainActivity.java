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
        final EditText editSex = findViewById(R.id.editSex);
        final EditText editKind = findViewById(R.id.editKind);
        final EditText editAge = findViewById(R.id.editAge);
        final TextView textResult = findViewById(R.id.textResult);

        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        Button buttonReset = findViewById(R.id.buttonReset);

        // 创建侦听器

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                str += editName.getText().toString() + "，";
                str += editSex.getText().toString() + "，";
                str += editKind.getText().toString() + "，";
                str += editAge.getText().toString() + "岁";
                Log.i("编辑框","结果"+ str);
                textResult.setText(str);

                //str = str.substring(0, str.length()-1);
                //Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
            }

        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAge.setText("");
                editKind.setText("");
                editName.setText("");
                editSex.setText("");
                textResult.setText("请重新填写宠物信息！");
            }
        });

    }
}
