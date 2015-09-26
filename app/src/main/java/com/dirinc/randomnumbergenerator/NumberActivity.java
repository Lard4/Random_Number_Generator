package com.dirinc.randomnumbergenerator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.util.Log;
import java.util.Random;


public class NumberActivity extends AppCompatActivity {
    public int newRecord;
    public int stashedRecord;
    int record;

    public static final String SHARED_PREFS = "shared_preferences";

    public TextView randomNumber;
    public TextView recordNumber;
    public Button doItAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        randomNumber = (TextView) findViewById(R.id.randomNumber);
        recordNumber = (TextView) findViewById(R.id.recordNumber);
        doItAgain = (Button) findViewById(R.id.doItAgain);

        record = 1000000;

        Integer sr = sharedPreferences.getInt("stashedRecord", stashedRecord);

        record = sr;

        generateNumber();
    }

    public void generateNumber() {
        int max;
        int min;
        int randomlyGeneratedNumber;
        String recordString;
        String randomlyGeneratedNumberString;
        String stashedRecordString;

        min = 1;
        max = 1000000;

        Random rand = new Random();
        randomlyGeneratedNumber = rand.nextInt(max - min + 1) + min;

        randomlyGeneratedNumberString = "" + randomlyGeneratedNumber;

        recordString = "Record: " + randomlyGeneratedNumber;

        stashedRecordString = "Record: " + record;

        randomNumber = (TextView) findViewById(R.id.randomNumber);
        if(randomNumber != null) randomNumber.setText(randomlyGeneratedNumberString);

        if(randomlyGeneratedNumber < record) {
            recordNumber = (TextView) findViewById(R.id.recordNumber);
            if(recordNumber != null) recordNumber.setText(recordString);
            record = randomlyGeneratedNumber;
        }
        else {
            recordNumber = (TextView) findViewById(R.id.recordNumber);
            if(recordNumber != null) recordNumber.setText(stashedRecordString);
        }

        doItAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Execute this on click
                emptyMethod();
            }
        });
    }

    public void emptyMethod() {
        generateNumber();
    }

    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        super.onStop();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", record);
        editor.commit();
    }
}