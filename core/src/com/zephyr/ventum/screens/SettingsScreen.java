package com.zephyr.ventum.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zephyr.ventum.actors.Background;
import com.zephyr.ventum.actors.GameButton;
import com.zephyr.ventum.utils.AssetsManager;
import com.zephyr.ventum.utils.AudioManager;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.GamePreferences;

import static com.zephyr.ventum.utils.Constants.PRIVACY_POLICY_BUTTON_PATH;
import static com.zephyr.ventum.utils.Constants.PRIVACY_POLICY_BUTTON_PRESSED_PATH;

/**
 * Created by sashaklimenko on 7/31/17.
 */

public class SettingsScreen implements Screen {

    private Game aGame;
    private Stage stage;
    private GamePreferences preferences;
    private AudioManager audioManager;
    private GameButton homeButton, musicButton, soundButton, githubButton, privacyButton;
    private Label developerLabel, designerLabel, testerLabel, poweredByLabel;
    private Label whoDeveloperLabel, whoDesignerLabel, whoTester, whoPowered;

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
        setUpGitHubButton();
        setUpPrivacyPolicyButton();
    }

    public void setUpBackground() {
        Background background = new Background();
        stage.addActor(background);
    }

    private void setUpPrivacyPolicyButton() {
        privacyButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH,
                Constants.RECTANGLE_BUTTON_HEIGHT,
                PRIVACY_POLICY_BUTTON_PATH,
                PRIVACY_POLICY_BUTTON_PRESSED_PATH,
                false);
        privacyButton.setPosition(Constants.WIDTH - privacyButton.getWidth() - 1, (2 * privacyButton.getHeight()) - 1.5f);
        privacyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                aGame.setScreen(new MenuScreen(aGame));
                Gdx.net.openURI("https://zephyrventum.gitlab.io/privacy-policy/flappy-spinner-privacy-policy.html");
            }
        });
        stage.addActor(privacyButton);
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

    public void setUpGitHubButton() {
        githubButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "github", false);
        githubButton.setPosition(Constants.WIDTH - githubButton.getWidth() - 1, 1);
        githubButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI("https://github.com/ZephyrVentum");
            }
        });
        stage.addActor(githubButton);
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
                if (preferences.isMusicEnable()) {
                    audioManager.playMusic();
                } else {
                    audioManager.pauseMusic();
                }
            }
        });
        stage.addActor(musicButton);
    }


    public void setUpLabels() {
        setUpDeveloperLabel();
        setUpWhoDeveloperLabel();
        setUpDesignerLabel();
        setUpWhoDesignerLabel();
        setUpTesterLabel();
        setUpWhoTesterLabel();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    public void setUpDeveloperLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getLargeFont();
        developerLabel = new Label("Developer", labelStyle);
        developerLabel.setFontScale(0.065f);
        developerLabel.setSize(Constants.WIDTH, Constants.LARGE_FONT_SIZE * developerLabel.getFontScaleY() + 1);
        developerLabel.setAlignment(Align.center);
        developerLabel.setPosition(0, Constants.HEIGHT - developerLabel.getHeight() - Constants.SQUARE_BUTTON_SIZE);
        stage.addActor(developerLabel);
    }

    public void setUpWhoDeveloperLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getMediumFont();
        whoDeveloperLabel = new Label("Alexander Klimenko", labelStyle);
        whoDeveloperLabel.setFontScale(0.065f);
        whoDeveloperLabel.setSize(Constants.WIDTH, Constants.LARGE_FONT_SIZE * whoDeveloperLabel.getFontScaleY() + 1);
        whoDeveloperLabel.setAlignment(Align.center);
        whoDeveloperLabel.setPosition(0, developerLabel.getY() - whoDeveloperLabel.getHeight() * 2 / 3);
        stage.addActor(whoDeveloperLabel);
    }

    public void setUpDesignerLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getLargeFont();
        designerLabel = new Label("Designer", labelStyle);
        designerLabel.setFontScale(0.065f);
        designerLabel.setSize(Constants.WIDTH, Constants.LARGE_FONT_SIZE * designerLabel.getFontScaleY() + 1);
        designerLabel.setAlignment(Align.center);
        designerLabel.setPosition(0, whoDeveloperLabel.getY() - designerLabel.getHeight() * 2 / 3);
        stage.addActor(designerLabel);
    }

    public void setUpWhoDesignerLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getMediumFont();
        whoDesignerLabel = new Label("Elena Kiselova", labelStyle);
        whoDesignerLabel.setFontScale(0.065f);
        whoDesignerLabel.setSize(Constants.WIDTH, Constants.LARGE_FONT_SIZE * whoDesignerLabel.getFontScaleY() + 1);
        whoDesignerLabel.setAlignment(Align.center);
        whoDesignerLabel.setPosition(0, designerLabel.getY() - whoDesignerLabel.getHeight() * 2 / 3);
        stage.addActor(whoDesignerLabel);
    }

    public void setUpTesterLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getLargeFont();
        testerLabel = new Label("Tester", labelStyle);
        testerLabel.setFontScale(0.065f);
        testerLabel.setSize(Constants.WIDTH, Constants.LARGE_FONT_SIZE * testerLabel.getFontScaleY() + 1);
        testerLabel.setAlignment(Align.center);
        testerLabel.setPosition(0, whoDesignerLabel.getY() - testerLabel.getHeight() * 2 / 3);
        stage.addActor(testerLabel);
    }

    public void setUpWhoTesterLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getMediumFont();
        whoTester = new Label("Alexey Koshmanov", labelStyle);
        whoTester.setFontScale(0.065f);
        whoTester.setSize(Constants.WIDTH, Constants.LARGE_FONT_SIZE * whoTester.getFontScaleY() + 1);
        whoTester.setAlignment(Align.center);
        whoTester.setPosition(0, testerLabel.getY() - whoTester.getHeight() * 2 / 3);
        stage.addActor(whoTester);
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
