package com.dirinc.randomnumbergenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class EasterEggActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String shrug = "¯" + "\\" + "_(ツ)_/¯";
        super.onCreate(savedInstanceState);
        // This is the easter egg kawaii shrug --->   ¯\_(ツ)_/¯
        setContentView(R.layout.activity_easter_egg);

        Toast.makeText(this, shrug, Toast.LENGTH_LONG).show();

    }

}
