package com.zephyr.ventum;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.vungle.publisher.AdConfig;
import com.vungle.publisher.VungleAdEventListener;
import com.vungle.publisher.VungleInitListener;
import com.vungle.publisher.VunglePub;
import com.zephyr.ventum.interfaces.GameEventListener;
import com.zephyr.ventum.screens.GameScreen;
import com.zephyr.ventum.utils.GamePreferences;
import com.zephyr.ventum.utils.PlayServices;

public class AndroidLauncher extends AndroidApplication implements GameEventListener, PlayServices {

    protected AdView adView;
    protected View gameView;

    private GameHelper gameHelper;
    private final static int requestCode = 1;

    private GameScreen.VungleCallBackListener listener;

    private static final String AD_UNIT_ID = "ca-app-pub-9581992838845527/7251962154";
    private static final String APP_UNIT_ID = "ca-app-pub-9581992838845527~8898239838";
    private static final String APP_VUNGLE_ID = "59a0624c7e5f852824000695";
    private final static String LOG_TAG = "Flappy Spiiner";
    private final String[] placement_list = {"DEFAULT64892"};

    final VunglePub vunglePub = VunglePub.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(false);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() {
            }

            @Override
            public void onSignInSucceeded() {
            }
        };

        gameHelper.setup(gameHelperListener);

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


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
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
        adView.setId(R.id.adsMobId); // this is an arbitrary id, allows for relative positioning in createGameView()
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        adView.setLayoutParams(params);
        adView.setBackgroundColor(Color.BLACK);
    }

    private void createGameView(AndroidApplicationConfiguration cfg) {
        gameView = initializeForView(new FlappySpinner(this), cfg);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);
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
    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out Flappy Spinner! \n https://play.google.com/store/apps/details?id=com.zephyr.ventum");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share!"));
    }

    @Override
    public String get10ScoreAchievementId() {
        return getResources().getString(R.string.achievement_10_score);
    }

    @Override
    public String get25ScoreAchievementId() {
        return getResources().getString(R.string.achievement_20_score);
    }

    @Override
    public String get50ScoreAchievementId() {
        return getResources().getString(R.string.achievement_100_score);
    }

    @Override
    public String get10GamesAchievementId() {
        return getResources().getString(R.string.achievement_10_games);
    }

    @Override
    public String get25GamesAchievementId() {
        return getResources().getString(R.string.achievement_50_games);
    }

    @Override
    public String get50GamesAchievementId() {
        return getResources().getString(R.string.achievement_100_games);
    }

    @Override
    public String getVentumZephyrAchievementId() {
        return getResources().getString(R.string.achievement_ventum_zephyr);
    }

    @Override
    public void signIn() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.signOut();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void unlockAchievement(String id) {
        if (gameHelper.isSignedIn()) {
            Games.Achievements.unlock(gameHelper.getApiClient(), id);
            new GamePreferences().setUnlockedAchievement(id);
        }
    }

    @Override
    public void submitScore(int highScore) {
        if (isSignedIn() == true) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_high_score_leaders), highScore);
        }
    }

    @Override
    public void showAchievement() {
        if (isSignedIn() == true) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
        } else {
            signIn();
        }
    }

    @Override
    public void showScore() {
        if (isSignedIn() == true) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_high_score_leaders)), requestCode);
        } else {
            signIn();
        }
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }

    private final VungleAdEventListener vungleDefaultListener = new VungleAdEventListener() {

        @Override
        public void onAdEnd(@NonNull String placementReferenceId, boolean wasSuccessFulView, boolean wasCallToActionClicked) {
            // Called when user exits the ad and control is returned to your application
            // if wasSuccessfulView is true, the user watched the ad and could be rewarded
            // if wasCallToActionClicked is true, the user clicked the call to action button in the ad.
            Log.w(LOG_TAG, "onAdEnd: " + placementReferenceId + " ,wasSuccessfulView: " + wasSuccessFulView + " ,wasCallToActionClicked: " + wasCallToActionClicked);
            if (wasSuccessFulView) {
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
