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
    public int record;
    public String randomNumberText;

    public NumberActivity() {
        record = 1000000;
    }


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
        String randomNumberText;
        String recordText;

        min = 1;
        max = 1000000;

        Random rand = new Random();
        newNumber = rand.nextInt(max - min + 1) + min;

        randomNumber = newNumber;

        randomNumberText = "" + randomNumber;

        setContentView(R.layout.activity_number);
        TextView tv = (TextView) findViewById(R.id.rng);
        if(tv != null) tv.setText(randomNumberText);

        if(randomNumber < record) {
            record = randomNumber;
            recordText = "" + record;
            setContentView(R.layout.activity_number);
            TextView tv2 = (TextView) findViewById(R.id.recordLow);
            if(tv2 != null) tv2.setText(recordText);
        }


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
