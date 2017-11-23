package com.example.pc.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AboutApplicationActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnAboutActivityOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_application);
        imageView = (ImageView) findViewById(R.id.imageView2);

        imageView.setImageResource(R.mipmap.logotype);
        btnAboutActivityOk = (Button) findViewById(R.id.btnAboutActivityOk);
        btnAboutActivityOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
