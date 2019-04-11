package com.example.liysuzy.activityreturn;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText parentEditName = findViewById(R.id.parentEditName);
        Button parentEnterButton = findViewById(R.id.parentButtonEnter);
        parentEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = parentEditName.getText().toString();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                /************  6-2 ****************/
//                intent.putExtra("extra_data", data);
//                startActivityForResult(intent, 1);
                /**********************************/

                /************  6-3 ****************/
                Bundle bundle = new Bundle();
                bundle.putCharSequence("name", data);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String returnedData = data.getStringExtra("data_return");
                    TextView textView = findViewById(R.id.parentTextResult);
                    textView.setText(returnedData);
                }
        }
    }
}
