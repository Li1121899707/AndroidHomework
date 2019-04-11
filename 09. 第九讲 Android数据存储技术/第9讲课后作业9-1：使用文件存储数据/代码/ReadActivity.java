package com.example.liysuzy.homework9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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


        // FileInputStream

//        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editId.setText(sp.getString("id", "0"));
//        editName.setText(sp.getString("name", "未知"));
//        editAge.setText(sp.getString("age", "0"));
//        if(sp.getString("sex",  "sex").equals("男"))
//            radioButtonMen.setChecked(true);
//        else
//            radioButtonWomen.setChecked(true);
//
//        if(sp.getString("music", "0").equals("音乐"))
//            checkBoxMusic.setChecked(true);
//        if(sp.getString("art", "0").equals("美术"))
//            checkBoxArt.setChecked(true);
//        if(sp.getString("pe", "0").equals("体育"))
//            checkBoxPe.setChecked(true);

        FileInputStream fis = null;
        byte[] buffer = null;
        try {
            fis = openFileInput("homework9");
            buffer = new byte[fis.available()];
            fis.read(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String data = new String(buffer);
        String[] resultArray = data.split(" ");

        editId.setText(resultArray[0]);
        editName.setText(resultArray[1]);
        editAge.setText(resultArray[2]);
        if (resultArray[3].equals("男"))
            radioButtonMen.setChecked(true);
        else
            radioButtonWomen.setChecked(true);

        String[] hobbies = resultArray[4].split(",");
        for (int i = 0; i < hobbies.length; i++) {
            if (hobbies[i].equals("音乐"))
                checkBoxMusic.setChecked(true);
            if (hobbies[i].equals("美术"))
                checkBoxArt.setChecked(true);
            if (hobbies[i].equals("体育"))
                checkBoxPe.setChecked(true);

        }


    }

}
