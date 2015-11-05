package com.dirinc.randomnumbergenerator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GetButtonActivity extends AppCompatActivity implements View.OnTouchListener {
    private Boolean isApply1;
    private Boolean isApply0;

    private Toast toast;
    private Button better_button_1, better_button_0;

    private static final String SHARED_PREFS = "shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setColors();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_button);

        isApply1 = false;
        isApply0 = false;

        initializeButons();
    }

    public void initializeButons() {
        better_button_1 = (Button) findViewById(R.id.better_button_1);
        better_button_1.setOnTouchListener(this);

        better_button_0 = (Button) findViewById(R.id.better_button_0);
        better_button_0.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.better_button_1:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    better_button_1.setBackgroundColor(getResources().getColor(R.color.button_background_pressed));
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    isApply0 = false;
                    isApply1 = true;
                    setButton();

                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP) {
                    better_button_1.setBackgroundColor(getResources().getColor(R.color.button_background));
                }

                return false;

            case R.id.better_button_0:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    better_button_0.setBackgroundColor(getResources().getColor(R.color.button_background_pressed));
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    isApply0 = true;
                    isApply1 = false;
                    setButton();

                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP) {
                    better_button_0.setBackgroundColor(getResources().getColor(R.color.button_background));
                }

                return false;

            default:
                return false;
        }
    }

    public void setButton() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        if (isApply1) {
            isApply1 = true;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // There shall not be more than one that is tru
            editor.putBoolean("better_button0", isApply0);
            editor.putBoolean("better_button1", isApply1);
            editor.apply();

            // Hide any other previous toasts to avoid constructing toasts
            if(toast != null) toast.cancel();
            toast = Toast.makeText(getApplicationContext(), "Better Button 1 Applied!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (isApply0) {
            isApply0 = true;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // There shall not be more than one that is tru
            editor.putBoolean("better_button0", isApply0);
            editor.putBoolean("better_button1", isApply1);
            editor.apply();

            // Hide any other previous toasts to avoid constructing toasts
            if(toast != null) toast.cancel();
            toast = Toast.makeText(getApplicationContext(), "Standard Button Applied!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public String getButton() {
        String whichButton;
        if (isApply1) {
            whichButton = "isApply1";
            return whichButton;
        } else {
            whichButton = "isApply0";
            return whichButton;
        }
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

    @Override
    protected void onPause() {
        super.onPause();
        if(toast != null) toast.cancel();
    }
}
