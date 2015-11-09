package com.dirinc.number_game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity implements View.OnTouchListener {
    private int counter = 0;

    private TextView about1, about2;
    private Button aboutVersion, back;
    private static final String SHARED_PREFS = "shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setColors();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if (android.os.Build.VERSION.SDK_INT>=19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

        initializeButtons();
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

    public void initializeButtons() {
        aboutVersion = (Button) findViewById(R.id.aboutVersion); // 'Hidden' button
        aboutVersion.setOnTouchListener(this);

        back = (Button) findViewById(R.id.back);
        back.setOnTouchListener(this);

        about1 = (TextView) findViewById(R.id.about1);
        about2 = (TextView) findViewById(R.id.about2);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {

            case R.id.aboutVersion:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    counter++;
                    if (counter == 5) startEasterEggActivity();
                    return true;
                }
                return false;

            case R.id.back:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    Intent changeActivities = new Intent(this, MainActivity.class);
                    Log.d("ActivitySwitch", "Switching to Main Activity");
                    startActivity(changeActivities);
                    return true;
                }
                return false;

            default:
                return false;
        }
    }

    public void setColors() {
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
                setTextColor("black");
                break;
            case "Orange":
                setTheme(R.style.Orange);
                setTextColor("white");
                break;
            case "Red":
                setTheme(R.style.Red);
                setTextColor("white");
                break;
            case "Pink":
                setTheme(R.style.Pink);
                setTextColor("white");
                break;
            default:
                setTheme(R.style.AppTheme);
                setTextColor("white");
                break;
        }
    }

    public void setTextColor(String c) {
        String color = c.toLowerCase();
        int newColor = 0;

        switch (color) {
            case "black":
                newColor = (this.getResources().getColor(android.R.color.black));
                break;

            case "white":
                newColor = (this.getResources().getColor(android.R.color.white));
        }
        if(about1 != null) about1.setTextColor(newColor);
        if(about2 != null) about2.setTextColor(newColor);
        if(aboutVersion != null) aboutVersion.setTextColor(newColor);
    }

    public void startEasterEggActivity() {
        Intent changeActivities = new Intent(this, EasterEggActivity.class);
        startActivity(changeActivities);
        finish();
    }
}
