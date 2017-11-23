package com.example.pc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.util.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class OpenPDFActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnIsser,btnIsserFileExsits;
    EditText edtttt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_pdf);

        edtttt = (EditText) findViewById(R.id.edtttt);
        btnIsser = (Button) findViewById(R.id.btnIsser);
        btnIsserFileExsits = (Button) findViewById(R.id.btnIsserFileExsits);
        btnIsser.setOnClickListener(this);
        btnIsserFileExsits.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIsser:
                pick();
                break;
            case R.id.btnIsserFileExsits:

                break;

        }
    }

    void pick(){
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "abc"), 42);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode==42 ){
            Uri uri = data.getData();
        }*/
        if (resultCode == RESULT_OK){
            if (requestCode == 42){
                Uri uri = data.getData();

            }
        }



        Toast.makeText(getApplicationContext(), data.getData().getUserInfo().toString(), Toast.LENGTH_LONG).show();
    }

    protected  void openODF(){
        Intent intent = new Intent();
        //intent.setType("application/pdf");
        intent.setType("images/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"),42 );
    }

}