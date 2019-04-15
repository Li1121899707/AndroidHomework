package com.example.liysuzy.classmatesresolver;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    private FloatingActionButton floatButton;
    private ListView lv;
    private String result;
    private List<Map<String, Object>> list;
    private String uri;
    private Integer selectPosition;
    private PopupWindow popupWindow;
    private Button btnCancel;
    private Button btnDelete;
    private Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
        queryAllToList();
        showList();
    }

    public void init(){
        Intent intent = getIntent();
        uri = intent.getStringExtra("uri");
        Log.i("uri", uri);

        floatButton = findViewById(R.id.fabAddContact);
        lv = findViewById(R.id.lvClassmates);
        list = new ArrayList<Map<String, Object>>();
        Log.i("classmate", "初始化");
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, AddLinkmanActivity.class);
                intent.putExtra("operation", "insert");
                intent.putExtra("uri", uri);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:;
            case 2:
                if(resultCode == RESULT_OK) {
                    queryAllToList();
                    showList();
                }
                break;
        }
    }

    public void queryAllToList(){
        list.clear();
        ContentResolver contentResolver = this.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(uri), null, null, null, null);
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
                new String[]{"dbid", "id", "name", "sex", "major", "grade", "classes"},
                new int[]{R.id.tvSQLId, R.id.tvId, R.id.tvName, R.id.tvSex, R.id.tvMajor, R.id.tvGrade, R.id.tvClass});
        lv.setAdapter(simpleAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
                popMenu();
            }
        });
    }

    public void popMenu(){
        View view1 = LayoutInflater.from(ListActivity.this).inflate(R.layout.popmenu,null);//PopupWindow对象
        popupWindow = new PopupWindow(ListActivity.this);
        //设置PopupWindow布局
        popupWindow.setContentView(view1);
        //设置动画
        popupWindow.setAnimationStyle(R.style.shipping_popup_style);
        //设置PopupWindow宽
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow高
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //在父布局的弹入/出位置
        View rootView = LayoutInflater.from(ListActivity.this).inflate(R.layout.activity_list, null);
        popupWindow.showAtLocation(rootView, Gravity.CENTER,0,0);
        //返回键点击，弹出
        popupWindow.setFocusable(false);

        btnUpdate = view1.findViewById(R.id.btnUpdate);
        btnDelete = view1.findViewById(R.id.btnDelete);
        btnCancel = view1.findViewById(R.id.btnCancel);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }

    public void delete(){
        ContentResolver contentResolver = this.getContentResolver();
        contentResolver.delete(Uri.parse(uri), "dbid=?", new String[]{String.valueOf(list.get(selectPosition).get("dbid"))});
        popupWindow.dismiss();
        Toast.makeText(this, "成功删除了一条数据！", Toast.LENGTH_SHORT).show();
        queryAllToList();
        showList();
    }

    public void update(){
        popupWindow.dismiss();
        Intent intent = new Intent(ListActivity.this, AddLinkmanActivity.class);
        intent.putExtra("operation", "update");
        intent.putExtra("uri", uri);
        Map<String , Object> map = list.get(selectPosition);
        intent.putExtra("dbid", String.valueOf(map.get("dbid")));
        intent.putExtra("id", String.valueOf(map.get("id")));
        intent.putExtra("name", String.valueOf(map.get("name")));
        intent.putExtra("sex", String.valueOf(map.get("sex")));
        intent.putExtra("grade", String.valueOf(map.get("grade")));
        intent.putExtra("major", String.valueOf(map.get("major")));
        intent.putExtra("classes", String.valueOf(map.get("classes")));
        startActivityForResult(intent, 2);
    }

}
