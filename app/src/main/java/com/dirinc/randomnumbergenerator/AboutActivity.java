package com.dirinc.randomnumbergenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    // Field for how many times the user clicked Version number
    private int clicked;

    public AboutActivity() {
        // Default is always 0 when the activity is launched for consistency
        clicked = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This is the AboutActivity, mmmmm?
        setContentView(R.layout.activity_about);

        // Here is the "hidden" button that is the version number
        final Button button = (Button) findViewById(R.id.aboutversion);
            // Can we get an onClickListener??
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click. Our action is to add 1 to the field clicked
                    clicked ++;
                    // When the user clicks the button 5 times...
                     if (clicked == 5) {
                            // Switch on over to the EasterEggActivity ¯\_(ツ)_/¯
                            Intent activityChangeIntent = new Intent(AboutActivity.this, EasterEggActivity.class);
                            AboutActivity.this.startActivity(activityChangeIntent);
                     }
                }
            });
    }
}
