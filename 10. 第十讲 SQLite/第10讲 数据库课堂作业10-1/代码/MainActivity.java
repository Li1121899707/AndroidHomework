package com.example.liysuzy.sqlitehomework1002;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 布局文件
    private EditText etId;
    private EditText etName;
    private RadioGroup rgSex;
    private RadioButton rbMen;
    private RadioButton rbWomen;
    private Spinner major;
    private Button btnSave;
    private Button btnRetrieve;
    private TextView tvResult;
    private String idChoiceStr;
    private String nameChoiceStr;
    private String sexChoiceStr;
    private String majorChoiceStr;
    private String result;

    // 数据库
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件
        init();
        // 创建数据库
        createDatabase();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idChoiceStr = etId.getText().toString();
                nameChoiceStr = etName.getText().toString();
                if(idChoiceStr.equals("") || nameChoiceStr.equals("") ){
                    Toast.makeText(MainActivity.this, "信息尚未填写完毕,请检查！",Toast.LENGTH_SHORT ).show();
                    return;
                }else{
                    // 存入数据库
                    int intid = Integer.valueOf(idChoiceStr);
                    insertToTable(intid, nameChoiceStr, sexChoiceStr, majorChoiceStr);
                    Toast.makeText(MainActivity.this, "添加成功！",Toast.LENGTH_SHORT ).show();
                    etId.setText("");
                    etName.setText("");
                }
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveFromTable();
                tvResult.setText(result);
            }
        });
    }

    public void init(){
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        rgSex = findViewById(R.id.rgSex);
        rbMen = findViewById(R.id.rbMen);
        rbWomen = findViewById(R.id.rbWomen);
        major = findViewById(R.id.major);
        btnSave = findViewById(R.id.btnSave);
        btnRetrieve = findViewById(R.id.btnRetrieve);
        tvResult = findViewById(R.id.tvResult);

        sexChoiceStr = "男";
        majorChoiceStr = "软件工程";

        // RadioGroup 性别
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton r = findViewById(checkedId);
                // 选中的赋值
                sexChoiceStr = r.getText().toString();
            }
        });

        // spinner 专业
        final String[] spinnerItems = {"软件工程","计算机","信息安全"};
        ArrayAdapter<String > spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        major.setAdapter(spinnerAdapter);
        major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, spinnerItems[position], Toast.LENGTH_SHORT).show();
                majorChoiceStr = spinnerItems[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void createDatabase(){
        dbHelper = new MyDatabaseHelper(this, "StuInfo.db", null, 1);
        dbHelper.getWritableDatabase();
    }

    public void insertToTable(Integer id, String name, String sex, String major){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("sex", sex);
        values.put("major", major);
        db.insert("stuinfo", null, values);
        values.clear();
    }

    public void retrieveFromTable(){
        result = "查询结果\n";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("stuinfo", null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                String major = cursor.getString(cursor.getColumnIndex("major"));
                result += "学号：" + id + "，姓名：" + name
                        + "，性别："+ sex + "，专业："+ major + "\n";
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

}
