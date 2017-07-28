package com.zephyr.ventum.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by sashaklimenko on 7/27/17.
 */

public class GamePreferences {

    private Preferences preferences;

    public GamePreferences() {
        preferences = getInstance();
    }

    public static Preferences getInstance() {
        return Gdx.app.getPreferences("GamePreferences");
    }

    public void setMaxScore(int score) {
        preferences.putInteger("max_score", score);
        preferences.flush();
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

    public void setUserMoney(int money){
        preferences.putInteger("money", money);
        preferences.flush();
    }

    public int getUserMoney(){
        return preferences.getInteger("money", 100);
    }

    public void setSkinBought(String skinName) {
        preferences.putBoolean(skinName, true);
        preferences.flush();
    }

    public boolean isSkinBougth(String skinName) {
        if (skinName.equals(Constants.SPINNER_STANDARD_SKIN)) return true;
        return preferences.getBoolean(skinName, false);
    }
}
