package com.zephyr.ventum.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by sashaklimenko on 7/27/17.
 */

public class GamePreferences {

    private Preferences preferences;
    private static final String COUNT_KEY = "_COUNT", UNLOCK_KEY = "_UNLOCK";

    public GamePreferences() {
        preferences = getInstance();
    }

    public static Preferences getInstance() {
        return Gdx.app.getPreferences("GamePreferences");
    }

    public void setMaxScore(int score) {
        if (score > getMaxScore()) {
            preferences.putInteger("max_score", score);
            preferences.flush();
        }
    }

    public int getMaxScore() {
        return preferences.getInteger("max_score", 0);
    }

    public void setCurrentSkin(String skin) {
        preferences.putString("current_skin", skin);
        preferences.flush();
    }

    public String getCurrentSkin() {
        return preferences.getString("current_skin", Constants.SPINNER_STANDARD_SKIN);
    }

    public void setUserMoney(int money) {
        preferences.putInteger("money", money);
        preferences.flush();
    }

    public int getUserMoney() {
        return preferences.getInteger("money", 50);
    }

    public void setSkinBought(String skinName) {
        preferences.putBoolean(skinName, true);
        preferences.flush();
    }

    public boolean isSkinBought(String skinName) {
        if (skinName.equals(Constants.SPINNER_STANDARD_SKIN)) return true;
        return preferences.getBoolean(skinName, false);
    }

    public boolean isSoundEnable() {
        return preferences.getBoolean("sound_on", true);
    }

    public void setSoundEnabled() {
        preferences.putBoolean("sound_on", !isSoundEnable());
        preferences.flush();
    }

    public boolean isMusicEnable() {
        return preferences.getBoolean("music_on", true);
    }

    public void setMusicEnabled() {
        preferences.putBoolean("music_on", !isMusicEnable());
        preferences.flush();
    }

    public void setUnlockedAchievement(String id) {
        preferences.putBoolean(id + UNLOCK_KEY, true);
        preferences.flush();
    }

    public boolean isAchievementUnlocked(String id) {
        return preferences.getBoolean(id + UNLOCK_KEY, false);
    }

    public void incrementAchievementCount(String id, int steps) {
        preferences.putInteger(id + COUNT_KEY, getAchievementCount(id) + steps);
        preferences.flush();
    }

    public int getAchievementCount(String id) {
        return preferences.getInteger(id + COUNT_KEY, 0);
    }

}
