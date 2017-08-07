package com.zephyr.ventum.utils;

import com.zephyr.ventum.interfaces.GameEventListener;

/**
 * Created by sashaklimenko on 8/2/17.
 */

public class GameManager implements GameEventListener{

    private GameEventListener gameEventListener;

    public void setGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
    }

    @Override
    public void displayAd() {
        gameEventListener.displayAd();
    }

    @Override
    public void hideAd() {
        gameEventListener.hideAd();
    }

    @Override
    public void changeBackgroundColor(String color) {
        gameEventListener.changeBackgroundColor(color);
    }

    @Override
    public void displayLeaderboard() {

    }

    @Override
    public void displayAchievements() {

    }

    @Override
    public void share() {
        gameEventListener.share();
    }
}
