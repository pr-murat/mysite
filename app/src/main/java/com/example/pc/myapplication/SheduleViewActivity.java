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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SheduleViewActivity extends AppCompatActivity {

    ListView listViewView;
    Button btnDelete, btnRenameEdit;
    Intent intent;
    DBHelper dbHelper;

    @Override
    protected void onResume(){
        super.onResume();
        show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule_view);

        listViewView = (ListView) findViewById(R.id.listViewView);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnRenameEdit = (Button) findViewById(R.id.btnRenameEdit);
        intent = getIntent();
        dbHelper = new DBHelper(this);

        show();

        final Intent deleteIntent = new Intent(this, DeleteSubjectActivity.class);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteIntent.putExtra("id", intent.getStringExtra("intentId").toString());
                startActivityForResult(deleteIntent,1);
            }
        });

        final Intent intentRenameEdit = new Intent(this, RenameActivity.class);
        btnRenameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentRenameEdit.putExtra("id", intent.getStringExtra("intentId").toString());
                startActivity(intentRenameEdit);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //эгер колдонуучу маалыматты очурсо анда расписание активитимине ото
        //антпесе мурунку активити (View) ге келет
        try{
            if(data.getStringExtra("finish").toString().equals("true")){
                finish();
            }
        }catch(Exception e){

        }


    }

    public void show(){

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ArrayList<Map<String, String>> arrayListView = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()){

            HashMap map;
            //textDay.setText(textDay.getText()+(" таб не пусто "));
            do{
                if (intent.getStringExtra("intentId").toString().equals(cursor.getString(0).toString())){

                    map = new HashMap<String, Object>();
                    map.put("label", "Name");
                    map.put("data", cursor.getString(3));
                    arrayListView.add(map);

                    map = new HashMap<String, Object>();
                    map.put("label", "Time");
                    map.put("data", cursor.getString(2));
                    arrayListView.add(map);

                    map = new HashMap<String, Object>();
                    map.put("label", "Audit");
                    map.put("data", cursor.getString(5));
                    arrayListView.add(map);

                    map = new HashMap<String, Object>();
                    map.put("label", "Group");
                    map.put("data", cursor.getString(4));
                    arrayListView.add(map);

                    map = new HashMap<String, Object>();
                    map.put("label", "Curs");
                    map.put("data", cursor.getString(6));
                    arrayListView.add(map);

                }
            }while(cursor.moveToNext());

        }


        String[] from = {"label", "data"};
        int[] to = {R.id.idLabel, R.id.idData};
        SimpleAdapter adapterList = new SimpleAdapter(getApplicationContext(), arrayListView, R.layout.simple_list_shedule_view, from, to);
        listViewView.setAdapter(adapterList);




        dbHelper.close();
    }

}
