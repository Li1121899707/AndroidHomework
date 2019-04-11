package com.example.liysuzy.homework9;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        final EditText editId = findViewById(R.id.editId);
        final EditText editName = findViewById(R.id.editName);
        final EditText editAge = findViewById(R.id.editAge);
        final RadioGroup radioGroupSex = findViewById(R.id.radioSex);
        final CheckBox checkBoxPe = findViewById(R.id.checkPe);
        final CheckBox checkBoxMusic = findViewById(R.id.checkMusic);
        final CheckBox checkBoxArt = findViewById(R.id.checkArt);

        radioGroupSex.setOnCheckedChangeListener(radioGroup_listener);
        checkBoxArt.setOnCheckedChangeListener(checkBox_listener);
        checkBoxMusic.setOnCheckedChangeListener(checkBox_listener);
        checkBoxPe.setOnCheckedChangeListener(checkBox_listener);


        Button buttonSubmit = findViewById(R.id.submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editId.getText().toString();
                String name = editName.getText().toString();
                String age = editAge.getText().toString();
                String sex = "";
                String music = "";
                String art = "";
                String pe = "";

                for(int i = 0; i < radioGroupSex.getChildCount(); i++){
                    RadioButton r = (RadioButton) radioGroupSex.getChildAt(i);
                    if(r.isChecked()){
                        sex += r.getText().toString();
                        break;
                    }
                }

                if(checkBoxMusic.isChecked())
                    music += checkBoxMusic.getText().toString();
                if(checkBoxArt.isChecked())
                    art += checkBoxArt.getText().toString();
                if(checkBoxPe.isChecked())
                    pe += checkBoxPe.getText().toString();

                SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("id", id);
                editor.putString("name", name);
                editor.putString("age", age);
                editor.putString("sex", sex);
                editor.putString("music", music);
                editor.putString("art", art);
                editor.putString("pe", pe);

                editor.commit();

                Intent intent2 = new Intent();
                setResult(RESULT_OK, intent2);
                finish();
            }
        });

        Button buttonReset = findViewById(R.id.reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editId.setText("");
                editAge.setText("");
                editName.setText("");
                checkBoxArt.setChecked(false);
                checkBoxMusic.setChecked(false);
                checkBoxPe.setChecked(false);
            }
        });


    }

    private RadioGroup.OnCheckedChangeListener radioGroup_listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton r = findViewById(checkedId);
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
