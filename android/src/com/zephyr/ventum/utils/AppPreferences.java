package com.zephyr.ventum.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sashaklimenko on 7/26/17.
 */

public class AppPreferences {

    public static final String APP_PREFERENCES = "application";

    public static final String COINS = "coins";


    private static final String STANDART_SPINNER_SKIN = "standart_spinner";
    private static final String RAINBOW_SPINNER_SKIN = "rainbow_spinner";
    private static final String SILVER_SPINNER_SKIN = "";
    private static final String GOLD_SPINNER_SKIN = "";

    private SharedPreferences sharedPreferences;

    private AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static AppPreferences getInstance(Context context) {
        return new AppPreferences(context);
    }

    public void setLvlScore(int score, String LEVEL_NAME) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LEVEL_NAME, score);
        editor.apply();
    }

    public int getLvlScore(String LVL_NAME) {
        return sharedPreferences.getInt(LVL_NAME, 0);
    }

}
