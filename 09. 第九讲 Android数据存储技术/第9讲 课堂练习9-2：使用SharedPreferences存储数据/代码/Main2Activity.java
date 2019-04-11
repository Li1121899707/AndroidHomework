package com.example.liysuzy.filetest2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView textName = findViewById(R.id.textName);
        TextView textPwd = findViewById(R.id.textPwd);

        SharedPreferences sp1 = getSharedPreferences("hitSoft", MODE_PRIVATE);
        String username = sp1.getString("UserName", "hit");
        String userpwd = sp1.getString("UserPwd", "000000");

        textName.setText(username);
        textPwd.setText(userpwd);

    }
}
