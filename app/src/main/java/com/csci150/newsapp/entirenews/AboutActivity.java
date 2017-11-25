package com.csci150.newsapp.entirenews;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = findViewById(R.id.toolbar);
        setupToolbar(false, true);

        CoordinatorLayout mRoot = findViewById(R.id.main_view);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.logo_news)
                .setDescription(getString(R.string.description))
                .addItem(new Element().setTitle("Version 1.0"))
                .addItem(new Element().setTitle("Private Policy").setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), PrivatePolicyActivity.class);
                        startActivityForResult(intent, 100);
                    }
                }))
                .addItem(new Element().setTitle("Libraries we use").setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), LibActivity.class);
                        startActivityForResult(intent, 100);

                    }
                }))
                //.addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("kishanshah147@mail.fresnostate.edu")
                .addGitHub("EntireNewsProject")
                //.addPlayStore("My Playstore")
                .addItem(createCopyright())
                .create();

        mRoot.addView(aboutPage, 1);
    }

    private Element createCopyright() {
        Element copyright = new Element();
        final String copyrightString = String.format("Copyright %d by EntireNews",
                Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setIconDrawable(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        return copyright;
    }
}


