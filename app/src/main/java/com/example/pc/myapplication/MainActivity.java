package com.example.pc.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    public boolean onCreateOptionsMenu(Menu menu){
        menu.add("Программа жөнүндө");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 0:
                Intent intent = new Intent(this, AboutApplicationActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        int[] imgs = {R.mipmap.main_shedule_icon,R.mipmap.main_work_program_icon,R.mipmap.main_today_icon};
        String[] texts = {"Жүгүртмө","Жумушчу программа","Календарь"};

        for (int i = 0; i < imgs.length; i++){
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("img", imgs[i]);
            m.put("textk",texts[i]);
            list.add(m);
        }

        String[] from = {"textk", "img"};
        int[] to = {R.id.satext, R.id.imageView};

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.simple_list, from, to);

        listView.setAdapter(adapter);
        final Intent sheduleIntent = new Intent(this, SheduleActivity.class);
        final Intent todayIntent = new Intent(this, TodayActivity.class);
        final Intent workProgrammsIntent = new Intent(this, WorkPrrammsActivity.class);
        final Intent todayTest = new Intent(this, OpenPDFActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(sheduleIntent);
                        break;
                    case 1:
                        startActivity(workProgrammsIntent);
                        break;
                    case 2:
                        startActivity(todayIntent);
                        break;
                    /*
                    case 3:
                        startActivity(todayTest);
                        break;*/
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
