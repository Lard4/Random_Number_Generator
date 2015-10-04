package com.dirinc.randomnumbergenerator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GetButtonActivity extends AppCompatActivity {
    private Boolean isApply1;
    private Boolean isApply0;
    public String thisButton;

    public static final String SHARED_PREFS = "shared_preferences";

    public GetButtonActivity() {
        isApply1 = false;
        isApply0 = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_button);

        final Button better_button_1 = (Button) findViewById(R.id.better_button_1);
        final Button better_button_0 = (Button) findViewById(R.id.better_button_0);

        better_button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isApply0 = false;
                isApply1 = true;
                setButton();
            }
        });

        better_button_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isApply0 = true;
                isApply1 = false;
                setButton();
            }
        });
    }

    public void setButton() {
        if(isApply1) {
            isApply1 = true;
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // There shall not be more than one that is tru
            editor.putBoolean("better_button0", isApply0);
            editor.putBoolean("better_button1", isApply1);
            editor.commit();

            Toast.makeText(this, "Better Button 1 Applied!", Toast.LENGTH_SHORT).show();
        }
        else if(isApply0) {
            isApply0 = true;
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // There shall not be more than one that is tru
            editor.putBoolean("better_button0", isApply0);
            editor.putBoolean("better_button1", isApply1);
            editor.commit();

            Toast.makeText(this, "Standard Button Applied!", Toast.LENGTH_SHORT).show();
        }
    }

    public String getButton() {
        if(isApply1) {
            thisButton = "isApply1";
            return thisButton;
        }
        else {
            thisButton = "isApply0";
            return thisButton;
        }
    }
}
