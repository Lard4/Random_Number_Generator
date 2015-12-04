package com.dirinc.number_game;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ArcadeClickFast extends AppCompatActivity implements View.OnTouchListener{

    private int totalClicks = 0;

    private long timeInMilliseconds = 15000;
    private long totalMilliseconds = 0;

    private Handler handler;
    private TextView time_remaining, clicks;
    private Button button_click_fast;

    private static final String SHARED_PREFS = "shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setColors();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcade_click_fast);

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

        handler = new Handler();
        initializeButtons();
        startTimer();
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.button_click_fast:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    button_click_fast.setBackgroundColor(getResources().getColor(R.color.button_background_pressed));
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    button_click_fast.setBackgroundColor(getResources().getColor(R.color.button_background));
                    totalClicks++;
                    clicks.setText(String.valueOf(totalClicks));
                    return true;
                }
                return false;

            default:
                return false;
        }
    }


    public void startTimer() {
        handler.postDelayed(new Runnable() {
            public void run() {
                time_remaining.setText(String.valueOf(timeInMilliseconds - totalMilliseconds));

                totalMilliseconds--;

                // loop, call again
                if (totalMilliseconds >= 0)
                    startTimer();
            }
        }, 1);
    }

    public void initializeButtons() {
        time_remaining = (TextView) findViewById(R.id.time_remaining);
        clicks = (TextView) findViewById(R.id.clicks);

        button_click_fast = (Button) findViewById(R.id.button_click_fast);
        button_click_fast.setOnTouchListener(this);
    }

    public void setColors() {
        Log.d("UI", "Setting Theme Color");
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        String unlockedColor = sharedPreferences.getString("color", "");

        switch (unlockedColor) {
            case "Purple":
                setTheme(R.style.Purple);
                break;
            case "Blue":
                setTheme(R.style.Blue);
                break;
            case "Teal":
                setTheme(R.style.Teal);
                break;
            case "Yellow":
                setTheme(R.style.Yellow);
                break;
            case "Orange":
                setTheme(R.style.Orange);
                break;
            case "Red":
                setTheme(R.style.Red);
                break;
            case "Pink":
                setTheme(R.style.Pink);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }
    }
}