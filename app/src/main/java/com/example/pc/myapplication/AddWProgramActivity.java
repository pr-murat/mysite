package com.example.pc.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

public class AddWProgramActivity extends AppCompatActivity implements View.OnClickListener{

    DBHelper dbHelper;
    Button btnAddWP, btnSaveFileAndTema;
    EditText editTextWP;
    String tema = "";
    String res = "";
    TextView textViewRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wprogram);

        btnAddWP = (Button) findViewById(R.id.btnAddWP);
        btnSaveFileAndTema = (Button) findViewById(R.id.btnSaveFileAndTema);
        editTextWP = (EditText) findViewById(R.id.editTextWP);
        textViewRes = (TextView) findViewById(R.id.textViewRes);

        btnAddWP.setOnClickListener(this);
        btnSaveFileAndTema.setOnClickListener(this);
    }

    //Маалыматты базага киргизуу
    private void upDate(String temas, String ress){
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.execSQL("insert into "+DBHelper.TABLE_WP+" ("+DBHelper.KEY_WP_TEMA+", "+DBHelper.KEY_WP_RES+") values ('"+temas+"', '"+ress+"');");

        dbHelper.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddWP:
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                //intent.setType("image/*");
                startActivityForResult(intent, 42);
                break;
            case R.id.btnSaveFileAndTema:
                tema = editTextWP.getText().toString();
                res = textViewRes.getText().toString();
                if(!tema.isEmpty()){
                    if (!res.isEmpty()){
                        upDate(tema, res);
                        Toast.makeText(getApplicationContext(), "Ийгиликтүү сакталды", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Файлды танданыз", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Теманы жазыныз", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
            if (requestCode == 42 && resultCode == Activity.RESULT_OK){
                Uri uri = null;
                if (data != null){
                    uri = data.getData();
                    Toast.makeText(getApplicationContext(), uri.toString()+" файл пикеред", Toast.LENGTH_SHORT).show();
                    textViewRes.setText(uri.toString());
                }
            }

    }



}
