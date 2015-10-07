package com.dirinc.randomnumbergenerator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class NumberActivity extends AppCompatActivity {
    public int stashedRecord;
    private int minimumRandomNumber = 1;
    private int maximumRandomNumber = 1000000;
    private int betterButtonOneCounter;
    private double odds;
    private String unlockedColor;

    public static final String SHARED_PREFS = "shared_preferences";

    public TextView randomNumber;
    public TextView recordNumber;
    public TextView percentOdds;
    public Button doItAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setColors();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        startNumberActivity();
    }

    public void startNumberActivity() {
        boolean isBetterButton0;
        boolean isBetterButton1;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        randomNumber = (TextView) findViewById(R.id.randomNumber);
        recordNumber = (TextView) findViewById(R.id.recordNumber);
        percentOdds = (TextView) findViewById(R.id.percentOdds);
        doItAgain = (Button) findViewById(R.id.doItAgain);

        doItAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                decideGeneration();
            }
        });

        stashedRecord = sharedPreferences.getInt("stashedRecord", maximumRandomNumber);
        betterButtonOneCounter = sharedPreferences.getInt("stashedBetterButtonOneCounter", 0);

        isBetterButton0 = sharedPreferences.getBoolean("better_button0", false);
        isBetterButton1 = sharedPreferences.getBoolean("better_button1", false);

        // Toasts to show user what button is in use
        if(isBetterButton0) {
            Toast.makeText(this, "You're using the standard button", Toast.LENGTH_SHORT).show();
        }
        else if(isBetterButton1) {
            Toast.makeText(this, "You're using better button #1", Toast.LENGTH_SHORT).show();
        }
        // Only here for insurance
        else {
            Toast.makeText(this, "You're using the standard button", Toast.LENGTH_SHORT).show();
        }

        decideGeneration();
    }

    public void decideGeneration() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        boolean isApply0;
        boolean isApply1;

        if(betterButtonOneCounter > 200) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("better_button1", false);
            editor.putBoolean("better_button0", true);
            editor.putInt("stashedBetterButtonOneCounter", 0);
            editor.commit();

            Toast.makeText(this, "Better Button 1 has ran out of clicks!", Toast.LENGTH_SHORT).show();
            betterButtonOneCounter = 0;
        }

        isApply0 = sharedPreferences.getBoolean("better_button0", false);
        isApply1 = sharedPreferences.getBoolean("better_button1", false);

        if(isApply0) {
            generateNumber();
        }
        else if(isApply1) {
            generateBetterNumber1();
        }
        // Only here for insurance
        else {
            generateNumber();
        }
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
            saveInfo();
            decideColor();

            odds = stashedRecord / 1000000.0;
            makeOdds();
        }
        else {
            recordNumber = (TextView) findViewById(R.id.recordNumber);
            if(recordNumber != null) recordNumber.setText(stashedRecordString);
            odds = stashedRecord / 1000000.0;
            makeOdds();
        }
    }

    public void generateBetterNumber1() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        int randomlyGeneratedNumber;
        int betterMaximumRandomNumber = 500000;

        String recordString;
        String randomlyGeneratedNumberString;
        String stashedRecordString;

        betterButtonOneCounter++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedBetterButtonOneCounter", betterButtonOneCounter);
        editor.commit();

        Random rand = new Random();
        randomlyGeneratedNumber = rand.nextInt(betterMaximumRandomNumber - minimumRandomNumber + 1) + minimumRandomNumber;

        randomlyGeneratedNumberString = "" + randomlyGeneratedNumber;

        recordString = "Record: " + randomlyGeneratedNumber;

        stashedRecordString = "Record: " + stashedRecord;

        randomNumber = (TextView) findViewById(R.id.randomNumber);
        if(randomNumber != null) randomNumber.setText(randomlyGeneratedNumberString);


        if(randomlyGeneratedNumber < stashedRecord) {
            recordNumber = (TextView) findViewById(R.id.recordNumber);
            if(recordNumber != null) recordNumber.setText(recordString);
            stashedRecord = randomlyGeneratedNumber;
            saveInfo();
            decideColor();

            odds = stashedRecord / 500000.0;
            makeOdds();
        }
        else {
            recordNumber = (TextView) findViewById(R.id.recordNumber);
            if(recordNumber != null) recordNumber.setText(stashedRecordString);
            odds = stashedRecord / 500000.0;
            makeOdds();
        }
    }

    public void makeOdds() {
        double myOdds = odds * 10.0;
        String percentOddsString = "You have a total " + myOdds +
                "% chance of getting lower than " + stashedRecord;
        percentOdds = (TextView) findViewById(R.id.percentOdds);
        if (percentOdds != null) percentOdds.setText(percentOddsString);
    }

    public String decideColor() {
        if ((stashedRecord <= 10000) && (stashedRecord > 5000)) {
            unlockedColor = "Purple";
            setTheme(R.style.Purple);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 5000) && (stashedRecord > 2500)) {
            unlockedColor = "Blue";
            setTheme(R.style.Blue);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 2500) && (stashedRecord > 750)) {
            unlockedColor = "Teal";
            setTheme(R.style.Teal);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 750) && (stashedRecord > 100)) {
            unlockedColor = "Yellow";
            setTheme(R.style.Yellow);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 100) && (stashedRecord > 50)) {
            unlockedColor = "Orange";
            setTheme(R.style.Orange);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 50) && (stashedRecord > 1)) {
            unlockedColor = "Red";
            setTheme(R.style.Red);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if (stashedRecord == 1) {
            unlockedColor = "Pink";
            setTheme(R.style.Pink);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        }
        return unlockedColor;
    }

    public void setColors() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        unlockedColor = sharedPreferences.getString("color", "");

        switch (unlockedColor) {
            case "Purple":
                setTheme(R.style.Purple);
                break;
            case "Blue":
                setTheme(R.style.Blue);
                break;
            case "Teal":
                setTheme(R.style.Teal);
                break;
            case "Yellow":
                setTheme(R.style.Yellow);
                break;
            case "Orange":
                setTheme(R.style.Orange);
                break;
            case "Red":
                setTheme(R.style.Red);
                break;
            case "Pink":
                setTheme(R.style.Pink);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }
    }

    public void saveInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", stashedRecord);
        editor.putInt("stashedBetterButtonOneCounter", betterButtonOneCounter);
        editor.putString("color", unlockedColor);
        editor.commit();
    }

    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", stashedRecord);
        editor.putInt("stashedBetterButtonOneCounter", betterButtonOneCounter);
        editor.putString("color", unlockedColor);
        editor.commit();

        super.onStop();
    }
}