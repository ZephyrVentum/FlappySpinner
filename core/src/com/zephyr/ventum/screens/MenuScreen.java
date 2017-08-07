package com.zephyr.ventum.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zephyr.ventum.FlappySpinner;
import com.zephyr.ventum.actors.Background;
import com.zephyr.ventum.actors.GameButton;
import com.zephyr.ventum.utils.AudioManager;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.AssetsManager;
import com.zephyr.ventum.utils.GamePreferences;

/**
 * Created by sashaklimenko on 7/6/17.
 */

public class MenuScreen implements Screen {

    private Stage stage;
    private Game game;
    private World world;
    private Image logo;
    private Background background;

    private GamePreferences preferences;
    private AudioManager audioManager;

    private GameButton playButton, leaderbordsButton, settingsButton, marketButton, achievementButton, shareButton, soundButton, musicButton;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    public MenuScreen(Game game) {
        Box2D.init();
        this.game = game;
        world = new World(new Vector2(0, 0), false);
        stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));

        preferences = new GamePreferences();
        audioManager = AudioManager.getInstance();
        Gdx.input.setInputProcessor(stage);

        FlappySpinner.gameManager.changeBackgroundColor("#4ec0ca");

        setUpBackground();
        setUpButtons();
        setUpLogo();
    }

    public void setUpLogo() {
        logo = new Image(AssetsManager.getTextureRegion(Constants.LOGO_IMAGE_NAME));
        logo.setSize(Constants.WIDTH, Constants.LOGO_HEIGHT);
        logo.setPosition(0, Constants.HEIGHT * 2 / 3 - logo.getHeight() / 4);
        stage.addActor(logo);
    }

    public void setUpBackground() {
        background = new Background();
        stage.addActor(background);
    }

    public void setUpButtons() {
        setUpPlayButton();
        setUpLeaderbordsButton();
        setUpSettingsButton();
        setUpShareButton();
        setUpAchievementButton();
        setUpMarketButton();
        setUpMusicButton();
        setUpSoundButton();
    }

    public void setUpPlayButton() {
        playButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "playbtn", false);
        playButton.setPosition(Constants.WIDTH / 4 - playButton.getWidth() * 2 / 5,
                Constants.HEIGHT / 2 - playButton.getHeight() * 2.5f -2);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //FlappySpinner.gameManager.hideAd();
                float delay = 0.3f;
                setUpFadeOut();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.setScreen(new GameScreen(game));
                    }
                }, delay);
            }
        });
        stage.addActor(playButton);
    }

    public void setUpMarketButton() {
        marketButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "market", false);
        marketButton.setPosition(Constants.WIDTH * 3 / 4 - marketButton.getWidth() * 3 / 5,
                Constants.HEIGHT / 2 - marketButton.getHeight() * 2.5f - 2);
        marketButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float delay = 0.3f;
                setUpFadeOut();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.setScreen(new MarketScreen(game));
                    }
                }, delay);
            }
        });
        stage.addActor(marketButton);
    }


    public void setUpSettingsButton() {
        settingsButton = new GameButton(Constants.SQUARE_BUTTON_SIZE - 0.4f, Constants.SQUARE_BUTTON_SIZE - 0.4f, "settings", false);
        settingsButton.setPosition(1, Constants.HEIGHT / 2 - settingsButton.getHeight() - 1);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game));
            }
        });
        stage.addActor(settingsButton);
    }

    public void setUpShareButton(){
        shareButton = new GameButton(Constants.SQUARE_BUTTON_SIZE - 0.4f, Constants.SQUARE_BUTTON_SIZE - 0.4f, "share", false);
        shareButton.setPosition(1, Constants.HEIGHT / 2);
        shareButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FlappySpinner.gameManager.share();
            }
        });
        stage.addActor(shareButton);
    }

    public void setUpSoundButton() {
        boolean isEnable = preferences.isSoundEnable();
        String drawable = (isEnable) ? "sound" : "sound_off";
        soundButton = new GameButton(Constants.SQUARE_BUTTON_SIZE - 0.4f, Constants.SQUARE_BUTTON_SIZE - 0.4f, drawable, false);
        soundButton.setPosition(Constants.WIDTH - soundButton.getWidth() - 1, Constants.HEIGHT / 2 - soundButton.getHeight() - 1);
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
        musicButton = new GameButton(Constants.SQUARE_BUTTON_SIZE - 0.4f, Constants.SQUARE_BUTTON_SIZE - 0.4f, drawable, false);
        musicButton.setPosition(Constants.WIDTH - musicButton.getWidth() - 1, Constants.HEIGHT / 2);
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

    public void setUpAchievementButton(){
        achievementButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "achievement", false);
        achievementButton.setPosition(Constants.WIDTH * 3 / 4 - achievementButton.getWidth() * 3 / 5,
                Constants.HEIGHT / 4 - achievementButton.getHeight()*1.35f - 2);
        achievementButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });
        stage.addActor(achievementButton);
    }

    public void setUpLeaderbordsButton() {
        leaderbordsButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "leaderboard", false);
        leaderbordsButton.setPosition(Constants.WIDTH / 4 - leaderbordsButton.getWidth() * 2 / 5,
                Constants.HEIGHT / 4 - leaderbordsButton.getHeight()*1.35f - 2);
        leaderbordsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });
        stage.addActor(leaderbordsButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        doPhysicsStep(delta);
    }

    public void setUpFadeOut() {
        Image lastActor = new Image();
        stage.addActor(lastActor);
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor != background && actor != lastActor) {
                actor.addAction(Actions.fadeOut(0.25f));
            }
        }
    }

    private void doPhysicsStep(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

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
