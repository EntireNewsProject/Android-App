package com.csci150.newsapp.entirenews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LibActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib);

        toolbar = findViewById(R.id.toolbar);
        setupToolbar(false, true);

        Button btRet = findViewById(R.id.btnRet);
        btRet.setOnClickListener(this);

        Button btGli = findViewById(R.id.btnGli);
        btGli.setOnClickListener(this);

        Button btCali = findViewById(R.id.btnCali);
        btCali.setOnClickListener(this);

        Button btJod = findViewById(R.id.btnJod);
        btJod.setOnClickListener(this);

        Button btAAP = findViewById(R.id.btnAAP);
        btAAP.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent browserIntent;
        switch (view.getId()){
            case R.id.btnRet:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://square.github.io/retrofit/"));
                startActivity(browserIntent);
                //here
                break;
            case R.id.btnGli:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bumptech.github.io/glide/"));
                startActivity(browserIntent);
                //here
                break;
            case R.id.btnCali:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://android-arsenal.com/details/1/163"));
                startActivity(browserIntent);
                break;
            case R.id.btnJod:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.joda.org/joda-time/"));
                startActivity(browserIntent);
                break;
            case R.id.btnAAP:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/medyo/android-about-page"));
                startActivity(browserIntent);
                break;
        }
    }
}


