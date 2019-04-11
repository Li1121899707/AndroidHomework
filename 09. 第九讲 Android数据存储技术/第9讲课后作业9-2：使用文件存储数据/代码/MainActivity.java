package com.example.liysuzy.homework902;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText editId;
    private EditText editName;
    private RadioGroup radioGroupSex;
    private Spinner spinnerRoom;
    private ListView listView;
    private Integer spinnerString = 0;
    private Integer listString = 0;
    private Integer radioString = 0;
    private Button submit;
    private Button read;
    private RadioButton men;
    private RadioButton women;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = findViewById(R.id.editId);
        editName = findViewById(R.id.editName);
        radioGroupSex = findViewById(R.id.radioSex);
        spinnerRoom = findViewById(R.id.spinnerRoom);
        listView = findViewById(R.id.listMajor);
        submit = findViewById(R.id.submit);
        read = findViewById(R.id.read);
        men = findViewById(R.id.radioButtonMen);
        women = findViewById(R.id.radioButtonWomen);


        generate();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitButtonClick();
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadButtonClick();
            }
        });
    }



    public void generate(){
        submit.setEnabled(true);
        read.setEnabled(false);
        // Radio
        radioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton r = findViewById(checkedId);
                radioString = checkedId;
            }
        });

        // Spinner
        final String[] spinnerItems = {"松涛阁","竹海园","梅花园"};
        ArrayAdapter<String > spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerRoom.setAdapter(spinnerAdapter);
        spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, spinnerItems[position], Toast.LENGTH_SHORT).show();
                spinnerString = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ListView
        final String[] listItems = {"土木","计算机","软件","数字媒体"};
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, listItems);
        listView.setAdapter(listAdapter);
        // 后面圆点会亮
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // 默认选中第一个
        listView.setItemChecked(0, true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "选择了第：" + listItems[position], Toast.LENGTH_LONG).show();
                listString = position;
            }
        });
    }

    public void SubmitButtonClick(){
        if(editId.getText().toString().equals("") || editName.getText().toString().equals("")){
            Toast.makeText(MainActivity.this, "请填写其他信息", Toast.LENGTH_SHORT).show();
        }
        else{
            String result = editId.getText().toString() + " " + editName.getText().toString() + " " +
                    radioString.toString() + " " + spinnerString + " " + listString;

            FileOutputStream fos = null;
            try {
                fos = openFileOutput("testfile", MODE_PRIVATE);
                fos.write(result.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            submit.setEnabled(false);
            read.setEnabled(true);

            editId.setText("");
            editName.setText("");
            listView.setItemChecked(0, true);
        }
    }

    public void ReadButtonClick() {
        FileInputStream fis = null;
        byte[] buffer = null;
        try{
            fis = openFileInput("testfile");
            buffer = new byte[fis.available()];
            fis.read(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null){
                try{
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String data = new String(buffer);
            Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            String[] words = data.split(" ");
            editId.setText(words[0]);
            editName.setText(words[1]);
            if(Integer.parseInt(words[2]) == 0)
                men.setChecked(true);
            else
                women.setChecked(true);

            spinnerRoom.setSelection(Integer.parseInt(words[3]));
            listView.setItemChecked(Integer.parseInt(words[4]), true);
        }
    }
}
