package com.dirinc.randomnumbergenerator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;

public class ColorChangingActivity extends AppCompatActivity {
    private String unlockedColor;

    public static final String SHARED_PREFS = "shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setColors();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_changing);
        buttons();
    }

    public void buttons() {
        Button purple = (Button) findViewById(R.id.purple);
        Button blue = (Button) findViewById(R.id.blue);
        Button teal = (Button) findViewById(R.id.teal);
        Button yellow = (Button) findViewById(R.id.yellow);
        Button orange = (Button) findViewById(R.id.orange);
        Button red = (Button) findViewById(R.id.red);
        Button pink = (Button) findViewById(R.id.pink);

        purple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Execute on click
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                unlockedColor = "Purple";
                saveInfo();
                setColors();
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Execute on click
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                unlockedColor = "Blue";
                saveInfo();
                setColors();
            }
        });

        teal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Execute on click
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                unlockedColor = "Teal";
                saveInfo();
                setColors();
            }
        });

        yellow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Execute on click
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                unlockedColor = "Yellow";
                saveInfo();
                setColors();
            }
        });

        orange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Execute on click
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                unlockedColor = "Orange";
                saveInfo();
                setColors();
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Execute on click
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                unlockedColor = "Red";
                saveInfo();
                setColors();
            }
        });

        pink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Execute on click
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                unlockedColor = "Pink";
                saveInfo();
                setColors();
            }
        });
    }

    public void saveInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("color", unlockedColor);
        editor.commit();
    }

    public void setColors() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        unlockedColor = sharedPreferences.getString("color", "");

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

    @Override
    protected void onResume() {
        setColors();
        super.onResume();
        setContentView(R.layout.activity_main);
    }
}
