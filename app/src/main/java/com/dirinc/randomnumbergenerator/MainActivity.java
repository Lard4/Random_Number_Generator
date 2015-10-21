package com.dirinc.randomnumbergenerator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.common.*;
import com.google.android.gms.common.api.*;
import com.google.android.gms.games.*;
import com.google.example.games.basegameutils.*;

public class MainActivity extends Activity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, View.OnLongClickListener {

    private static final String SHARED_PREFS = "shared_preferences";

    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;

    @Override
    protected void onCreate(Bundle icicle) {
        setColors();
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
        signIn();
        initializeButtons();
    }

    public void initializeButtons() {
        Button number_button = (Button) findViewById(R.id.number_button);
        number_button.setOnClickListener(this);
        number_button.setOnLongClickListener(this);

        Button button_more = (Button) findViewById(R.id.button_more);
        button_more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.number_button:
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                startNumberActivity();
                break;

            case R.id.button_more:
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                startGetButtonActivity();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(v.getId() == R.id.number_button){
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            startAboutActivity();
        }
        return true;
    }

    public void setColors() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        String unlockedColor = sharedPreferences.getString("color", "");

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

    public void startNumberActivity() {
        Intent changeActivities = new Intent(this, NumberActivity.class);
        startActivity(changeActivities);
    }

    public void startAboutActivity() {
        Intent changeActivities = new Intent(this, AboutActivity.class);
        startActivity(changeActivities);
    }

    public void startGetButtonActivity() {
        Intent changeActivities = new Intent(this, GetButtonActivity.class);
        startActivity(changeActivities);
    }

    @Override
    protected void onResume() {
        setColors();
        super.onResume();
        setContentView(R.layout.activity_main);
        initializeButtons();
    }

    @Override
    protected void onStart() {
        setColors();
        super.onStart();
        mGoogleApiClient.connect();
        initializeButtons();
    }

    @Override
    protected void onStop() {
        setColors();
        super.onStop();
        mGoogleApiClient.disconnect();
        initializeButtons();
    }

    private void signIn() {
        mSignInClicked = true;
        mGoogleApiClient.connect();
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
            mResolvingConnectionFailure = true;

            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, "Connection to Google Play failed!")) {
                mResolvingConnectionFailure = false;
            }
        }
        Toast.makeText(getApplicationContext(), "Connected to Google Play", Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, 0);
            }
        }
    }
}