package com.zephyr.ventum.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zephyr.ventum.actors.Background;
import com.zephyr.ventum.actors.GameButton;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.TextureHolder;

/**
 * Created by sashaklimenko on 7/6/17.
 */

public class MenuScreen implements Screen {

    private Stage stage;
    private Game game;
    private World world;
    private Image logo;
    private Background background;

    private GameButton playButton, leaderbordsButton, settingsButton, marketButton;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    public MenuScreen(Game game) {
        Box2D.init();
        this.game = game;
        world = new World(new Vector2(0, 0), false);
        stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));

        Gdx.input.setInputProcessor(stage);
        setUpBackground();
        setUpButtons();
        setUpLogo();
    }

    public void setUpLogo(){
        logo = new Image(TextureHolder.getTextureRegion(Constants.LOGO_IMAGE_NAME));
        logo.setSize(Constants.WIDTH, Constants.LOGO_HEIGHT);
        logo.setPosition(0,Constants.HEIGHT *2/3 - logo.getHeight()/2);
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
    }

    public void setUpPlayButton() {
        playButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "playbtn", false);
        playButton.setPosition(Constants.WIDTH / 4 - playButton.getWidth() * 2 / 5,
                Constants.HEIGHT / 2 - playButton.getHeight() * 2);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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

    public void setUpFadeOut() {
        Image lastActor = new Image();
        stage.addActor(lastActor);
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors){
            if (actor != background && actor !=lastActor) {
                actor.addAction(Actions.fadeOut(0.25f));
            }
        }
    }

    public void setUpLeaderbordsButton() {
        leaderbordsButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "leaderboard", false);
        leaderbordsButton.setPosition(Constants.WIDTH * 3 / 4 - leaderbordsButton.getWidth() * 3 / 5,
                Constants.HEIGHT / 2 - leaderbordsButton.getHeight() * 2);
        leaderbordsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });
        stage.addActor(leaderbordsButton);
    }


    public void setUpSettingsButton() {
        settingsButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "settings", false);
        settingsButton.setPosition(Constants.WIDTH / 4 - settingsButton.getWidth() * 2 / 5, Constants.HEIGHT / 4 - settingsButton.getHeight());
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });
        stage.addActor(settingsButton);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        doPhysicsStep(delta);
    }

    @Override
    public void resize(int width, int height) {

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
