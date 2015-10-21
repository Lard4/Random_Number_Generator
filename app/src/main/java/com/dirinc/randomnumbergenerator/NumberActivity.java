package com.dirinc.randomnumbergenerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.*;
import com.google.android.gms.common.api.*;
import com.google.android.gms.games.*;
import com.google.example.games.basegameutils.BaseGameUtils;

import java.util.Random;

public class NumberActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private int stashedRecord;
    private int minimumRandomNumber = 1;
    private int betterButtonOneCounter;
    private boolean firstTimePurpley = true;
    private boolean firstTimeYellow = true;

    private String unlockedColor;

    private TextView randomNumber;
    private TextView recordNumber;
    private TextView percentOdds;

    private static final String SHARED_PREFS = "shared_preferences";

    // Google Play API stuff
    private GoogleApiClient mGoogleApiClient;

    private boolean mResolvingConnectionFailure = false;
    private boolean mSignInClicked = false;
    private boolean mAutoStartSignInFlow = true;

    // Random ass number
    private static final int RC_UNUSED = 5001;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setColors();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
        signIn();

        startNumberActivity();
    }

    public void startNumberActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        boolean isBetterButton0;
        boolean isBetterButton1;

        randomNumber = (TextView) findViewById(R.id.randomNumber);
        recordNumber = (TextView) findViewById(R.id.recordNumber);
        percentOdds = (TextView) findViewById(R.id.percentOdds);
        Button doItAgain = (Button) findViewById(R.id.doItAgain);

        doItAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                decideGeneration();
            }
        });

        stashedRecord = sharedPreferences.getInt("stashedRecord", 1000000);
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
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        boolean isApply0;
        boolean isApply1;

        if(betterButtonOneCounter > 200) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("better_button1", false);
            editor.putBoolean("better_button0", true);
            editor.putInt("stashedBetterButtonOneCounter", 0);
            editor.commit();

            Toast.makeText(getApplicationContext(), "Better Button 1 has ran out of clicks!", Toast.LENGTH_SHORT).show();
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
        int maximumRandomNumber = 1000000;

        Random rand = new Random();
        randomlyGeneratedNumber = rand.nextInt(maximumRandomNumber - minimumRandomNumber + 1) + minimumRandomNumber;

        // Late declaration because above statement initializes randomlyGeneratedNumber
        String randomlyGeneratedNumberString = "" + randomlyGeneratedNumber;

        randomNumber = (TextView) findViewById(R.id.randomNumber);
        if(randomNumber != null) randomNumber.setText(randomlyGeneratedNumberString);

        if(randomlyGeneratedNumber < stashedRecord) {
            stashedRecord = randomlyGeneratedNumber;
            // Zero for standard button
            setRecord(stashedRecord, 0);
        } else {
            refreshRecord();
        }
        makeOdds(stashedRecord, maximumRandomNumber);
    }

    public void generateBetterNumber1() {
        int randomlyGeneratedNumber;
        int maximumRandomNumber = 500000;

        Random rand = new Random();
        randomlyGeneratedNumber = rand.nextInt(maximumRandomNumber - minimumRandomNumber + 1) + minimumRandomNumber;

        // Late declaration because above statement initializes randomlyGeneratedNumber
        String randomlyGeneratedNumberString = "" + randomlyGeneratedNumber;

        randomNumber = (TextView) findViewById(R.id.randomNumber);
        if(randomNumber != null) randomNumber.setText(randomlyGeneratedNumberString);

        if(randomlyGeneratedNumber < stashedRecord) {
            stashedRecord = randomlyGeneratedNumber;
            // One for better button
            setRecord(stashedRecord, 1);
        } else {
            refreshRecord();
        }
        makeOdds(stashedRecord, maximumRandomNumber);


        betterButtonOneCounter++;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedBetterButtonOneCounter", betterButtonOneCounter);
        editor.commit();
    }

    public void decideColor() {
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
    }

    public void makeOdds(int r, int max) {
        double odds = ((double)r / (double)max) * 10.0;
        String percentOddsString = "You have a total " + odds +
                "% chance of getting lower than " + r;

        percentOdds = (TextView) findViewById(R.id.percentOdds);
        if (percentOdds != null) percentOdds.setText(percentOddsString);
    }

    public void setRecord(int r, int b) { // IF FROM GENERATENUMBER__()
        String recordString = "Record: " + r;

        recordNumber = (TextView) findViewById(R.id.recordNumber);
        if(recordNumber != null) recordNumber.setText(recordString);

        saveInfo();
        if(stashedRecord < 10000) updateLeaderboard(b);
        decideColor();
        if((unlockedColor.equals("Purple") && (firstTimePurpley))) achievementsPurpley();
        if((unlockedColor.equals("Yellow") && (firstTimeYellow))) achievementsHalfway();
    }

    public void refreshRecord() { // ELSE FROM GENERATENUMBER__()
        String stashedRecordString =  "Record: " + stashedRecord;

        recordNumber = (TextView) findViewById(R.id.recordNumber);
        if(recordNumber != null) recordNumber.setText(stashedRecordString);
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

    public void updateLeaderboard(int b) {
        Log.d("FUNCTIONALITY", "Updating leaderboard");
        if (isSignedIn()) {
            if (b == 0) {
                Log.d("LEADERBOARD", "Updating leaderboard: Standard Button");
                Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_STANDARDBUTTON), stashedRecord);
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                        getString(R.string.leaderboard_STANDARDBUTTON)), RC_UNUSED);
            } else if (b == 1) {
                Log.d("LEADERBOARD", "Updating leaderboard: Better Button");
                Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_BETTERBUTTON), stashedRecord);
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                        getString(R.string.leaderboard_BETTERBUTTON)), RC_UNUSED);
            }
        } else {
            BaseGameUtils.makeSimpleDialog(this, "Google Play Services failed to connect").show();
            while (!isSignedIn()) {
                Log.d("GPS", "In loop of not signed in! Waiting for connection.");
            }
            updateLeaderboard(b);
        }
    }

    public void achievementsPurpley() {
        Log.d("FUNCTIONALITY", "Updating Achievement: Purpley");
        firstTimePurpley = false;
        if (isSignedIn()) {
            Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_PURPLEY));
            startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),
                    RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, "Google Play Services failed to connect").show();
            while (!isSignedIn()) {
                Log.d("GPS", "In loop of not signed in! Waiting for connection.");
            }
            achievementsPurpley();
        }
    }

    public void achievementsHalfway() {
        Log.d("FUNCTIONALITY", "Updating Achievement: Halfway");
        firstTimeYellow = false;
        if (isSignedIn()) {
            Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_HALFWAYTHERE));
            startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),
                    RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, "Google Play Services failed to connect").show();
            while (!isSignedIn()) {
                Log.d("GPS", "In loop of not signed in! Waiting for connection.");
            }
            achievementsHalfway();
        }
    }

    private void signIn() {
        mSignInClicked = true;
        mGoogleApiClient.connect();
    }

    private boolean isSignedIn() {
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("GPS", "Google Play Games CONNECTED");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("GPS", "Google Play Games SUSPENDED");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("GPS", "Google Play Games FAILED");
        if (mResolvingConnectionFailure) {
            return;
        }

        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;

            mResolvingConnectionFailure = BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, "Connection to Google Play failed!");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, 0);
            }
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