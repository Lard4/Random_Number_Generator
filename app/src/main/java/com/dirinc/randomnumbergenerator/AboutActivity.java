package com.dirinc.randomnumbergenerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {
    // Variable for how many times the user clicked Version number
    private int clicked;

    private static final String SHARED_PREFS = "shared_preferences";
    private final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

    public AboutActivity() {
        // Default is always 0 when the activity is launched for consistency
        clicked = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setColors();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        // This is the "hidden" button that is the version number
        Button aboutVersion = (Button) findViewById(R.id.aboutVersion);

        aboutVersion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click. Our action is to add 1 to the field clicked
                clicked ++;
                // When the user clicks the button 5 times...
                if (clicked == 5) {
                    // Switch on over to the EasterEggActivity ¯\_(ツ)_/¯
                    startEasterEggActivity();
                }
            }
        });
    }

    public void setColors() {
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

    public void startEasterEggActivity() {
        Intent changeActivities = new Intent(this, EasterEggActivity.class);
        startActivity(changeActivities);
        finish();
    }
}
