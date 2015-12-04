package com.dirinc.number_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;

import android.widget.Toast;

import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.purplebrain.adbuddiz.sdk.AdBuddizLogLevel;
import com.purplebrain.adbuddiz.sdk.AdBuddizRewardedVideoDelegate;
import com.purplebrain.adbuddiz.sdk.AdBuddizRewardedVideoError;

public class RewardedVideoActivity extends Activity {

    private Toast toast;

    private static final String SHARED_PREFS = "shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_video);

        final Activity activity = this;

        AdBuddiz.setLogLevel(AdBuddizLogLevel.Info);
        AdBuddiz.setTestModeActive();
        AdBuddiz.setPublisherKey(String.valueOf(R.string.ad_id));

        // Fetch le ad
        AdBuddiz.RewardedVideo.fetch(this);

        // Show le ad
        AdBuddiz.RewardedVideo.show(this);

        // Implement this delegate to get notified when the user completed the video
        // All callbacks in the delegate will be called in UI thread.
        AdBuddiz.RewardedVideo.setDelegate(new AdBuddizRewardedVideoDelegate() {
            @Override
            public void didFetch() { // Next rewarded video is ready to be displayed
                Log.d("AdBud", "Ad successfully fetched");
            }

            @Override
            public void didComplete() { // User closed the ad after having fully watched the video
                Log.d("AdBud", "Ad successfully watched");
                toast = Toast.makeText(activity, "Better Button 1 Applied!", Toast.LENGTH_SHORT);
                toast.show();
                giveRewardToUser();
            }

            @Override
            public void didFail(AdBuddizRewardedVideoError error) { // Something went wrong when fetching or showing a video
                Log.e("AdBud", "Ad failed to load");
                new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.Dialog))
                        .setTitle("An Error Has Occurred")
                        .setMessage("Uh oh, I couldn't play your ad. Please try again later.")
                        .setPositiveButton("Ok m8!", null)
                        .show();
            }

            @Override
            public void didNotComplete() { // Media player encountered an error
                Log.d("AdBud", "Ad closed");
            }
        });
    }

    private void giveRewardToUser() {
        Log.d("AdBud", "Giving award");
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        // Give the reward to the user. As example, we only show a toast.
        if(toast != null) toast.cancel();
        toast = Toast.makeText(getApplicationContext(), "Better Button 1 Applied!", Toast.LENGTH_SHORT);
        toast.show();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        // There shall not be more than one that is tru
        editor.putBoolean("better_button0", false);
        editor.putBoolean("better_button1", true);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(toast != null) toast.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdBuddiz.onDestroy(); // to minimize memory footprint
    }
}