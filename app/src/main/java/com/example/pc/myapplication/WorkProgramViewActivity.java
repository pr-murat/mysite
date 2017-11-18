package com.example.pc.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class WorkProgramViewActivity extends AppCompatActivity {

    TextView textViewTitleBrowser;
    WebView webview;
    Intent intentGetIdAndUri;

    String id, uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_program_view);

        intentGetIdAndUri = getIntent();
        id = intentGetIdAndUri.getStringExtra("id");
        uri = intentGetIdAndUri.getStringExtra("uri");

        textViewTitleBrowser = (TextView) findViewById(R.id.textViewTitleBrowser);
        webview = (WebView) findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        String uri = getIntent().getStringExtra("uri");
        webview.loadUrl(uri);
        textViewTitleBrowser.setText(getIntent().getStringExtra("id"));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add("Өчүрүү");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case 0:
                Intent intentDeleteWP = new Intent(this, DeleteWPActivity.class);
                intentDeleteWP.putExtra("id", id);
                intentDeleteWP.putExtra("uri", uri);
                startActivityForResult(intentDeleteWP,1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        try{
            if(data.getStringExtra("deleted").toString().equals("true")){
                finish();
            }
        }catch (Exception e){

        }

    }
}
