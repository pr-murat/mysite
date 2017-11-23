package com.example.pc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkPrrammsActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listViewWorkProgramm;
    TextView textViewNameSubjectWP;
    Button btnStartActivity;
    DBHelper dbHelper;

    //  базадыгы таблицанын   id   си
    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> resarralist = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_prramms);

        btnStartActivity = (Button) findViewById(R.id.btnStartActivity);
        btnStartActivity.setOnClickListener(this);
        listViewWorkProgramm = (ListView) findViewById(R.id.listViewWorkProgramm);

        showWorkPrograms();

        final Intent intentViewWP = new Intent(this, WorkProgramViewActivity.class);
        listViewWorkProgramm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    intentViewWP.putExtra("id",id.get(i).toString());
                    intentViewWP.putExtra("uri",resarralist.get(i).toString());
                    startActivity(intentViewWP);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        showWorkPrograms();
    }

    @Override
    protected void onResume(){
        super.onResume();
        showWorkPrograms();
    }

    private void showWorkPrograms(){
        textViewNameSubjectWP = (TextView) findViewById(R.id.textViewNameSubjectWP);

        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;

        Cursor cursor = database.query(DBHelper.TABLE_WP, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            id.clear();
            resarralist.clear();
            do{
                map = new HashMap<String, Object>();
                map.put("textView", cursor.getString(1));
                id.add(cursor.getString(0));
                resarralist.add(cursor.getString(2));
                data.add(map);
            }while(cursor.moveToNext());
            if(data.isEmpty()){
                map = new HashMap<String, Object>();
                map.put("textView", "САБАКТАР ЖОК");
                data.add(map);//isEmptySub = "false";*/
            }
        }

        String[] from ={"textView"};
        int[] to ={R.id.textViewNameSubjectWP};
        MySimpleAdapter mySimpleAdapter = new MySimpleAdapter(this, data, R.layout.simple_item_work_programs_list, from, to);
        listViewWorkProgramm.setAdapter(mySimpleAdapter);

        dbHelper.close();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStartActivity:
                Intent intent = new Intent(this, AddWProgramActivity.class);
                startActivity(intent);
                break;
        }
    }
}


class  MySimpleAdapter extends SimpleAdapter{
    public  MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resorce, String[] from, int[] to){
        super(context, data, resorce, from, to);
    }

    @Override
    public  void  setViewText(TextView v, String text){
        super.setViewText(v, text);
        if (v.getId() == R.id.textViewNameSubjectWP){
            if(text.toString().equals("Окутуу")){
                v.setTextColor(Color.BLACK);
            }
        }
    }
}

