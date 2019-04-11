package com.example.liysuzy.sqliteafter1002;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatButton;
    private String  result;
    private ListView lv;
//    private Button refresh;

    private MyDatabaseHelper dbHelper;
    private List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        createDatabase();
        queryAll();
        refreshList();

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddLinkman.class);
                startActivityForResult(intent, 1);
            }
        });

//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                queryAll();
//                refreshList();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    queryAll();
                    refreshList();
                }
        }
    }

    public void init(){
        floatButton = findViewById(R.id.fabAddContact);
        lv = findViewById(R.id.lvContacts);
        list = new ArrayList<Map<String, Object>>();
        //refresh = findViewById(R.id.refresh);
    }

    public void createDatabase(){
        dbHelper = new MyDatabaseHelper(this, "Telbook.db", null, 1);
        dbHelper.getWritableDatabase();
    }

    public void refreshList(){
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.listcontent,
                new String[]{"picture", "name", "phone"}, new int[]{R.id.picture, R.id.tvName, R.id.tvPhone});
        lv.setAdapter(simpleAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map = list.get(position);
                Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
                intent.putExtra("data", String.valueOf(map.get("id")));
                startActivity(intent);
            }
        });
    }

    public void queryAll(){
        list.clear();
        result = "查询结果\n";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("telbook", null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                String name  = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String picture = cursor.getString(cursor.getColumnIndex("picture"));

                result += "序号：" + id + "，姓名" + name
                        + "手机号："+ phone + "，图片路径："+ picture + "\n";
                Log.i("result", result);

                Map<String , Object> map = new HashMap<String , Object>();
                //Bitmap bitmap = BitmapFactory.decodeFile(picture);
                map.put("id", id);
                map.put("picture", picture);
                map.put("name",name);
                map.put("phone",phone);
                list.add(map);

            }while (cursor.moveToNext());
        }
        cursor.close();
    }
}
