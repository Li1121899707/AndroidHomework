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

public class ReadActivity extends AppCompatActivity {

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
        final RadioButton radioButtonMen = findViewById(R.id.radioButtonMen);
        final RadioButton radioButtonWomen = findViewById(R.id.radioButtonWomen);



        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editId.setText(sp.getString("id", "0"));
        editName.setText(sp.getString("name", "未知"));
        editAge.setText(sp.getString("age", "0"));
        if(sp.getString("sex",  "sex").equals("男"))
            radioButtonMen.setChecked(true);
        else
            radioButtonWomen.setChecked(true);

        if(sp.getString("music", "0").equals("音乐"))
            checkBoxMusic.setChecked(true);
        if(sp.getString("art", "0").equals("美术"))
            checkBoxArt.setChecked(true);
        if(sp.getString("pe", "0").equals("体育"))
            checkBoxPe.setChecked(true);



    }

}
