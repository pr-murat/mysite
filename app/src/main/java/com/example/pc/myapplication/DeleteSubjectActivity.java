package com.example.pc.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeleteSubjectActivity extends AppCompatActivity {

    Button btnDelete, btnBack;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_subject);
        //Intent intent = getIntent();

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnBack = (Button) findViewById(R.id.btnBack);
        dbHelper = new DBHelper(this);

        final Intent intentDoFinish = new Intent(this, SheduleViewActivity.class);

        final Intent intentGets = getIntent();
        final SheduleViewActivity sed = new SheduleViewActivity();

        final Intent intentDel = new Intent();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                //тут надо определить какие чекбоксы выделены
                database.execSQL("update subjects set "+
                        DBHelper.KEY_NAME+"='okoshka', "+
                                DBHelper.KEY_GROUP+"='nulling', "+
                                DBHelper.KEY_AUDIT+"=0, "+
                                DBHelper.KEY_COURSE+"=0"+
                                " where "+DBHelper.KEY_ID+"='"+intentGets.getStringExtra("id").toString()+"';");

                dbHelper.close();
                
                Toast.makeText(getApplicationContext(),"Өчүрүлдү", Toast.LENGTH_SHORT).show();
                intentDel.putExtra("finish","true");
                setResult(1, intentDel);

                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
