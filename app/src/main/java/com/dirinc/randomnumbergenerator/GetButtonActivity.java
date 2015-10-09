package com.dirinc.randomnumbergenerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GetButtonActivity extends AppCompatActivity {
    private Boolean isApply1;
    private Boolean isApply0;
    private String whichButton;

    private static final String SHARED_PREFS = "shared_preferences";
    private final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

    public GetButtonActivity() {
        isApply1 = false;
        isApply0 = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setColors();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_button);

        Button better_button_1 = (Button) findViewById(R.id.better_button_1);
        Button better_button_0 = (Button) findViewById(R.id.better_button_0);
        Button button_change_style = (Button) findViewById(R.id.button_change_style);


        better_button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                isApply0 = false;
                isApply1 = true;
                setButton();
            }
        });

        better_button_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                isApply0 = true;
                isApply1 = false;
                setButton();
            }
        });

        button_change_style.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                startColorChangingActivity();
            }
        });
    }

    public void setButton() {
        if(isApply1) {
            isApply1 = true;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // There shall not be more than one that is tru
            editor.putBoolean("better_button0", isApply0);
            editor.putBoolean("better_button1", isApply1);
            editor.commit();

            Toast.makeText(getApplicationContext(), "Better Button 1 Applied!", Toast.LENGTH_SHORT).show();
        }
        else if(isApply0) {
            isApply0 = true;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // There shall not be more than one that is tru
            editor.putBoolean("better_button0", isApply0);
            editor.putBoolean("better_button1", isApply1);
            editor.commit();

            Toast.makeText(getApplicationContext(), "Standard Button Applied!", Toast.LENGTH_SHORT).show();
        }
    }

    public String getButton() {
        if(isApply1) {
            whichButton = "isApply1";
            return whichButton;
        }
        else {
            whichButton = "isApply0";
            return whichButton;
        }
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

    public void startColorChangingActivity() {
        Intent changeActivities = new Intent(this, ColorChangingActivity.class);
        startActivity(changeActivities);
    }
}
