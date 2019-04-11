package com.example.liysuzy.gallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private int[] imageId = new int[]{
            R.drawable.img01,
            R.drawable.img02,
            R.drawable.img03,
            R.drawable.img04,
            R.drawable.img05,
            R.drawable.img06,
            R.drawable.img07,
            R.drawable.img08,
            R.drawable.img09,
            R.drawable.img10,
            R.drawable.img11,
            R.drawable.img12
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView = findViewById(R.id.gridView1);

        String[] title = new String[]{
                "花开富贵","海天一色","日出","天路",
                "一枝独秀","云","独占鳌头","蒲公英花",
                "花团锦簇","争奇斗艳","和谐","林间小路"
        };
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for(int i=0; i<imageId.length; i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", imageId[i]);
            map.put("title", title[i]);
            listItems.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, listItems,
                R.layout.item, new String[]{"title", "image"},
                new int[]{R.id.title, R.id.image});
        gridView.setAdapter(adapter);
    }
}
