package com.dirinc.randomnumbergenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class NumberActivity extends AppCompatActivity {

    public int randomNumber;
    public int randomNumberInt;
    public String randomNumberText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        generateNumber();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_number, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void generateNumber() {
        int max;
        int min;
        int newNumber;


        min = 1;
        max = 999999999;

        Random rn = new Random();
        newNumber = rn.nextInt(max - min + 1) + min;

        randomNumber = newNumber;

        String randomNumberText;
        randomNumberText = "" + randomNumber;

        setContentView(R.layout.activity_number);
        TextView tv = (TextView) findViewById(R.id.rng);
        if(tv != null) tv.setText(randomNumberText);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                emptyMethod();
            }
        });

    }

    public void emptyMethod(){
        generateNumber();
    }
}
