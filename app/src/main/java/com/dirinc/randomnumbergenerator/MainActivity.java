package com.dirinc.randomnumbergenerator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private static final String SHARED_PREFS = "shared_preferences";
    // TODO: Sign into Google Play Games

    @Override
    protected void onCreate(Bundle icicle) {
        setColors();
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        setButtons();
    }

    public void setButtons() {
        final Button number_button = (Button) findViewById(R.id.number_button);
        final Button button_more = (Button) findViewById(R.id.button_more);

        number_button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    startNumberActivity();
                    return true;
                }
                return false;
            }
        });

        number_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                startAboutActivity();
                return true;
            }
        });

        button_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                startGetButtonActivity();
            }
        });
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

    public void startNumberActivity() {
        Intent changeActivities = new Intent(this, NumberActivity.class);
        startActivity(changeActivities);
    }

    public void startAboutActivity() {
        Intent changeActivities = new Intent(this, AboutActivity.class);
        startActivity(changeActivities);
    }

    public void startGetButtonActivity() {
        Intent changeActivities = new Intent(this, GetButtonActivity.class);
        startActivity(changeActivities);
    }

    @Override
    protected void onResume() {
        setColors();
        super.onResume();
        setContentView(R.layout.activity_main);
        setButtons();
    }
}