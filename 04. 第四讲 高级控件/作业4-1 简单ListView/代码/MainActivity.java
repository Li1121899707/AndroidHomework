package com.example.liysuzy.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String [] str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        str = new String[]{"第一行代码Android","Android开发与实践","疯狂Android讲义", "精通Android Studio"};
        ListView listView = findViewById(R.id.list1);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, str);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, str);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("按下了", String.valueOf(position));
                Toast.makeText(MainActivity.this, str[position], Toast.LENGTH_LONG).show();
            }
        });

//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        {
//            Map<String , Object> map = new HashMap<String , Object>();
//            map.put("image", R.drawable.pig);
//            map.put("name","pig");
//            list.add(map);
//        }
//        {
//            Map<String , Object> map = new HashMap<String , Object>();
//            map.put("image", R.drawable.pig);
//            map.put("name","pig");
//            list.add(map);
//        }
//        {
//            Map<String , Object> map = new HashMap<String , Object>();
//            map.put("image", R.drawable.pig);
//            map.put("name","pig");
//            list.add(map);
//        }
//        {
//            Map<String , Object> map = new HashMap<String , Object>();
//            map.put("image", R.drawable.pig);
//            map.put("name","pig");
//            list.add(map);
//        }
//
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.listcontent,
//                new String[]{"image", "name"}, new int[]{R.id.imageView2, R.id.textView});
//        ListView lv = findViewById(R.id.list1);
//        lv.setAdapter(simpleAdapter);

    }
}
