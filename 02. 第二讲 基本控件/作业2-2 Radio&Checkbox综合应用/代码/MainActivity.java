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
        final RadioGroup radioGroupSex = findViewById(R.id.radioGroupSex);
        final CheckBox checkBoxArt = findViewById(R.id.checkBoxArt);
        final CheckBox checkBoxMusic = findViewById(R.id.checkBoxMusic);
        final CheckBox checkBoxPe = findViewById(R.id.checkBoxPe);
        final CheckBox checkBoxGarden = findViewById(R.id.checkBoxGarden);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        // 创建侦听器
        radioGroupSex.setOnCheckedChangeListener(radioGroup_listener);
        checkBoxArt.setOnCheckedChangeListener(checkBox_listener);
        checkBoxMusic.setOnCheckedChangeListener(checkBox_listener);
        checkBoxPe.setOnCheckedChangeListener(checkBox_listener);
        checkBoxGarden.setOnCheckedChangeListener(checkBox_listener);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";

                Log.i("编辑框","姓名"+ editName.getText());
                str += editName.getText().toString();
                str += "，";

                // 性别
                for(int i = 0; i < radioGroupSex.getChildCount(); i++){
                    RadioButton r = (RadioButton) radioGroupSex.getChildAt(i);
                    if(r.isChecked()){
                        str += r.getText().toString();
                        Log.i("单选按钮","性别"+r.getText());
                        break;
                    }
                }

                str += "，喜欢：";

                // 爱好
                if(checkBoxMusic.isChecked())
                    str += checkBoxMusic.getText().toString() + "、";
                if(checkBoxArt.isChecked())
                    str += checkBoxArt.getText().toString() + "、";
                if(checkBoxPe.isChecked())
                    str += checkBoxPe.getText().toString() + "、";
                if(checkBoxGarden.isChecked())
                    str += checkBoxGarden.getText().toString() + "、";

                str = str.substring(0, str.length()-1);

                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
            }

        });

    }

    private RadioGroup.OnCheckedChangeListener radioGroup_listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton r = findViewById(checkedId);
            Log.i("单选按钮","您的选择是"+r.getText());
        }
    };

    private CompoundButton.OnCheckedChangeListener checkBox_listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                Log.i("复选按钮", "选中了"+ buttonView.getText().toString());
            }
        }
    };
}
