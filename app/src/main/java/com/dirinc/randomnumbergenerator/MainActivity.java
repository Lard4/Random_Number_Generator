package com.dirinc.randomnumbergenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // We are on the MainActivity here.
        setContentView(R.layout.activity_main);

        //Vibrator vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        Button number_button = (Button) findViewById(R.id.number_button);
        Button button_more = (Button) findViewById(R.id.button_more);

        // This is the onClickListener for the "Generate a random number button"
        number_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action upon user click
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Intent activityChangeIntent = new Intent(MainActivity.this, NumberActivity.class);
                // Change activities :)
                MainActivity.this.startActivity(activityChangeIntent);
            }
        });

        // If the user longClicks the same button, they will be taken to AboutActivity
        number_button.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Intent activityChangeIntent = new Intent(MainActivity.this, AboutActivity.class);
                MainActivity.this.startActivity(activityChangeIntent);
                return true;
            }
        });

        button_more.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Intent activityChangeIntent = new Intent(MainActivity.this, GetButtonActivity.class);
                MainActivity.this.startActivity(activityChangeIntent);
            }
        });
    }
}
