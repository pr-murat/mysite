package com.example.pc.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SheduleActivity extends AppCompatActivity {


    ArrayList<String> identifikators = new ArrayList<>();
    ArrayList<HashMap<String, Object>> listShed;
    DBHelper dbHelper;
    Date todayDate;
    static int today;
    ListView listViewShedule;
    int selectDay;
    String isEmptySub;

    String[] days = {"Дүйшөмбү","Шейшемби","Шаршемби","Бейбшемби","Жума","Ишемби"};
    //int today;

    public boolean onCreateOptionsMenu(Menu menu){
        menu.add("Кошуу");
        menu.add("Өчүрүү");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.toString()){
            case "Кошуу":
                Intent addSubIntent = new Intent(this, AddSubActivity.class);
                addSubIntent.putExtra("day",Integer.toString(today));
                startActivity(addSubIntent);
                //Toast.makeText(this, "Нажата "+item.toString(), Toast.LENGTH_SHORT).show();
                break;
            case "Өчүрүү":

                break;
        }




        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart(){
        super.onStart();
        showlist(today+1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule);

        todayDate = new Date();
        today = todayDate.getDay()-1;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_for_shedule, days);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        listViewShedule = (ListView) findViewById(R.id.listViewShedule);

        spinner.setAdapter(adapter);
        spinner.setPrompt("Күн");
        spinner.setSelection(today);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showlist(i+1);
                today = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Intent intentView = new Intent(this, SheduleViewActivity.class);
        listViewShedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //intentView.putExtra("intentDay", Integer.toString(today));
                intentView.putExtra("intentId", identifikators.get(i));
                startActivity(intentView);
            }
        });


    }
     void showlist(int today){


         identifikators.clear();

         selectDay = today;
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //ArrayList<HashMap<String, Object>> listShed = getInformationTable(database, 2);
        listShed = new ArrayList<HashMap<String, Object>>();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                if (today == Integer.parseInt(cursor.getString(1)) && !cursor.getString(3).equals("okoshka")){
                    HashMap map = new HashMap<String, Object>();
                    identifikators.add(cursor.getString(0));
                    map.put("title", cursor.getString(2)+". "+cursor.getString(3));
                    map.put("info", cursor.getString(5)+" \t"+cursor.getString(4)+" \t"+cursor.getString(6));
                    listShed.add(map);
                }

            }while(cursor.moveToNext());
            isEmptySub = "true";
            if(listShed.isEmpty()){
                HashMap map = new HashMap<String, Object>();
                map.put("title", "САБАКТАР ЖОК");
                map.put("info", "");
                listShed.add(map);
                isEmptySub = "false";
            }

        }

        String[] from = {"title", "info"};
        int[] to = {R.id.idTitle, R.id.idInfor};
        SimpleAdapter adapterList = new SimpleAdapter(getApplicationContext(), listShed, R.layout.simple_list_item2, from, to);
        listViewShedule.setAdapter(adapterList);




        dbHelper.close();
    }

}
