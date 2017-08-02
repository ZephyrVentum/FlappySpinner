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

    }

    @Override
    public void hideAd() {

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
