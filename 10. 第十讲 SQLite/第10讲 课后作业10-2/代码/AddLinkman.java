package com.example.liysuzy.sqliteafter1002;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddLinkman extends AppCompatActivity {
    private Button submit;
    private Button reset;
    private Button btnChooseImg;
    private EditText etName;
    private EditText etPhone;
    private RadioGroup rgSex;
    private RadioButton rbMen;
    private RadioButton rbWomen;
    private ImageView picture;

    private String sexStr;
    private String imagePathStr;

    private String result;

    private MyDatabaseHelper dbHelper;

    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_linkman);
        init();
        createDatabase();

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                if(name.equals("") || sexStr.equals("")|| phone.equals("") || imagePathStr.equals("") ){
                    Toast.makeText(AddLinkman.this, "您还有信息尚未填写！", Toast.LENGTH_SHORT).show();
                }
                else{
                    insertToDatabase(name, sexStr, phone, imagePathStr);
                    Toast.makeText(AddLinkman.this, "添加联系人成功！", Toast.LENGTH_SHORT).show();
                    goBack();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    public void init(){
        submit = findViewById(R.id.submit);
        reset = findViewById(R.id.reset);
        btnChooseImg = findViewById(R.id.btnChooseImg);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        rgSex = findViewById(R.id.rgSex);
        rbMen = findViewById(R.id.rbMen);
        rbWomen = findViewById(R.id.rbWomen);
        picture = findViewById(R.id.picture);

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton r = findViewById(checkedId);
                // 选中的赋值
                sexStr = r.getText().toString();
            }
        });
    }

    public void createDatabase(){
        dbHelper = new MyDatabaseHelper(this, "Telbook.db", null, 1);
        dbHelper.getWritableDatabase();
    }

    public void insertToDatabase(String name, String sex, String phone, String picture){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("sex", sex);
        values.put("phone", phone);
        values.put("picture", picture);
        db.insert("telbook", null, values);
        values.clear();
    }

    public void goBack(){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    // 图片相关处理函数，参照课本
    public void chooseImg(){
        if(ContextCompat.checkSelfPermission(AddLinkman.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddLinkman.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {
            openAlbum();
        }
    }

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i("picture","打开相册");
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }
                else {
                    Toast.makeText(this, "你拒绝了申请读取SD卡的权限！", Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        Log.i("picture","API19");
        Log.i("picture", "requestcode" + requestCode);
        switch (requestCode){
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){

                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
                default:
                    break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this, uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri, null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        Toast.makeText(this, "显示",Toast.LENGTH_SHORT).show();
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
            // 图片存放位置
            Toast.makeText(this, imagePath,Toast.LENGTH_SHORT).show();
            imagePathStr = imagePath;
        }else {
            Toast.makeText(this, "打开图片失败",Toast.LENGTH_SHORT).show();
        }
    }
}
