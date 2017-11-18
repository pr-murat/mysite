package com.example.pc.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.BaseKeyListener;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class RenameActivity extends AppCompatActivity implements View.OnClickListener{

    DBHelper dbHelper;
    TextView textDayTime;
    EditText textNameRen, textAuditRen, textGroupRen, textCourseRen;
    Button btnRenameDataBase;
    int idint;
    String idstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename);

        textDayTime = (TextView) findViewById(R.id.textDayTime);

        textNameRen = (EditText) findViewById(R.id.textNameRen);
        textAuditRen = (EditText) findViewById(R.id.textAuditRen);
        textGroupRen = (EditText) findViewById(R.id.textGroupRen);
        textCourseRen = (EditText) findViewById(R.id.textCourseRen);
        btnRenameDataBase = (Button) findViewById(R.id.btnRenameDataBase);

        idint = Integer.parseInt(getIntent().getStringExtra("id").toString());
        idstr = getIntent().getStringExtra("id").toString();

        btnRenameDataBase.setOnClickListener(this);
        getDayTime();
        //Компоненттерге маалыматтарды коем
        setTexts();



    }

    void setTexts(){
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                if (idstr.equals(cursor.getString(0).toString())){
                    textNameRen.setText(cursor.getString(3).toString());
                    textAuditRen.setText(cursor.getString(5).toString());
                    textGroupRen.setText(cursor.getString(4).toString());
                    textCourseRen.setText(cursor.getString(6).toString());
                }
            }while(cursor.moveToNext());
        }
        dbHelper.close();
    }

    void getDayTime(){
        dbHelper = new DBHelper(this);
        String s = "null";
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                if (idstr.equals(cursor.getString(0).toString())){
                    //dbHelper.close();
                    s = cursor.getString(1).toString()+"-күн, "+cursor.getString(2).toString()+"-саат";
                }
            }while(cursor.moveToNext());
        }
        dbHelper.close();
        textDayTime.setText(s.toString());
    }

    void rename(){
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String name, audit, group, course;
        name = textNameRen.getText().toString();
        audit = textAuditRen.getText().toString();
        group = textGroupRen.getText().toString();
        course = textCourseRen.getText().toString();


        database.execSQL("update subjects set "+
                DBHelper.KEY_NAME+"='"+name+"', "+
                DBHelper.KEY_GROUP+"='"+group+"', "+
                DBHelper.KEY_AUDIT+"="+audit+", "+
                DBHelper.KEY_COURSE+"="+course+
                " where "+DBHelper.KEY_ID+"='"+idstr+"';");


        dbHelper.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRenameDataBase:
                if (isComponents()){
                    rename();
                    Toast.makeText(getApplicationContext(), "Маалымат сакталды", Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
        }
    }

    boolean isComponents(){
        String name, audit, group, course;
        name = textNameRen.getText().toString();
        audit = textAuditRen.getText().toString();
        group = textGroupRen.getText().toString();
        course = textCourseRen.getText().toString();

        //проверить сим оппостров
        if (name.contains("'") && audit.contains("'") && group.contains("'")){
            Toast.makeText(getApplicationContext(), "Туура эмес символ киргизилди", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (name.isEmpty() || audit.isEmpty() || group.isEmpty() || course.isEmpty()){
            Toast.makeText(getApplicationContext(), "Талааларды толтурунуз", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            try{
                int sss = Integer.parseInt(course);
                if (sss>6 || sss<1){
                    Toast.makeText(getApplicationContext(), "Курсту туура киргизиниз", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), "Курсту туура киргизиниз", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }
}
