package com.zephyr.ventum;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.vungle.publisher.AdConfig;
import com.vungle.publisher.VungleAdEventListener;
import com.vungle.publisher.VungleInitListener;
import com.vungle.publisher.VunglePub;
import com.zephyr.ventum.interfaces.GameEventListener;
import com.zephyr.ventum.screens.GameScreen;

public class AndroidLauncher extends AndroidApplication implements GameEventListener {

    protected AdView adView;
    protected View gameView;

    private GameScreen.VungleCallBackListener listener;

    private static final String AD_UNIT_ID = "ca-app-pub-9581992838845527/7251962154";
    private static final String APP_UNIT_ID = "ca-app-pub-9581992838845527~8898239838";
    private static final String APP_VUNGLE_ID = "59a0624c7e5f852824000695";
    private final static String LOG_TAG = "Flappy Spiiner";
    private final String[] placement_list = {"DEFAULT64892"};

    final VunglePub vunglePub = VunglePub.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useGyroscope = false;

        MobileAds.initialize(this, APP_UNIT_ID);
        vunglePub.init(this, APP_VUNGLE_ID, placement_list, new VungleInitListener() {
            @Override
            public void onSuccess() {
                Log.d(LOG_TAG, "init success");
                vunglePub.clearAndSetEventListeners(vungleDefaultListener);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        createAdView();
        layout.addView(adView);

        createGameView(config);
        layout.addView(gameView);

        setContentView(layout);

        startAdvertising(adView);
    }

    private void createAdView() {
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AD_UNIT_ID);
        adView.setId(1337); // this is an arbitrary id, allows for relative positioning in createGameView()
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        adView.setLayoutParams(params);
        adView.setBackgroundColor(Color.BLACK);
    }

    private void createGameView(AndroidApplicationConfiguration cfg) {
        gameView = initializeForView(new FlappySpinner(this), cfg);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.BELOW, adView.getId());
        gameView.setLayoutParams(params);
    }

    private void startAdvertising(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }


    @Override
    public void displayAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        vunglePub.onPause();
    }

    @Override
    protected void onDestroy() {
        // remove VungleAdEventListeners
        vunglePub.removeEventListeners(vungleDefaultListener);
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        vunglePub.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void hideAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adView.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void displayVungle(GameScreen.VungleCallBackListener listener) {
        this.listener = listener;
        if (vunglePub != null && vunglePub.isInitialized()) {
            // Check a Placement if it is ready to play the Ad
            if (vunglePub.isAdPlayable(placement_list[0])) {
                // Play a Placement ad with Placement ID, you can pass AdConfig to customize your ad
                AdConfig overrideConfig = new AdConfig();
                overrideConfig.setBackButtonImmediatelyEnabled(false);
                vunglePub.playAd(placement_list[0], overrideConfig);
            }
        }
    }

    @Override
    public void changeBackgroundColor(final String color) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adView.setBackgroundColor(Color.parseColor(color));//"#4ec0ca" sky, "#e9fcd9" cloud
            }
        });
    }

    @Override
    public void displayLeaderboard() {

    }

    @Override
    public void displayAchievements() {

    }

    @Override
    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out Flappy Spinner! \n https://github.com/ZephyrVentum/FlappySpinner");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share!"));
    }

    private final VungleAdEventListener vungleDefaultListener = new VungleAdEventListener() {

        @Override
        public void onAdEnd(@NonNull String placementReferenceId, boolean wasSuccessFulView, boolean wasCallToActionClicked) {
            // Called when user exits the ad and control is returned to your application
            // if wasSuccessfulView is true, the user watched the ad and could be rewarded
            // if wasCallToActionClicked is true, the user clicked the call to action button in the ad.
            Log.w(LOG_TAG, "onAdEnd: " + placementReferenceId + " ,wasSuccessfulView: " + wasSuccessFulView + " ,wasCallToActionClicked: " + wasCallToActionClicked);
            if(wasSuccessFulView) {
                listener.vungleCallBack();
            }
        }

        @Override
        public void onAdStart(@NonNull String placementReferenceId) {
            // Called before playing an ad
            Log.w(LOG_TAG, "onAdStart: " + placementReferenceId);
        }

        @Override
        public void onUnableToPlayAd(@NonNull String placementReferenceId, String reason) {
            // Called after playAd(placementId, adConfig) is unable to play the ad
            Log.w(LOG_TAG, "onUnableToPlayAd: " + placementReferenceId + " ,reason: " + reason);
        }

        @Override
        public void onAdAvailabilityUpdate(@NonNull String placementReferenceId, boolean isAdAvailable) {

            // Notifies ad availability for the indicated placement
            // There can be duplicate notifications
            Log.w(LOG_TAG, "onAdAvailabilityUpdate: " + placementReferenceId + " isAdAvailable: " + isAdAvailable);

            final String placementIdUpdated = placementReferenceId;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    };
}
