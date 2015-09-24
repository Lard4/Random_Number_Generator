package com.dirinc.randomnumbergenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class NumberActivity extends AppCompatActivity {

    public int randomNumber;
    public int record;
    boolean hasRecord;

    public NumberActivity() {
        record = 1000000;
        hasRecord = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        generateNumber();
    }

    public void generateNumber() {
        int max;
        int min;
        int newNumber;
        String randomNumberText;
        String recordText;

        min = 1;
        max = 1000000;
        recordText = "0";

        if(hasRecord = true){
            setContentView(R.layout.activity_number);
            TextView tv3 = (TextView) findViewById(R.id.recordLow);
            if(tv3 != null) tv3.setText(recordText);
        }

        Random rand = new Random();
        newNumber = rand.nextInt(max - min + 1) + min;

        randomNumber = newNumber;

        randomNumberText = "" + randomNumber;

        setContentView(R.layout.activity_number);
        TextView tv = (TextView) findViewById(R.id.rng);
        if(tv != null) tv.setText(randomNumberText);

        if(randomNumber < record) {
            record = randomNumber;
            recordText = "Record: " + record;
            setContentView(R.layout.activity_number);
            TextView tv2 = (TextView) findViewById(R.id.recordLow);
            if(tv2 != null) tv2.setText(recordText);
            hasRecord = true;
        }

        final Button button = (Button) findViewById(R.id.button2);
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
