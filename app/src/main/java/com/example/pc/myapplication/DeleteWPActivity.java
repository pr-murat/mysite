package com.example.pc.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DeleteWPActivity extends AppCompatActivity implements View.OnClickListener{

    Intent intent;
    String id, uri;
    Button btnDeleteWPok, btnDeleteWPcancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_wp);

        intent = getIntent();
        id = intent.getStringExtra("id");
        uri = intent.getStringExtra("uri");

        btnDeleteWPok = (Button) findViewById(R.id.btnDeleteWPok);
        btnDeleteWPcancel = (Button) findViewById(R.id.btnDeleteWPcancel);

        btnDeleteWPcancel.setOnClickListener(this);
        btnDeleteWPok.setOnClickListener(this);

    }
    Intent intents = new Intent();
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDeleteWPok:
                doDeleteWP();
                intents.putExtra("deleted", "true");
                setResult(1, intents);
                finish();
                break;
            case R.id.btnDeleteWPcancel:
                finish();
                break;

        }
    }


    private void doDeleteWP(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.execSQL("delete from "+DBHelper.TABLE_WP+" where "+DBHelper.KEY_WP_ID+"="+id+";");
        Toast.makeText(getApplicationContext(), "Жумушчу программа өчүрүлдү", Toast.LENGTH_SHORT).show();


        dbHelper.close();
    }
}
