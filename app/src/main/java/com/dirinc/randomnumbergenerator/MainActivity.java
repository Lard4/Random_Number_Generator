package com.dirinc.randomnumbergenerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREFS = "shared_preferences";

    @Override
    protected void onCreate(Bundle icicle) {
        setColors();
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        final Button number_button = (Button) findViewById(R.id.number_button);
        final Button button_more = (Button) findViewById(R.id.button_more);

        number_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                startNumberActivity();
            }
        });

        number_button.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                startAboutActivity();
                return true;
            }
        });

        button_more.setOnClickListener(new View.OnClickListener() {
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
        finish();
    }

    public void startAboutActivity() {
        Intent changeActivities = new Intent(this, AboutActivity.class);
        startActivity(changeActivities);
        finish();
    }

    public void startGetButtonActivity() {
        Intent changeActivities = new Intent(this, GetButtonActivity.class);
        startActivity(changeActivities);
        finish();
    }

    @Override
    protected void onResume() {
        setColors();
        super.onResume();
        setContentView(R.layout.activity_main);
    }
}