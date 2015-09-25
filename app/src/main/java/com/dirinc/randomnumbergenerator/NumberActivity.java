package com.dirinc.randomnumbergenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class NumberActivity extends AppCompatActivity {

    // Just some fields needed
    public int randomNumber;
    public int record;
    boolean hasRecord;


    public NumberActivity() {
        // Take note that record is not defined here so that the user keeps their
        // records from previous sessions.
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        // Start the random number generation process
        generateNumber();
    }

    public void generateNumber() {
        // Local fields needed
        int max;
        int min;
        int newNumber;
        String randomNumberText;
        String recordText;

        // Define max and min values for random number generation
        min = 1;
        max = 1000000;

        /**
         * IT IS IMPORTANT THAT
         * setContentView(R.layout.activity_number);
         * CANNOT, I REPEAT CAN NOT EXIST IN THIS METHOD.
         *      This will not allow the record as well as the
         *      random number to exist in the NumberActivity.
         */

        // Get a random number between max and min
        Random rand = new Random();
        newNumber = rand.nextInt(max - min + 1) + min;

        // The randomly generated number is now called randomNumber
        randomNumber = newNumber;

        // randomNumberText is the string version of randomNumber
        randomNumberText = "" + randomNumber;

        // TextView for the randomly generated number
        TextView tv = (TextView) findViewById(R.id.rng);
        if(tv != null) tv.setText(randomNumberText);

        // If our randomNumber sets a new (lowest) record, then
        if(randomNumber < record) {
            // Execute this to set the new record to the randomNumber
            record = randomNumber;
            // String version of the record number
            recordText = "Record: " + record;

            // TextView for our new record
            TextView tv2 = (TextView) findViewById(R.id.recordLow);
            if(tv2 != null) tv2.setText(recordText);

            // The user has a record
            hasRecord = true;
        }

        // "Do it again" button onClickListner
        final Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                emptyMethod();
            }
        });
    }

    // Method that calls generateNumber
    public void emptyMethod(){
        generateNumber();
    }
}
