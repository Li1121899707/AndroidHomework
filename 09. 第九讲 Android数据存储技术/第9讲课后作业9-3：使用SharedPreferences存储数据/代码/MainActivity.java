package com.example.liysuzy.homework903;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editId = findViewById(R.id.editId);
        final EditText editName = findViewById(R.id.editName);
        final RadioGroup radioGroupSex = findViewById(R.id.radioSex);
        final RadioButton radioButtonMen = findViewById(R.id.radioButtonMen);
        final RadioButton radioButtonWomen = findViewById(R.id.radioButtonWomen);

        Button buttonSubmit = findViewById(R.id.submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editId.getText().toString();
                String name = editName.getText().toString();
                String sex = "";
                for(int i = 0; i < radioGroupSex.getChildCount(); i++){
                    RadioButton r = (RadioButton) radioGroupSex.getChildAt(i);
                    if(r.isChecked()){
                        sex += r.getText().toString();
                        break;
                    }
                }

                SharedPreferences sp = getSharedPreferences("mySP", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("id", id);
                editor.putString("name", name);
                editor.putString("sex", sex);

                editor.commit();

            }
        });

        Button buttonRead = findViewById(R.id.reset);
        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("mySP", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                String result = "ID：" + sp.getString("id", "0") + " 姓名：" +
                        sp.getString("name", "未知") + " 性别：" +
                        sp.getString("sex",  "sex");

                TextView textView = findViewById(R.id.textResult);
                textView.setText(result);
            }
        });


    }

    private RadioGroup.OnCheckedChangeListener radioGroup_listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton r = findViewById(checkedId);
        }
    };
}
