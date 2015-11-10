package com.dirinc.number_game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

public class ArcadeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnTouchListener {

    private GoogleApiClient mGoogleApiClient;
    public TextView countdown;
    private Button button_arcade_go;
    public static ArcadeActivity activity;

    private static int RC_SIGN_IN = 9001;

    private static final String SHARED_PREFS = "shared_preferences";

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setColors();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcade);

        activity = this;

        if (android.os.Build.VERSION.SDK_INT>=19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApiIfAvailable(Games.API).addScope(Games.SCOPE_GAMES)
                .setViewForPopups(findViewById(android.R.id.content))
                .build();
        signIn();

        initializeButtons();
    }

    public static ArcadeActivity getInstance(){
        return activity;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && android.os.Build.VERSION.SDK_INT>=19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.button_arcade_go:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    button_arcade_go.setBackgroundColor(getResources().getColor(R.color.button_background_pressed));
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    button_arcade_go.setBackgroundColor(getResources().getColor(R.color.button_background));
                    startActivity(1);
                    return true;
                }
                return false;

            default:
                return false;
        }
    }

    public void initializeButtons() {
        countdown = (TextView) findViewById(R.id.countdown);

        button_arcade_go = (Button) findViewById(R.id.button_arcade_go);
        button_arcade_go.setOnTouchListener(this);
    }

    public void updateCountdown(String secs) {
        Log.d("COUNTDOWN", "Updating clock");
        setContentView(R.layout.activity_arcade);
        initializeButtons();
        countdown.setText(secs);
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
         * 1 for Initial Countdown Activity
         */
        switch (which) {
            case 1:
                changeActivities = new Intent(this, ArcadeCountdownActivity.class);
                Log.d("ActivitySwitch", "Switching to Initial Countdown Activity");
                startActivity(changeActivities);
                break;
        }
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
