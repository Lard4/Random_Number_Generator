package com.dirinc.randomnumbergenerator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.*;
import com.google.android.gms.common.api.*;
import com.google.android.gms.games.*;
import com.google.example.games.basegameutils.BaseGameUtils;

import java.util.Objects;
import java.util.Random;

public class NumberActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnTouchListener {

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

    // Buttons
    private Button doItAgain, leaderboards, back;

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
                .addApiIfAvailable(Games.API).addScope(Games.SCOPE_GAMES)
                .setViewForPopups(findViewById(android.R.id.content))
                .build();
        signIn();

        initializeButtons();
        startNumberActivity();
    }

    public void initializeButtons() {
        doItAgain = (Button) findViewById(R.id.doItAgain);
        doItAgain.setOnTouchListener(this);

        leaderboards = (Button) findViewById(R.id.leaderboards);
        leaderboards.setOnTouchListener(this);

        back = (Button) findViewById(R.id.back);
        back.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.doItAgain:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    doItAgain.setBackgroundColor(getResources().getColor(R.color.button_background_pressed));
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    decideGeneration();
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    doItAgain.setBackgroundColor(getResources().getColor(R.color.button_background));
                }
                return false;

            case R.id.back:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    startActivity(1);
                    return true;
                }
                return false;

            case R.id.leaderboards:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    showLeaderboards(0);
                    return true;
                }
                return false;

            default:
                return false;
        }
    }

    public void startNumberActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        boolean isBetterButton0;
        boolean isBetterButton1;

        randomNumber = (TextView) findViewById(R.id.randomNumber);
        recordNumber = (TextView) findViewById(R.id.recordNumber);
        percentOdds = (TextView) findViewById(R.id.percentOdds);

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
        if ((stashedRecord <= 10000) && (stashedRecord > 5000) && (!Objects.equals(unlockedColor, "Purple"))) {
            unlockedColor = "Purple";
            showDialog();
            setTheme(R.style.Purple);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 5000) && (stashedRecord > 2500) && (!Objects.equals(unlockedColor, "Blue"))) {
            unlockedColor = "Blue";
            showDialog();
            setTheme(R.style.Blue);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 2500) && (stashedRecord > 750) && (!Objects.equals(unlockedColor, "Teal"))) {
            unlockedColor = "Teal";
            showDialog();
            setTheme(R.style.Teal);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 750) && (stashedRecord > 100) && (!Objects.equals(unlockedColor, "Yellow"))) {
            unlockedColor = "Yellow";
            showDialog();
            setTheme(R.style.Yellow);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 100) && (stashedRecord > 50) && (!Objects.equals(unlockedColor, "Orange"))) {
            unlockedColor = "Orange";
            showDialog();
            setTheme(R.style.Orange);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 50) && (stashedRecord > 1) && (!Objects.equals(unlockedColor, "Red"))) {
            unlockedColor = "Red";
            showDialog();
            setTheme(R.style.Red);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if (stashedRecord == 1) {
            unlockedColor = "Pink";
            showDialog();
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
        setFactoid();
        decideColor();

        if(stashedRecord <= 20000) updateLeaderboard(b);


        if((unlockedColor.equals("Purple") && (firstTimePurpley))) achievementsPurpley();
        if((unlockedColor.equals("Yellow") && (firstTimeYellow))) achievementsHalfway();
        if(stashedRecord == 1) achievementsMLG();
    }

    public void refreshRecord() { // ELSE FROM GENERATENUMBER__()
        String stashedRecordString =  "Record: " + stashedRecord;

        recordNumber = (TextView) findViewById(R.id.recordNumber);
        if(recordNumber != null) recordNumber.setText(stashedRecordString);
    }

    public void setColors() {
        Log.d("UI", "Setting Theme Color");
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

    public void setFactoid() {
        FunFacts retriever = new FunFacts();
        String[] factoidArr = retriever.getFactoid();

        String lessThanLine = factoidArr[0];
        String factoidGutsLine = factoidArr[1];

        TextView factoid = (TextView) findViewById(R.id.factoid);
        if(factoid != null) factoid.setText(lessThanLine);

        TextView factoidDetail = (TextView) findViewById(R.id.factoidGuts);
        if(factoidDetail != null) factoidDetail.setText(factoidGutsLine);
    }

    public void updateLeaderboard(int b) {
        Log.d("FUNCTIONALITY", "Updating leaderboard");
        if (isSignedIn()) {
            if (b == 0) {
                Log.d("LEADERBOARD", "Updating leaderboard: Standard Button");
                Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_STANDARDBUTTON), stashedRecord);
            } else if (b == 1) {
                Log.d("LEADERBOARD", "Updating leaderboard: Better Button");
                Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_BETTERBUTTON), stashedRecord);
            }
        } else {
            BaseGameUtils.makeSimpleDialog(this, "Google Play Services failed to connect").show();
            // TODO: Auto-sign in to GPS
            Log.d("GPS", "Google play services failed to connect on Leaderboard. Line: " +
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
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
            // TODO: Auto-sign in to GPS
            Log.d("GPS", "Google play services failed to connect on Purple Achievement. Line: " +
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
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
            // TODO: Auto-sign in to GPS
            Log.d("GPS", "Google play services failed to connect on Halfway Achievement. Line: " +
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    public void achievementsMLG() {
        Log.d("FUNCTIONALITY", "Updating Achievement: MLG SHIT");
        if (isSignedIn()) {
            Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_MLGNUMBERGENERATOR));
            startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),
                    RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, "Google Play Services failed to connect").show();
            // TODO: Auto-sign in to GPS
            Log.d("GPS", "Google play services failed to connect on MLG Achievement. Line: " +
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    public void showLeaderboards(int x) {
        switch (x) {
            case 0:
                Log.d("ActivitySwitch", "Showing Leaderboards");
                startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient),
                        RC_UNUSED);
                break;
            case 1:
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                        getString(R.string.leaderboard_STANDARDBUTTON)), RC_UNUSED);
                break;
            case 2:
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                        getString(R.string.leaderboard_BETTERBUTTON)), RC_UNUSED);
                break;
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

    public void startActivity(int which) {
        Intent changeActivities;

        switch (which) {
            case 1:
                changeActivities= new Intent(this, MainActivity.class);
                Log.d("ActivitySwitch", "Switching to Main Activity");
                startActivity(changeActivities);
                break;
        }
    }

    public void showDialog() {
        setTheme(R.style.Dialog);
        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Dialog))
                 .setTitle("ᕙ( * •̀ ᗜ •́ * )ᕗ   New Theme!")
                 .setMessage("Congrats ming mang, you got a new color scheme!")
                 .setPositiveButton("Ok m8!", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         //bruh
                         initializeButtons();
                         setFactoid();
                     }
                 })
                 .show();
        setColors();
        initializeButtons();
    }

    public int getRecord() {
        return stashedRecord;
    }

    public void saveInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", stashedRecord);
        editor.putInt("stashedBetterButtonOneCounter", betterButtonOneCounter);
        editor.putString("color", unlockedColor);
        editor.apply();
    }

    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", stashedRecord);
        editor.putInt("stashedBetterButtonOneCounter", betterButtonOneCounter);
        editor.putString("color", unlockedColor);
        editor.apply();

        super.onStop();
    }
}