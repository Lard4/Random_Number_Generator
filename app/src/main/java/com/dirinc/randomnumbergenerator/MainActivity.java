package com.dirinc.randomnumbergenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // We are on the MainActivity here.
        setContentView(R.layout.activity_main);

        // This is the onClickListener for the "Generate a random number button"
        final Button button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action upon user click
                    Intent activityChangeIntent = new Intent(MainActivity.this, NumberActivity.class);
                    // Change activities :)
                    MainActivity.this.startActivity(activityChangeIntent);
                }
            });

            // If the user longClicks the same button, they will be taken to AboutActivity
            button.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    // Perform action upon user click
                    Intent activityChangeIntent = new Intent(MainActivity.this, AboutActivity.class);
                    // Change activities
                    MainActivity.this.startActivity(activityChangeIntent);
                    return true;
                }
            });
    }
}
