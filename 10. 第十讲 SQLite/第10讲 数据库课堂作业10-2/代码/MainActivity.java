package com.example.liysuzy.sqlitehomework1001;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnGenerate;
    private Button btnClear;
    private Button btnShow;
    private TextView tvResult;
    private ListView lvNumbers;
    private MyDatabaseHelper dbHelper;
    private String result;
    final String[] listItems = {"数字","平方数","立方数"};
    final String[] listItemsEn = {"id","square","cube"};
    // 存放ListView的值
    private ArrayList<Integer> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件
        init();
        // 创建数据库 Number.db 与数据库表 number
        createDatabase();

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=1; i<10; i++)
                    insertToTable(i);
                Toast.makeText(MainActivity.this, "所有数据添加成功！", Toast.LENGTH_SHORT).show();
                btnShow.setEnabled(true);
                btnClear.setEnabled(true);
                btnGenerate.setEnabled(false);
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            // List 与 数组 相互转化。 因为数据库查询需要用到数组作为参数。
            public void onClick(View v) {
                List<String> templist = new ArrayList<String>();
                for(int i=0; i<arrayList.size(); i++){
                    if(arrayList.get(i) == 1)
                        templist.add(listItemsEn[i]);
                }
                int size=templist.size();
                String[] args = (String[])templist.toArray(new String[size]);
                retrieveFromTable(args);
                tvResult.setText(result);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText("");
                deleteTable();
                btnGenerate.setEnabled(true);
                btnClear.setEnabled(false);
                btnShow.setEnabled(false);
            }
        });
    }

    public void init(){

        btnGenerate = findViewById(R.id.btnGenerate);
        btnClear = findViewById(R.id.btnClear);
        btnShow = findViewById(R.id.btnShow);
        tvResult = findViewById(R.id.tvResult);
        lvNumbers = findViewById(R.id.lvNumbers);

        // ListView
        // 存放选中结果，选中为1，未选中为0
        arrayList = new ArrayList<>();
        // 初始化为0
        for(int i=0; i<listItems.length; i++)
            arrayList.add(0);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, listItems);
        lvNumbers.setAdapter(listAdapter);
        // 后面圆点会亮
        lvNumbers.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // 默认选中第一个
//        lvNumbers.setItemChecked(0, true);
//        arrayList.set(0, 1);

        lvNumbers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(MainActivity.this, "选择了：" + listItems[position], Toast.LENGTH_SHORT).show();
               // 点击一下调换顺序
               if(arrayList.get(position) == 0)
                   arrayList.set(position, 1);
               else
                   arrayList.set(position, 0);
            }
        });
    }

    public void createDatabase(){
        dbHelper = new MyDatabaseHelper(this, "Number.db", null, 1);
        dbHelper.getWritableDatabase();
    }

    public void insertToTable(Integer id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("square", id*id);
        values.put("cube", id*id*id);
        db.insert("number", null, values);
        values.clear();
    }

    public void retrieveFromTable(String[] args){
        result = "查询结果：\n";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("number", args,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                for(int i=0; i<args.length; i++){
                    if(args[i] != ""){
                        Integer num = cursor.getInt(cursor.getColumnIndex(args[i]));
                        result += num + "; ";
                    }
                }
                result += "\n";
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void deleteTable(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int num = db.delete("number",null,null);
        if(num>0)
            Toast.makeText(MainActivity.this, "删除所有数据成功", Toast.LENGTH_SHORT).show();
        else if(num == 0)
            Toast.makeText(MainActivity.this, "数据库表中没有数据", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "删除失败！", Toast.LENGTH_SHORT).show();
    }
}
