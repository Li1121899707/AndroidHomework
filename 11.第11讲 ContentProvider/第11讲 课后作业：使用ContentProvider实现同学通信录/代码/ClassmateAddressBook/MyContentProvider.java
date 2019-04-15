package com.example.liysuzy.classmateaddressbook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyContentProvider extends ContentProvider {

    private MyDatabaseHelper dbHelper;
    private List<Map<String, Object>> list;
    private String result;

    public void createDatabase(){
        dbHelper = new MyDatabaseHelper(getContext(), "Classmates.db", null, 1);
        dbHelper.getWritableDatabase();
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int n = db.delete("classmates", selection, selectionArgs);
        System.out.println("影响了" + n + "条数据！");
        return 49;
    }

    @Override
    public String getType(Uri uri) {
        System.out.println("GetType OK!");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("classmates", null, values);
        values.clear();
        return null;
    }

    @Override
    public boolean onCreate() {
        createDatabase();
        System.out.println("OnCreate");
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        System.out.println("查询了所有同学信息!");
        return queryAllToList();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update("classmates", values, selection, selectionArgs);
        return 1;
    }

    public Cursor queryAllToList(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("classmates", null,null,null,null,null,null);
        return cursor;
    }
}
