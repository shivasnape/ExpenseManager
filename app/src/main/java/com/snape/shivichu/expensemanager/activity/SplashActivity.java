package com.snape.shivichu.expensemanager.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.snape.shivichu.expensemanager.R;

/**
 * Created by Shivichu on 23-03-2018.
 */

public class SplashActivity extends AppCompatActivity {

    private TextView tTitle,tFooter;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Hiding Title bar of this activity screen */
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        /** Making this activity, full screen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        typeface = Typeface.createFromAsset(this.getAssets(),"AveriaSansLibre-Bold.ttf");

        tTitle = (TextView)findViewById(R.id.txt_splash_title);
        tFooter = (TextView)findViewById(R.id.txt_splash_powered);

        tTitle.setTypeface(typeface);
        tFooter.setTypeface(typeface);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();

            }
        }, 500);

    }
}
