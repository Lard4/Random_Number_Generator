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

    private SharedPreferences sharedPreferences;

    // Buttons
    private Button doItAgain;

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

        Button leaderboards = (Button) findViewById(R.id.leaderboards);
        leaderboards.setOnTouchListener(this);

        Button back = (Button) findViewById(R.id.back);
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
        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

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
        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        boolean isApply0;
        boolean isApply1;

        if(betterButtonOneCounter > 200) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("better_button1", false);
            editor.putBoolean("better_button0", true);
            editor.putInt("stashedBetterButtonOneCounter", 0);
            editor.apply();

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
        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedBetterButtonOneCounter", betterButtonOneCounter);
        editor.apply();
    }

    public void decideColor() {
        if ((stashedRecord <= 10000) && (stashedRecord > 5000) && (!Objects.equals(unlockedColor, "Purple"))) {
            unlockedColor = "Purple";
            saveInfo(unlockedColor);
            showDialog();
            setTheme(R.style.Purple);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 5000) && (stashedRecord > 2500) && (!Objects.equals(unlockedColor, "Blue"))) {
            unlockedColor = "Blue";
            saveInfo(unlockedColor);
            showDialog();
            setTheme(R.style.Blue);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 2500) && (stashedRecord > 750) && (!Objects.equals(unlockedColor, "Teal"))) {
            unlockedColor = "Teal";
            saveInfo(unlockedColor);
            showDialog();
            setTheme(R.style.Teal);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 750) && (stashedRecord > 100) && (!Objects.equals(unlockedColor, "Yellow"))) {
            unlockedColor = "Yellow";
            saveInfo(unlockedColor);
            showDialog();
            setTheme(R.style.Yellow);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 100) && (stashedRecord > 50) && (!Objects.equals(unlockedColor, "Orange"))) {
            unlockedColor = "Orange";
            saveInfo(unlockedColor);
            showDialog();
            setTheme(R.style.Orange);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if ((stashedRecord <= 50) && (stashedRecord > 1) && (!Objects.equals(unlockedColor, "Red"))) {
            unlockedColor = "Red";
            saveInfo(unlockedColor);
            showDialog();
            setTheme(R.style.Red);
            setContentView(R.layout.activity_number);
            startNumberActivity();
        } else if (stashedRecord == 1) {
            unlockedColor = "Pink";
            saveInfo(unlockedColor);
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

        setFactoid();
        decideColor();
        saveInfo(unlockedColor);

        if(stashedRecord <= 20000) updateLeaderboard(b);


        if((unlockedColor.equals("Purple") && (firstTimePurpley))) achievementsPurpley();
        if((unlockedColor.equals("Yellow") && (firstTimeYellow))) achievementsHalfway();
        if(stashedRecord == 1) achievementsMLG();
    }

    public void refreshRecord() { // ELSE FROM GENERATENUMBER__()
        String stashedRecordString =  "Record: " + stashedRecord;

        setFactoid();

        recordNumber = (TextView) findViewById(R.id.recordNumber);
        if(recordNumber != null) recordNumber.setText(stashedRecordString);
    }

    public void setColors() {
        Log.d("UI", "Setting Theme Color");
        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        unlockedColor = sharedPreferences.getString("color", "");
        Log.d("UI", "Theme Color should be set to " + unlockedColor);

        switch (unlockedColor) {
            case "Purple":
                Log.d("UI", "Setting Theme Color to PURPLE");
                setTheme(R.style.Purple);
                break;
            case "Blue":
                Log.d("UI", "Setting Theme Color to BLUE");
                setTheme(R.style.Blue);
                break;
            case "Teal":
                Log.d("UI", "Setting Theme Color to TEAL");
                setTheme(R.style.Teal);
                break;
            case "Yellow":
                Log.d("UI", "Setting Theme Color to YELLOW");
                setTheme(R.style.Yellow);
                break;
            case "Orange":
                Log.d("UI", "Setting Theme Color to ORANGE");
                setTheme(R.style.Orange);
                break;
            case "Red":
                Log.d("UI", "Setting Theme Color to RED");
                setTheme(R.style.Red);
                break;
            case "Pink":
                Log.d("UI", "Setting Theme Color to PINK");
                setTheme(R.style.Pink);
                break;
            default:
                Log.e("UI", "Setting Theme Color DEFAULTING");
                setTheme(R.style.AppTheme);
                break;
        }
    }

    public void setFactoid() {
        Log.d("Functionality", "Starting FunFacts");

        int factoidRecord = stashedRecord + 1;
        String lessThanLine;
        String factoidGutsLine = "";

        String[] factoidArr = new String[2];

        if((stashedRecord < 500000) && (stashedRecord > 400000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 50% chance.";
            factoidGutsLine = "That's the odds of getting diagnosed with cancer!";
        }
        else if((stashedRecord < 400000) && (stashedRecord > 300000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 40% chance.";
            factoidGutsLine = "That's the odds of a celebrity marriage lasting a lifetime!";
        }
        else if((stashedRecord < 300000) && (stashedRecord > 200000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 30% chance.";
            factoidGutsLine = "That's the odds that any given president attended Harvard!";
        }
        else if((stashedRecord < 200000) && (stashedRecord > 100000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 20% chance.";
            factoidGutsLine = "That's the odds of having a stroke!";
        }
        else if((stashedRecord < 100000) && (stashedRecord > 75000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 10% chance.";
            factoidGutsLine = "That's the odds of getting the flu this year!";
        }
        else if((stashedRecord < 75000) && (stashedRecord > 50000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 7.5% chance.";
            factoidGutsLine = "That's the odds of getting accepted to MIT!";
        }
        else if((stashedRecord < 50000) && (stashedRecord > 25000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 5% chance.";
            factoidGutsLine = "That's the odds of being the victim of a serious crime in your lifetime!";
        }
        else if((stashedRecord < 25000) && (stashedRecord > 10000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 2.5% chance.";
            factoidGutsLine = "That's the odds of being born with 11 toes!";
        }
        else if((stashedRecord < 10000) && (stashedRecord > 5000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 1% chance.";
            factoidGutsLine = "That's the odds of being on a plane with a drunken pilot!";
        }
        else if((stashedRecord < 5000) && (stashedRecord > 4000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.5% chance.";
            factoidGutsLine = "That's the odds of being audited by the IRS!";
        }
        else if((stashedRecord < 4000) && (stashedRecord > 3000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.4% chance.";
            factoidGutsLine = "That's the odds of dating a millionaire!";
        }
        else if((stashedRecord < 3000) && (stashedRecord > 2000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.3% chance.";
            factoidGutsLine = "That's the odds of dying in a car accident";
        }
        else if((stashedRecord < 2000) && (stashedRecord > 1000)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.2% chance.";
            factoidGutsLine = "That's the odds of catching a ball at a major league baseball game";
        }
        else if((stashedRecord < 1000) && (stashedRecord > 750)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.1% chance.";
            factoidGutsLine = "That's the odds of being killed while crossing the street";
        } else if((stashedRecord < 750) && (stashedRecord > 500)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.075% chance.";
            factoidGutsLine = "That's the odds of dying from accidental poisoning!";
        }
        else if((stashedRecord < 500) && (stashedRecord > 400)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.05% chance.";
            factoidGutsLine = "That's the odds of dying from any injury this year!";
        }
        else if((stashedRecord < 400) && (stashedRecord > 300)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.04% chance.";
            factoidGutsLine = "That's the odds of ";
        }
        else if((stashedRecord < 200) && (stashedRecord > 100)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.02% chance.";
            factoidGutsLine = "That's the odds of an amateur golfer getting an  hole in one!";
        }
        else if((stashedRecord < 100) && (stashedRecord > 75)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.01% chance.";
            factoidGutsLine = "That's the odds of winning the military Medal of Honor";
        }
        else if((stashedRecord < 75) && (stashedRecord > 50)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.0075% chance.";
            factoidGutsLine = "That's the odds of being killed by a hippo";
        }
        else if((stashedRecord < 50) && (stashedRecord > 25)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.005% chance.";
            factoidGutsLine = "That's the odds of slipping and dying in the shower!";
        }
        else if((stashedRecord < 25) && (stashedRecord > 10)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.0025% chance.";
            factoidGutsLine = "That's the odds of dying due to sharp objects!";
        }
        else if((stashedRecord < 10) && (stashedRecord > 1)) {
            lessThanLine = "Less than " + factoidRecord + "! You just beat about a 0.0001% chance.";
            factoidGutsLine = "That's the odds of getting struck by lightning!";
        }
        else if(stashedRecord == 1) {
            lessThanLine = "much wow. very sad.";
        }
        else {
            lessThanLine = "Less than something big";
            factoidGutsLine = "I don't know the odds of that.";
        }

        factoidArr[0] = lessThanLine;
        factoidArr[1] = factoidGutsLine;

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
            Log.e("GPS", "Google play services failed to connect on Leaderboard. Line: " +
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
            Log.e("GPS", "Google play services failed to connect on Purple Achievement. Line: " +
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
        Log.e("GPS", "Google Play Games FAILED");
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
        setFactoid();
        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Dialog))
                 .setTitle("ᕙ( * •̀ ᗜ •́ * )ᕗ   New Theme!")
                 .setMessage("Congrats mang, you got a new color scheme!")
                 .setPositiveButton("Ok m8!", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         //bruh
                         initializeButtons();
                     }
                 })
                 .show();
        setColors();
        initializeButtons();
    }

    public void saveInfo(String color) {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", stashedRecord);
        editor.putInt("stashedBetterButtonOneCounter", betterButtonOneCounter);
        editor.putString("color", color);
        editor.putString("blah", "pls work");
        editor.apply();
    }

    @Override
    protected void onStop() {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stashedRecord", stashedRecord);
        editor.putInt("stashedBetterButtonOneCounter", betterButtonOneCounter);
        editor.putString("color", unlockedColor);
        editor.putString("blah", "pls work");
        editor.apply();

        super.onStop();
    }
}