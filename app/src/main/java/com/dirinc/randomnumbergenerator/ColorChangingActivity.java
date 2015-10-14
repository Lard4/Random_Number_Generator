package com.dirinc.randomnumbergenerator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ColorChangingActivity extends AppCompatActivity {
    private String unlockedColor;
    private String bestColor;
    private boolean unlockedPurple;
    private boolean unlockedBlue;
    private boolean unlockedTeal;
    private boolean unlockedYellow;
    private boolean unlockedOrange;
    private boolean unlockedRed;
    private boolean unlockedPink;
    private NumberActivity purpleFinder = new NumberActivity();

    private static final String SHARED_PREFS = "shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setColors();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_changing);
        hasTheseColors();
        buttons();
    }

    public void hasTheseColors() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        unlockedColor = sharedPreferences.getString("color", "");

        switch (unlockedColor) {
            case "Purple":
                unlockedPurple = true;
                bestColor = "Purple";
                break;
            case "Blue":
                unlockedPurple = true;
                unlockedBlue = true;
                bestColor = "Blue";
                break;
            case "Teal":
                unlockedPurple = true;
                unlockedBlue = true;
                unlockedTeal = true;
                bestColor = "Teal";
                break;
            case "Yellow":
                unlockedPurple = true;
                unlockedBlue = true;
                unlockedTeal = true;
                unlockedYellow = true;
                bestColor = "Yellow";
                break;
            case "Orange":
                unlockedPurple = true;
                unlockedBlue = true;
                unlockedTeal = true;
                unlockedYellow = true;
                unlockedOrange = true;
                bestColor = "Orange";
                break;
            case "Red":
                unlockedPurple = true;
                unlockedBlue = true;
                unlockedTeal = true;
                unlockedYellow = true;
                unlockedOrange = true;
                unlockedRed = true;
                bestColor = "Red";
                break;
            case "Pink":
                unlockedPurple = true;
                unlockedBlue = true;
                unlockedTeal = true;
                unlockedYellow = true;
                unlockedOrange = true;
                unlockedRed = true;
                unlockedPink = true;
                bestColor = "Pink";
                break;
        }
    }

    public void buttons() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        unlockedColor = sharedPreferences.getString("bestColor", "");

        Button purple = (Button) findViewById(R.id.purple);
        Button blue = (Button) findViewById(R.id.blue);
        Button teal = (Button) findViewById(R.id.teal);
        Button yellow = (Button) findViewById(R.id.yellow);
        Button orange = (Button) findViewById(R.id.orange);
        Button red = (Button) findViewById(R.id.red);
        Button pink = (Button) findViewById(R.id.pink);

        if((unlockedPurple) || ((bestColor != null) && (bestColor.equals("Purple")))) {
            purple.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Execute on click
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    unlockedColor = "Purple";
                    saveInfo();
                    setColors();
                    setContentView(R.layout.activity_color_changing);
                }
            });
        }

        if((unlockedBlue) || ((bestColor != null) && (bestColor.equals("Blue")))) {
            blue.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Execute on click
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    unlockedColor = "Blue";
                    saveInfo();
                    setColors();
                    setContentView(R.layout.activity_color_changing);
                }
            });
        }

        if((unlockedTeal) || ((bestColor != null) && (bestColor.equals("Teal")))) {
            teal.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Execute on click
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    unlockedColor = "Teal";
                    saveInfo();
                    setColors();
                    setContentView(R.layout.activity_color_changing);
                }
            });
        }

        if((unlockedYellow) || ((bestColor != null) && (bestColor.equals("Yellow")))) {
            yellow.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Execute on click
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    unlockedColor = "Yellow";
                    saveInfo();
                    setColors();
                    setContentView(R.layout.activity_color_changing);
                }
            });
        }

        if((unlockedOrange) || ((bestColor != null) && (bestColor.equals("Orange")))) {
            orange.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Execute on click
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    unlockedColor = "Orange";
                    saveInfo();
                    setColors();
                    setContentView(R.layout.activity_color_changing);
                }
            });
        }

        if((unlockedRed) || ((bestColor != null) && (bestColor.equals("Red")))) {
            red.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Execute on click
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    unlockedColor = "Red";
                    saveInfo();
                    setColors();
                    setContentView(R.layout.activity_color_changing);
                }
            });
        }

        if((unlockedPink) || ((bestColor != null) && (bestColor.equals("Pink")))) {
            pink.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Execute on click
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    unlockedColor = "Pink";
                    saveInfo();
                    setColors();
                    setContentView(R.layout.activity_color_changing);
                }
            });
        }
    }

    public void saveInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("color", unlockedColor);
        editor.putString("bestColor", bestColor);
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
}
