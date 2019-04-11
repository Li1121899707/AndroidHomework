package com.example.liysuzy.sqliteafter1002;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class DescriptionActivity extends AppCompatActivity {

    private Button btnBack;
    private TextView showName;
    private TextView showPhone;
    private TextView showSex;
    private ImageView showPicture;

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        init();

        final Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        queryById(data);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void init(){
        btnBack = findViewById(R.id.btnBack);
        showName = findViewById(R.id.showName);
        showPhone = findViewById(R.id.showPhone);
        showSex = findViewById(R.id.showSex);
        showPicture = findViewById(R.id.showPicture);

        dbHelper = new MyDatabaseHelper(this, "Telbook.db", null, 1);
        dbHelper.getWritableDatabase();
    }

    public void queryById(String key){
        String result = "查询结果\n";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("telbook", null,"id=?",new String[]{key},null,null,null);
        if(cursor.moveToFirst()){
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                String name  = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String picture = cursor.getString(cursor.getColumnIndex("picture"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));

                result += "序号：" + id + "，姓名" + name
                        + "手机号："+ phone + "，图片路径："+ picture + "\n";
                Log.i("result", result);

                showName.setText(name);
                showPhone.setText(phone);
                showSex.setText(sex);
                Bitmap bitmap = BitmapFactory.decodeFile(picture);
                showPicture.setImageBitmap(bitmap);


            }while (cursor.moveToNext());
        }
        cursor.close();
    }
}
