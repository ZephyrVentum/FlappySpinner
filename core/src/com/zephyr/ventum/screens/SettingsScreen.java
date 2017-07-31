package com.zephyr.ventum.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zephyr.ventum.actors.Background;
import com.zephyr.ventum.actors.GameButton;
import com.zephyr.ventum.utils.AudioManager;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.GamePreferences;

/**
 * Created by sashaklimenko on 7/31/17.
 */

public class SettingsScreen implements Screen {

    private Game aGame;
    private Stage stage;
    private GamePreferences preferences;
    private AudioManager audioManager;
    private GameButton homeButton, musicButton, soundButton;
    private Label developerLabel, designerLabel, testerLabel, poweredByLabel;
    private Label whoDeveloperLavel, whoDesignerLabel, whoTaster, whoPowered;

    public SettingsScreen(Game game) {
        this.aGame = game;
        preferences = new GamePreferences();
        audioManager = AudioManager.getInstance();
        stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
        Gdx.input.setInputProcessor(stage);

        setUpBackground();


        setUpLabels();

        setUpHomeButton();
        setUpMusicButton();
        setUpSoundButton();
    }

    public void setUpBackground() {
        Background background = new Background();
        stage.addActor(background);
    }

    public void setUpHomeButton() {
        homeButton = new GameButton(Constants.SQUARE_BUTTON_SIZE - 0.4f, Constants.SQUARE_BUTTON_SIZE - 0.4f, "home", false);
        homeButton.setPosition(1, Constants.HEIGHT - homeButton.getWidth() - 1);
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                aGame.setScreen(new MenuScreen(aGame));
            }
        });
        stage.addActor(homeButton);
    }

    public void setUpSoundButton() {
        boolean isEnable = preferences.isSoundEnable();
        String drawable = (isEnable) ? "sound" : "sound_off";
        soundButton = new GameButton(Constants.SQUARE_BUTTON_SIZE, Constants.SQUARE_BUTTON_SIZE, drawable, false);
        soundButton.setPosition(1, 1);
        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                preferences.setSoundEnabled();
                String drawable = (preferences.isSoundEnable()) ? "sound" : "sound_off";
                soundButton.changeDrawable(drawable);
            }
        });
        stage.addActor(soundButton);
    }

    public void setUpMusicButton() {
        boolean isEnable = preferences.isMusicEnable();
        String drawable = (isEnable) ? "music" : "music_off";
        musicButton = new GameButton(Constants.SQUARE_BUTTON_SIZE, Constants.SQUARE_BUTTON_SIZE, drawable, false);
        musicButton.setPosition(1 + musicButton.getWidth() + 1, 1);
        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                preferences.setMusicEnabled();
                String drawable = (preferences.isMusicEnable()) ? "music" : "music_off";
                musicButton.changeDrawable(drawable);
                if(preferences.isMusicEnable()){
                    audioManager.playMusic();
                } else {
                    audioManager.pauseMusic();
                }
            }
        });
        stage.addActor(musicButton);
    }


    public void setUpLabels() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
