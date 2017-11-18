package com.example.pc.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddSubActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase database;

    String[] days = {"Дүйшөмбү", "Шейшемби", "Шаршемби", "Бейбшемби", "Жума", "Ишемби"};
    String[] time = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    String[] course = {"1", "2", "3", "4", "5", "6"};
    int[] times = {1, 2, 3, 4, 5};

    //База даннызга киргизуу учун
    String fdname;
    int fdday;
    int fdtime;
    String fdgroup;
    String fdaudit;
    String fdcourse;


    Spinner spinnerDay, spinnerTime, spinnerCourse;
    Button btnSave;
    EditText name, group, audit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);


        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        String day = intent.getStringExtra("day");

        spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        spinnerTime = (Spinner) findViewById(R.id.spinnerTime);
        spinnerCourse = (Spinner) findViewById(R.id.spinnerCourse);
        btnSave = (Button) findViewById(R.id.btnSave);

        name = (EditText) findViewById(R.id.name);
        group = (EditText) findViewById(R.id.group);
        audit = (EditText) findViewById(R.id.audit);


        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, days);
        adapterDays.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDays);
        spinnerDay.setSelection(Integer.parseInt(day));

        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, time);
        adapterTime.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapterTime);

        ArrayAdapter<String> adapterCourse = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, course);
        adapterCourse.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(adapterCourse);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strname = name.getText().toString();
                String strgroup = group.getText().toString();
                String straudit = audit.getText().toString();
                if (!strname.isEmpty() && !strgroup.isEmpty() && !straudit.isEmpty()) {
                    if (strname.contains("'") && straudit.contains("'") && strgroup.contains("'")){
                        Toast.makeText(getApplicationContext(), "Туура эмес символ киргизилди", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    database = dbHelper.getWritableDatabase();
                    database.execSQL("update subjects set " +
                                                DBHelper.KEY_NAME+"='"+name.getText().toString()+"', " +
                                                DBHelper.KEY_GROUP+"='"+group.getText().toString()+"', " +
                                                DBHelper.KEY_AUDIT+"="+audit.getText().toString()+", " +
                                                DBHelper.KEY_COURSE+"="+(spinnerCourse.getSelectedItem().toString())+
                                                " where "+DBHelper.KEY_DAY+"='"+(spinnerDay.getSelectedItemId()+1)+"' and "+DBHelper.KEY_TIME+"='"+spinnerTime.getSelectedItem().toString()+"';");
                    /*
                    database.execSQL("update subjects set "+
                                                DBHelper.KEY_NAME+"='okoshka', " +
                                                DBHelper.KEY_GROUP+"='nulling', " +
                                                DBHelper.KEY_AUDIT+"=0, " +
                                                DBHelper.KEY_COURSE+"=0" +
                                                " where "+DBHelper.KEY_DAY+"='"+day+"' and "+DBHelper.KEY_TIME+"='"+time+"';");
*/
                    Toast.makeText(getApplicationContext(),"Маалымат сакталды", Toast.LENGTH_SHORT).show();
                    dbHelper.close();

                } else {
                    Toast.makeText(getApplicationContext(), "Берилген талааларды толтурунуз", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


}
