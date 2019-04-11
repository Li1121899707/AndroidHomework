package com.example.liysuzy.activityreturn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        TextView childTextName = findViewById(R.id.childTextName);
        final Intent intent = getIntent();

        //String data = intent.getStringExtra("extra_data");
        //childTextName.setText(data);

        Bundle bundle = intent.getExtras();
        childTextName.setText(bundle.getString("name"));


        final EditText childEditName = findViewById(R.id.childEditName);

        Button childButtonReturn = findViewById(R.id.childButtonReturn);
        childButtonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("data_return", childEditName.getText().toString());
                setResult(RESULT_OK, intent1);
                finish();
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        TextView childTextName = findViewById(R.id.childTextName);
//        Intent intent3 = new Intent();
//        intent3.putExtra("data_return", childTextName.getText().toString());
//        setResult(RESULT_OK, intent3);
//        finish();
//    }
}
