package com.example.liysuzy.filetest2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = findViewById(R.id.editName);
                EditText editPwd = findViewById(R.id.editPwd);
                String name = editName.getText().toString();
                String pwd = editPwd.getText().toString();

                SharedPreferences sp = getSharedPreferences("hitSoft", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("UserName", name);
                editor.putString("UserPwd", pwd);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });


    }
}
