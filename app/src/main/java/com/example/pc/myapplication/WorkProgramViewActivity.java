package com.example.pc.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FileUtils;

import java.io.File;
import java.util.HashMap;

public class WorkProgramViewActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewCoutPage;
    Intent intentGetIdAndUri;
    String idIntent;
    Button btnDeleteWorkProgramm, btnCancelWorkProgramViewAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_program_view);

        intentGetIdAndUri = getIntent();
        idIntent = intentGetIdAndUri.getStringExtra("id").toString();

        textViewCoutPage = (TextView) findViewById(R.id.textViewCoutPage);
        btnDeleteWorkProgramm = (Button) findViewById(R.id.btnDeleteWorkProgramm);
        btnCancelWorkProgramViewAc = (Button) findViewById(R.id.btnCancelWorkProgramViewAc);
        btnCancelWorkProgramViewAc.setOnClickListener(this);

        btnDeleteWorkProgramm.setOnClickListener(this);
        //uri = "Kotlin_promo.pdf";

        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        try{if(data.getStringExtra("deleted").toString().equals("true")){finish();}
        }catch (Exception e){}
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDeleteWorkProgramm:
                Intent intentDeleteWP = new Intent(this, DeleteWPActivity.class);
                intentDeleteWP.putExtra("id", idIntent);
                startActivityForResult(intentDeleteWP,1);
                break;
            case R.id.btnCancelWorkProgramViewAc:
                finish();
                break;
        }
    }


    //открыть pdf файл
    void getData(){
        textViewCoutPage.setText(getInfoDatabase(idIntent));
        try{
            //Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/1.pdf"), "application/pdf"
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://"+getInfoDatabase(idIntent)), "application/pdf");
            startActivity(intent);

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //извлечить путь из базы данных по id
    String getInfoDatabase(String id){
        String uri;
        DBHelper dbHelper = new DBHelper(this);     SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_WP, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                if(id.equals(cursor.getString(0).toString())){
                    uri = cursor.getString(2).toString();
                    dbHelper.close();
                    return uri;
                }
            }while(cursor.moveToNext());
        }
        dbHelper.close();
        return "";
    }
}
