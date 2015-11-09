package com.dirinc.number_game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.util.Log;

import com.google.android.gms.common.*;
import com.google.android.gms.common.api.*;
import com.google.android.gms.games.*;
import com.google.example.games.basegameutils.*;

public class MainActivity extends Activity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnTouchListener {

    private boolean hasLongClicked = false;

    private static final String SHARED_PREFS = "shared_preferences";

    private GoogleApiClient mGoogleApiClient;
    private Handler handler;
    private Runnable mLongPressed;
    private Button number_button, button_more;

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
                .addApiIfAvailable(Games.API).addScope(Games.SCOPE_GAMES)
                .setViewForPopups(findViewById(android.R.id.content))
                .build();
        signIn();
        initializeButtons();
    }

    public void initializeButtons() {
        number_button = (Button) findViewById(R.id.number_button);
        number_button.setOnTouchListener(this);

        button_more = (Button) findViewById(R.id.button_more);
        button_more.setOnTouchListener(this);

        handler = new Handler();
        mLongPressed = new Runnable() {
            public void run() {
                startActivity(2);
                hasLongClicked = true;
            }
        };
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.number_button:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    number_button.setBackgroundColor(getResources().getColor(R.color.button_background_pressed));
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    handler.postDelayed(mLongPressed, 1000);
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    number_button.setBackgroundColor(getResources().getColor(R.color.button_background));
                    handler.removeCallbacks(mLongPressed);
                    if(!hasLongClicked) {
                        startActivity(1);
                    }
                    hasLongClicked = false;
                    return true;
                }
                return false;

            case R.id.button_more:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    button_more.setBackgroundColor(getResources().getColor(R.color.button_background_pressed));
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    button_more.setBackgroundColor(getResources().getColor(R.color.button_background));
                    startActivity(3);
                    return true;
                }
                return false;

            default:
                return false;
        }
    }

    public void setColors() {
        Log.d("UI", "Setting Theme Color");
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

    public void startActivity(int which) {
        Intent changeActivities;

        /*
         * 1 for Number Activity
         * 2 for About
         * 3 for Fun Stuff
         */
        switch (which) {
            case 1:
                changeActivities = new Intent(this, NumberActivity.class);
                Log.d("ActivitySwitch", "Switching to Number Activity");
                startActivity(changeActivities);
                break;

            case 2:
                changeActivities = new Intent(this, AboutActivity.class);
                Log.d("ActivitySwitch", "Switching to About Activity");
                startActivity(changeActivities);
                break;

            case 3:
                changeActivities = new Intent(this, GetButtonActivity.class);
                Log.d("ActivitySwitch", "Switching to Get Button Activity");
                startActivity(changeActivities);
                break;
        }
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
        Log.e("GPS", "Google Play Games SUSPENDED");
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

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
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
}