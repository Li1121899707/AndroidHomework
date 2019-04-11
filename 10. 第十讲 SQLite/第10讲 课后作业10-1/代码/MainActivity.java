package com.example.liysuzy.sqliteafter1001;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etPrice;
    private EditText etInfo;
    private Button btnWrite;
    private Button btnQuery;
    private TextView tvResult;
    private RadioGroup rgType;
    private TextView tvTitleBalance;

    private String typeChoice;
    private MyDatabaseHelper dbHelper;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        createDatabase();

        tvTitleBalance.setText(queryBalanceFromTable().toString());

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击记账
                if(btnWrite.getText().toString().equals("记账"))
                    inputPrice();
                // 点击确定
                else{
                    Double price = Double.valueOf(etPrice.getText().toString());
                    String info = etInfo.getText().toString();
                    Double lastbalance = queryBalanceFromTable();
                    Double balance = 0.0;
                    if(typeChoice.equals("收入"))
                        balance  = lastbalance + price;
                    else
                        balance = lastbalance - price;
                    if(balance < 0)
                        Toast.makeText(MainActivity.this, "余额为负！", Toast.LENGTH_SHORT).show();

                    insertToDatabase(typeChoice, price, balance, info);
                    Toast.makeText(MainActivity.this, "记账成功！", Toast.LENGTH_SHORT).show();
                    tvTitleBalance.setText(queryBalanceFromTable().toString());
                    btnWrite.setText("记账");
                    btnQuery.setText("查询");
                    etInfo.setText("");
                    etPrice.setText("");
                    etInfo.setEnabled(false);
                    etPrice.setEnabled(false);
                }
            }
        });

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnQuery.getText().toString().equals("查询")){
                    tvTitleBalance.setText(queryBalanceFromTable().toString());
                    queryAll();
                    tvResult.setText(result);
                    Toast.makeText(MainActivity.this, "查询成功！", Toast.LENGTH_SHORT).show();
                }
                else{
                    etInfo.setText("");
                    etPrice.setText("");
                    etInfo.setEnabled(false);
                    etPrice.setEnabled(false);
                    btnWrite.setText("记账");
                    btnQuery.setText("查询");
                }
            }
        });

    }

    public void init(){
        etPrice = findViewById(R.id.etPrice);
        etInfo = findViewById(R.id.etInfo);
        btnWrite = findViewById(R.id.btnWrite);
        btnQuery = findViewById(R.id.btnQuery);
        tvResult = findViewById(R.id.tvResult);
        rgType = findViewById(R.id.rgType);
        tvTitleBalance = findViewById(R.id.tvTitleBalance);

        etInfo.setEnabled(false);
        etPrice.setEnabled(false);

        typeChoice = "收入";

        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton r = findViewById(checkedId);
                // 选中的赋值
                typeChoice = r.getText().toString();
            }
        });

    }

    public void createDatabase(){
        dbHelper = new MyDatabaseHelper(this, "Bill.db", null, 1);
        dbHelper.getWritableDatabase();
    }

    public void inputPrice(){
        etInfo.setEnabled(true);
        etPrice.setEnabled(true);
        btnWrite.setText("确定");
        btnQuery.setText("取消");
    }

    public void insertToDatabase(String type, Double price, Double balance, String info){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type" , type);
        values.put("price", price);
        values.put("balance", balance);
        values.put("info", info);
        db.insert("bill", null, values);
        values.clear();
    }

    public Double queryBalanceFromTable(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("bill", null,null,null,null,null,"id desc");
        Double balance = 0.0;
        if(cursor.getCount() == 0)
            balance = 0.0;
        else if(cursor.moveToFirst())
            balance = cursor.getDouble(cursor.getColumnIndex("balance"));

        return balance;
    }

    public void queryAll(){
        result = "查询结果\n";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("bill", null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                String type  = cursor.getString(cursor.getColumnIndex("type"));
                Double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Double balance = cursor.getDouble(cursor.getColumnIndex("balance"));
                String info = cursor.getString(cursor.getColumnIndex("info"));

                result += "序号：" + id + "，" + type
                        + "金额："+ price + "，说明："+ info + "，余额：" + balance + "\n";
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

}
