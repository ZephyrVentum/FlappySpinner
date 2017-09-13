package com.zephyr.ventum.utils;

import com.zephyr.ventum.interfaces.GameEventListener;
import com.zephyr.ventum.screens.GameScreen;

/**
 * Created by sashaklimenko on 8/2/17.
 */

public class GameManager implements GameEventListener {

    private GameEventListener gameEventListener;
    private GamePreferences preferences;

    public void setGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
    }

    public void setPreferences(GamePreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void signIn() {
    }

    @Override
    public void signOut() {
    }

    @Override
    public void unlockAchievement(String id) {
        gameEventListener.unlockAchievement(id);
    }

    @Override
    public void submitScore(int highScore) {
        gameEventListener.submitScore(highScore);

        if (highScore >= 10 && !preferences.isAchievementUnlocked(get10ScoreAchievementId())) {
            unlockAchievement(get10ScoreAchievementId());
        }

        if (highScore >= 20 && !preferences.isAchievementUnlocked(get10ScoreAchievementId())) {
            unlockAchievement(get10ScoreAchievementId());
        }

        if (highScore >= 100 && !preferences.isAchievementUnlocked(get10ScoreAchievementId())) {
            unlockAchievement(get10ScoreAchievementId());
        }

        if (!preferences.isAchievementUnlocked(get10GamesAchievementId())) {
            preferences.incrementAchievementCount(get10GamesAchievementId(), 1);
            if (preferences.getAchievementCount(get10GamesAchievementId()) >= 10) {
                unlockAchievement(get10GamesAchievementId());
            }
        }

        if (!preferences.isAchievementUnlocked(get50GamesAchievementId())) {
            preferences.incrementAchievementCount(get50GamesAchievementId(), 1);
            if (preferences.getAchievementCount(get50GamesAchievementId()) >= 50) {
                unlockAchievement(get50GamesAchievementId());
            }
        }

        if (!preferences.isAchievementUnlocked(get100GamesAchievementId())) {
            preferences.incrementAchievementCount(get100GamesAchievementId(), 1);
            if (preferences.getAchievementCount(get100GamesAchievementId()) >= 100) {
                unlockAchievement(get10GamesAchievementId());
            }
        }
    }

    @Override
    public void showAchievement() {
        gameEventListener.showAchievement();
    }

    @Override
    public void showScore() {
        gameEventListener.showScore();
    }

    @Override
    public boolean isSignedIn() {
        return false;
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
    public void displayVungle(GameScreen.VungleCallBackListener listener) {
        gameEventListener.displayVungle(listener);
    }

    @Override
    public void changeBackgroundColor(String color) {
        gameEventListener.changeBackgroundColor(color);
    }

    public void achievementVentumZephyr() {
        if (!preferences.isAchievementUnlocked(getVentumZephyrAchievementId())) {
            preferences.incrementAchievementCount(getVentumZephyrAchievementId(), 1);
            if (preferences.getAchievementCount(getVentumZephyrAchievementId()) >= 3) {
                unlockAchievement(getVentumZephyrAchievementId());
            }
        }
    }

    @Override
    public void share() {
        gameEventListener.share();
    }

    @Override
    public String get10ScoreAchievementId() {
        return gameEventListener.get10ScoreAchievementId();
    }

    @Override
    public String get20ScoreAchievementId() {
        return gameEventListener.get20ScoreAchievementId();
    }

    @Override
    public String get100ScoreAchievementId() {
        return gameEventListener.get100ScoreAchievementId();
    }

    @Override
    public String get10GamesAchievementId() {
        return gameEventListener.get10GamesAchievementId();
    }

    @Override
    public String get50GamesAchievementId() {
        return gameEventListener.get50GamesAchievementId();
    }

    @Override
    public String get100GamesAchievementId() {
        return gameEventListener.get100GamesAchievementId();
    }

    @Override
    public String getVentumZephyrAchievementId() {
        return gameEventListener.getVentumZephyrAchievementId();
    }

}
