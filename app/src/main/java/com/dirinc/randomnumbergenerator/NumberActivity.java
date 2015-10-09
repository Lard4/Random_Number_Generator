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
    private int stashedRecord;
    private int minimumRandomNumber = 1;
    private int maximumRandomNumber = 1000000;
    private int betterButtonOneCounter;
    private double odds;
    private String unlockedColor;

    private boolean hasPurple;
    private boolean hasBlue;
    private boolean hasTeal;
    private boolean hasYellow;
    private boolean hasOrange;
    private boolean hasRed;
    private boolean hasPink;

    private static final String SHARED_PREFS = "shared_preferences";
    private final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

    private TextView randomNumber;
    private TextView recordNumber;
    private TextView percentOdds;
    private Button doItAgain;

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
            Toast.makeText(getApplicationContext(), "You're using the standard button", Toast.LENGTH_SHORT).show();
        }
        else if(isBetterButton1) {
            Toast.makeText(getApplicationContext(), "You're using better button #1", Toast.LENGTH_SHORT).show();
        }
        // Only here for insurance
        else {
            Toast.makeText(getApplicationContext(), "You're using the standard button", Toast.LENGTH_SHORT).show();
        }

        decideGeneration();
    }

    public void decideGeneration() {
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
            hasPurple = true;
            setTheme(R.style.Purple);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 5000) && (stashedRecord > 2500)) {
            unlockedColor = "Blue";
            hasPurple = true;
            hasBlue = true;
            setTheme(R.style.Blue);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 2500) && (stashedRecord > 750)) {
            unlockedColor = "Teal";
            hasPurple = true;
            hasBlue = true;
            hasTeal = true;
            setTheme(R.style.Teal);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 750) && (stashedRecord > 100)) {
            unlockedColor = "Yellow";
            hasPurple = true;
            hasBlue = true;
            hasTeal = true;
            hasYellow = true;
            setTheme(R.style.Yellow);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 100) && (stashedRecord > 50)) {
            unlockedColor = "Orange";
            hasPurple = true;
            hasBlue = true;
            hasTeal = true;
            hasYellow = true;
            hasOrange = true;
            setTheme(R.style.Orange);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 50) && (stashedRecord > 1)) {
            unlockedColor = "Red";
            hasPurple = true;
            hasBlue = true;
            hasTeal = true;
            hasYellow = true;
            hasOrange = true;
            hasRed = true;
            setTheme(R.style.Red);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if (stashedRecord == 1) {
            unlockedColor = "Pink";
            hasPurple = true;
            hasBlue = true;
            hasTeal = true;
            hasYellow = true;
            hasOrange = true;
            hasRed = true;
            hasPink = true;
            setTheme(R.style.Pink);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        }
        return unlockedColor;
    }

    public void setColors() {
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

    public boolean getPurple() {
        return hasPurple;
    }
    public boolean getBlue() {
        return hasBlue;
    }
    public boolean getTeal() {
        return hasTeal;
    }
    public boolean getYellow() {
        return hasYellow;
    }
    public boolean getOrange() {
        return hasOrange;
    }
    public boolean getRed() {
        return hasRed;
    }
    public boolean getPink() {
        return hasPink;
    }

    public void saveInfo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", stashedRecord);
        editor.putInt("stashedBetterButtonOneCounter", betterButtonOneCounter);
        editor.putString("color", unlockedColor);
        editor.commit();
    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", stashedRecord);
        editor.putInt("stashedBetterButtonOneCounter", betterButtonOneCounter);
        editor.putString("color", unlockedColor);
        editor.commit();

        super.onStop();
    }
}