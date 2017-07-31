package com.zephyr.ventum.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by sashaklimenko on 7/31/17.
 */

public class AudioManager {

    private static AudioManager ourInstance = new AudioManager();
    private static Music music;
    private static Sound jumpSound, hitSound, scoreSound, buttonSound, marketSound, gameoverSound;
    private static boolean soundState;

    private static GamePreferences preferences;

    public AudioManager() {}

    public static AudioManager getInstance() {
        updatePreferences();
        return ourInstance;
    }

    private static void updatePreferences() {
        preferences = new GamePreferences();
        soundState = preferences.isSoundEnable();
    }

    public void init() {
        updatePreferences();
        music = Gdx.audio.newMusic(Gdx.files.internal(Constants.MUSIC_PATH));
        music.setLooping(true);
        music.setVolume(0.4f);
        playMusic();
        jumpSound = createSound(Constants.JUMP_SOUND_PATH);
        hitSound = createSound(Constants.HIT_SOUND_PATH);
        scoreSound = createSound(Constants.SCORE_SOUND_PATH);
        buttonSound = createSound(Constants.BUTTON_SOUND_PATH);
        marketSound = createSound(Constants.MARKET_SOUND_PATH);
        gameoverSound = createSound(Constants.GAMEOVER_SOUND_PATH);
    }

    public Sound createSound(String soundFileName) {
        return Gdx.audio.newSound(Gdx.files.internal(soundFileName));
    }

    public void playMusic() {
        if (preferences.isMusicEnable()) {
            music.play();
        }
    }

    public void playSound(Sound sound) {
        if(soundState) {
            sound.play();
        }
    }

    public void pauseMusic(){
        if(music.isPlaying()){
            music.pause();
        }
    }

    public Music getMusic() {
        return music;
    }

    public Sound getJumpSound() {
        return jumpSound;
    }

    public Sound getHitSound() {
        return hitSound;
    }

    public Sound getScoreSound() {
        return scoreSound;
    }

    public Sound getButtonSound() {
        return buttonSound;
    }

    public Sound getMarketSound() {
        return marketSound;
    }

    public Sound getGameoverSound() {
        return gameoverSound;
    }

    public void dispose(){
        music.dispose();
        jumpSound.dispose();
        hitSound.dispose();
        scoreSound.dispose();
        buttonSound.dispose();
        marketSound.dispose();
        gameoverSound.dispose();
    }

}
