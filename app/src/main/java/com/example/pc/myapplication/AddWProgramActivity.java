package com.example.pc.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FileUtils;

import java.io.File;
import java.io.InputStream;

public class AddWProgramActivity extends AppCompatActivity implements View.OnClickListener{

    DBHelper dbHelper;
    Button  btnSaveFileAndTema;
    EditText editTextWP;
    String tema = "";
    String res = "";
    TextView textViewRes;

    final String pathTeacherDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Teacher/" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wprogram);

        btnSaveFileAndTema = (Button) findViewById(R.id.btnSaveFileAndTema);
        editTextWP = (EditText) findViewById(R.id.editTextWP);
        textViewRes = (TextView) findViewById(R.id.textViewRes);
        btnSaveFileAndTema.setOnClickListener(this);

        File checkDir = new File(pathTeacherDir);
        if(checkDir.exists()) {
        } else {
            checkDir.mkdirs();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*case R.id.btnAddWP:
                getPathFile();
                break;*/
            case R.id.btnSaveFileAndTema:
                savePathInDb();
                break;
        }
    }

    //кнопка сохранить в базу данных
    void savePathInDb(){
        tema = editTextWP.getText().toString();
        String res = pathTeacherDir+textViewRes.getText().toString();
        if(!tema.isEmpty()){
            if (!(res.replace(pathTeacherDir,"")).isEmpty()){
                if (res.endsWith(".pdf")){
                    upDate(tema, res);
                    Toast.makeText(getApplicationContext(), "Ийгиликтүү сакталды", Toast.LENGTH_SHORT).show();
                    finish();}
                else {Toast.makeText(getApplicationContext(), "Файлдын атын тиби менен жазыныз. Үлгү: файл.pdf", Toast.LENGTH_LONG).show();}
            }else{
                Toast.makeText(getApplicationContext(), "Файлды танданыз", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Теманы жазыныз", Toast.LENGTH_SHORT).show();
        }
    }

    //Маалыматты базага киргизуу
    private void upDate(String temas, String ress){
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("insert into "+
                DBHelper.TABLE_WP+" ("+DBHelper.KEY_WP_TEMA+", "+DBHelper.KEY_WP_RES+") values ('"+temas+"', '"+ress+"');");
        dbHelper.close();
    }
}
