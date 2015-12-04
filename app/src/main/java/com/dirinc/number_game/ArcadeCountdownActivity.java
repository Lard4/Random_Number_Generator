package com.dirinc.number_game;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ArcadeCountdownActivity extends AppCompatActivity {

    private TextView initialCountdown;
    private Handler handler;
    private int seconds = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcade_countdown);

        initialCountdown = (TextView) findViewById(R.id.initialCountdown);
        handler = new Handler();

        if (android.os.Build.VERSION.SDK_INT>=19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

        // We need this to prevent the delay before '3'
        initialCountdown.setText("3");
        startInitialCountdown();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && android.os.Build.VERSION.SDK_INT>=19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    public void startInitialCountdown() {
        handler.postDelayed(new Runnable() {
            public void run() {
                if (seconds == 0) {
                    initialCountdown.setText("GO");
                    setTheme(R.style.dynamic_countdown_green);
                }
                else {
                    initialCountdown.setText(String.valueOf(seconds));
                    switch (seconds) {
                        case 2:
                            setTheme(R.style.dynamic_countdown_yellow);
                            break;
                        case 1:
                            setTheme(R.style.dynamic_countdown_orange);
                            break;
                    }
                }
                seconds--;

                // loop, call again
                if (seconds >= 0)
                    startInitialCountdown();
            }
        }, 1000);
        Log.d("COUNTDOWN", String.valueOf(seconds));

        if (seconds == 0) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    // make dat activity disappear ;)
                    finish();
                    startActivity();
                }
            }, 1750);
        }
    }

    public void startActivity() {
        Intent changeActivities = new Intent(this, ArcadeClickFast.class);
        Log.d("ActivitySwitch", "Switching to Click Fast Activity");
        startActivity(changeActivities);
    }
}