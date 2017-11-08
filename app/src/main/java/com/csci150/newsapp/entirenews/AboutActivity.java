package com.csci150.newsapp.entirenews;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_about);

        Element adsElement = new Element();
        adsElement.setTitle("Advertise here");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.smaplelogo)
                .setDescription("This is demo version")
                .addItem(new Element().setTitle("Version 1.0"))
                .addItem(new Element().setTitle("Private Policy").setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.d("Kishan","Its working");
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), PrivatePolicyActivity.class);

                        startActivityForResult(intent, 100);

                    }
                }))
                .addItem(new Element().setTitle("Libraries we use").setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("Kishan","Its working");
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), LibActivity.class);

                        startActivityForResult(intent, 100);

                    }
                }))
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("kishanshah147@mail.fresnostate.edu")
                .addGitHub("Entire News Project (News App)")
                .addPlayStore("My Playstore")
                .addItem(createCopyright())
                .create();

        setContentView(aboutPage);
    }





    private Element createCopyright() {
        Element copyright = new Element();
        final String copyrightString = String.format("Copyright %d by EntireNews", java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setIconDrawable(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutActivity.this, copyrightString, Toast.LENGTH_SHORT).show();

            }
        });
        return copyright;
    }
}


