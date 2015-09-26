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
    public int minimumRandomNumber = 1;
    public int maximumRandomNumber = 1000000;

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
                generateNumber();
            }
        });

        stashedRecord = sharedPreferences.getInt("stashedRecord", maximumRandomNumber);
        generateNumber();
    }

    public void generateNumber() {
        int randomlyGeneratedNumber;
        String recordString;
        String randomlyGeneratedNumberString;
        String stashedRecordString;

        Random rand = new Random();
        randomlyGeneratedNumber = rand.nextInt(maximumRandomNumber - minimumRandomNumber + 1) + minimumRandomNumber;

        randomlyGeneratedNumberString = "" + randomlyGeneratedNumber;

        recordString = "Record: " + randomlyGeneratedNumber;

        stashedRecordString = "Record: " + stashedRecord;

        randomNumber = (TextView) findViewById(R.id.randomNumber);
        if(randomNumber != null) randomNumber.setText(randomlyGeneratedNumberString);

        if(randomlyGeneratedNumber < stashedRecord) {
            recordNumber = (TextView) findViewById(R.id.recordNumber);
            if(recordNumber != null) recordNumber.setText(recordString);
            stashedRecord = randomlyGeneratedNumber;
        }
        else {
            recordNumber = (TextView) findViewById(R.id.recordNumber);
            if(recordNumber != null) recordNumber.setText(stashedRecordString);
        }
    }

    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", stashedRecord);
        editor.commit();

        super.onStop();
    }
}