package com.dirinc.randomnumbergenerator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import java.util.Random;


public class NumberActivity extends AppCompatActivity {
    public int stashedRecord;
    public int record;
    public boolean userHasRecord;

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

        doItAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Execute this on click
                generateNumber();
            }
        });

        record = 1000000;

        stashedRecord = sharedPreferences.getInt("stashedRecord", 0);

        userHasRecord = sharedPreferences.getBoolean("userHasRecord", false);

        if(userHasRecord == true) {
            generateNumber();
        }
        else {
            generateFirstNumber();
        }
    }

    public void generateFirstNumber() {
        int min = 1;
        int max = 1000000;
        int randomlyGeneratedNumber;
        String recordString;
        String randomlyGeneratedNumberString;
        String stashedRecordString;

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
            stashedRecord = randomlyGeneratedNumber;
            savePrefs();
        }
        else {
            recordNumber = (TextView) findViewById(R.id.recordNumber);
            if(recordNumber != null) recordNumber.setText(stashedRecordString);
        }
    }

    public void generateNumber() {
        int min = 1;
        int max = 1000000;
        int randomlyGeneratedNumber;
        String recordString;
        String randomlyGeneratedNumberString;
        String stashedRecordString;

        Random rand = new Random();
        randomlyGeneratedNumber = rand.nextInt(max - min + 1) + min;

        randomlyGeneratedNumberString = "" + randomlyGeneratedNumber;

        recordString = "Record: " + randomlyGeneratedNumber;

        stashedRecordString = "Record: " + stashedRecord;

        randomNumber = (TextView) findViewById(R.id.randomNumber);
        if(randomNumber != null) randomNumber.setText(randomlyGeneratedNumberString);

        if(randomlyGeneratedNumber < stashedRecord) {
            recordNumber = (TextView) findViewById(R.id.recordNumber);
            if(recordNumber != null) recordNumber.setText(recordString);
            stashedRecord = randomlyGeneratedNumber;
            savePrefs();
        }
        else {
            recordNumber = (TextView) findViewById(R.id.recordNumber);
            if(recordNumber != null) recordNumber.setText(stashedRecordString);
        }
    }

    public void savePrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", stashedRecord);
        editor.putBoolean("userHasRecord", true);
        editor.commit();
    }

    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", stashedRecord);
        editor.putBoolean("userHasRecord", true);
        editor.commit();

        super.onStop();
    }
}