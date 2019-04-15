package com.example.liysuzy.classmatesresolver;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liysuzy.classmatesresolver.R;

public class AddLinkmanActivity extends AppCompatActivity {
    private Button btnSubmit;
    private Button btnReset;
    private EditText etId;
    private EditText etName;
    private EditText etMajor;
    private EditText etGrade;
    private EditText etClass;
    private TextView tvAddInfo;

    private RadioGroup rgSex;
    private String sexStr="";
    private String uri;
    private String dbid;
    private String operation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_linkman);
        init();
    }

    public void init(){
        tvAddInfo = findViewById(R.id.tvAddInfo);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etMajor = findViewById(R.id.etMajor);
        etGrade = findViewById(R.id.etGrade);
        etClass = findViewById(R.id.etClass);
        rgSex = findViewById(R.id.rgSex);

        Intent intent = getIntent();
        operation = intent.getStringExtra("operation");
        if(operation.equals("insert")){
            dbid = "";
            tvAddInfo.setText("添加同学信息");
            btnSubmit.setText("添加");
        }else{
            dbid = intent.getStringExtra("dbid");
            tvAddInfo.setText("修改同学信息");
            btnSubmit.setText("更新");
            etId.setText(intent.getStringExtra("id"));
            etName.setText(intent.getStringExtra("name"));
            etMajor.setText(intent.getStringExtra("major"));
            etGrade.setText(intent.getStringExtra("grade"));
            etClass.setText(intent.getStringExtra("classes"));
        }
        uri = intent.getStringExtra("uri");
        Log.i("uri", uri);


        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton r = findViewById(checkedId);
                sexStr = r.getText().toString(); // 选中的赋值
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(); // 返回上一个Intent
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etId.getText().toString();
                String name = etName.getText().toString();
                String major = etMajor.getText().toString();
                String grade = etGrade.getText().toString();
                String classes = etClass.getText().toString();

                if(id.equals("") || name.equals("") || sexStr.equals("")|| major.equals("") || grade.equals("") || classes.equals("") ){
                    Toast.makeText(AddLinkmanActivity.this, "您还有信息尚未填写！", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(operation.equals("insert")){
                        insertToDatabase(id, name, sexStr, major, grade, classes);
                    }
                    else{
                        updateToDatabase(id, name, sexStr, major, grade, classes);
                    }
                    goBack();
                }
            }
        });
    }


    public void insertToDatabase(String id, String name, String sex, String major, String grade, String classes){
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("sex", sex);
        values.put("major", major);
        values.put("grade", grade);
        values.put("classes", classes);
        ContentResolver contentResolver = this.getContentResolver();
        contentResolver.insert(Uri.parse(uri), values);
        values.clear();
        Toast.makeText(this, "添加同学信息成功！", Toast.LENGTH_SHORT).show();
    }

    public void updateToDatabase(String id, String name, String sex, String major, String grade, String classes){
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("sex", sex);
        values.put("major", major);
        values.put("grade", grade);
        values.put("classes", classes);
        ContentResolver contentResolver = this.getContentResolver();
        contentResolver.update(Uri.parse(uri), values, "dbid=?", new String[]{dbid});
        values.clear();
        Toast.makeText(this, "修改同学信息成功！", Toast.LENGTH_SHORT).show();
    }

    public void goBack(){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
