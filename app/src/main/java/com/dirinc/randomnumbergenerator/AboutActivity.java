package com.dirinc.randomnumbergenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    private int clicked;

    public AboutActivity() {
        clicked = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final Button button = (Button) findViewById(R.id.aboutversion);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                clicked = clicked + 1;
                 if (clicked == 5) {
                        Intent activityChangeIntent = new Intent(AboutActivity.this, EasterEggActivity.class);
                        AboutActivity.this.startActivity(activityChangeIntent);
                 }
            }
        });

    }
}
