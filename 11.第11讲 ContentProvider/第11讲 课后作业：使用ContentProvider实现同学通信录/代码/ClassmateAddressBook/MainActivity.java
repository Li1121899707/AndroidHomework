package com.example.liysuzy.classmateaddressbook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private String result;

    private MyDatabaseHelper dbHelper;
    private List<Map<String, Object>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); // 初始化控件，添加监听事件
        createDatabase(); //创建数据库（如果没有数据库）
        queryAllToList(); // 查询数据库数据，填充到list中
        showList(); // 显示list
    }

    public void init(){
        lv = findViewById(R.id.lvClassmates);
        list = new ArrayList<Map<String, Object>>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    queryAllToList();
                    showList();
                }
        }
    }

    public void createDatabase(){
        dbHelper = new MyDatabaseHelper(this, "Classmates.db", null, 1);
        dbHelper.getWritableDatabase();
    }

    public void queryAllToList(){
        list.clear();
        result = "查询结果\n";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("classmates", null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Integer dbid = cursor.getInt(cursor.getColumnIndex("dbid"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name  = cursor.getString(cursor.getColumnIndex("name"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                String major = cursor.getString(cursor.getColumnIndex("major"));
                String grade = cursor.getString(cursor.getColumnIndex("grade"));
                String classmates = cursor.getString(cursor.getColumnIndex("classes"));

                result += "序号：" + dbid + "，学号" + id + "，姓名" + name
                        + "性别："+ sex + "，专业："+ major +
                        "，年级："+ grade + "，班级："+ classmates + "\n";
                Log.i("result", result);

                Map<String , Object> map = new HashMap<String , Object>();
                map.put("dbid", dbid);
                map.put("id", id);
                map.put("name", name);
                map.put("sex", sex);
                map.put("major", major);
                map.put("grade", grade);
                map.put("classes", classmates);
                list.add(map);

            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void showList(){
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.listcontent,
                new String[]{"id", "name", "sex", "major", "grade", "classes"},
                new int[]{R.id.tvId, R.id.tvName, R.id.tvSex, R.id.tvMajor, R.id.tvGrade, R.id.tvClass});
        lv.setAdapter(simpleAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
