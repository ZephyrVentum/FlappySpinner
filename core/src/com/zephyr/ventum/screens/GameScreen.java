package com.zephyr.ventum.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zephyr.ventum.enums.GameState;
import com.zephyr.ventum.actors.Background;
import com.zephyr.ventum.actors.GameButton;
import com.zephyr.ventum.actors.Ground;
import com.zephyr.ventum.actors.Spinner;
import com.zephyr.ventum.actors.Tube;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.AssetsManager;
import com.zephyr.ventum.utils.GamePreferences;
import com.zephyr.ventum.utils.WorldUtils;

/**
 * Created by ZiFir on 08.07.2017.
 */

public class GameScreen implements Screen, ContactListener {

    private Stage stage;
    private Game aGame;
    private World world;
    private Image onPause, onResume, onFinish;
    private GameButton pauseButton, playButton, homeButton;
    private Label scoreLabel;
    private GamePreferences preferences;

    private GameState state;

    private Ground ground;
    private Tube tubeFirst, tubeSecond;

    private int SCORE = 0;

    private boolean isFirstClick = true;

    private Spinner spinner;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;
    private Box2DDebugRenderer renderer = new Box2DDebugRenderer();


    public GameScreen(Game game) {
        Box2D.init();

        preferences = new GamePreferences();

        aGame = game;
        stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        Gdx.input.setInputProcessor(stage);

        state = GameState.RESUME;

        setUpBackground();
        setUpTube();
        setUpGround();
        setUpSpinner();


        setUpOnPause();
        setUpOnResume();
        setUpOnFinish();

        setUpPauseButton();
        setUpPlayButton();
        setUpHomeButton();

        setUpScoreLabel();
    }

    public void setUpTube() {
        tubeFirst = new Tube(WorldUtils.createTopTube(world, Constants.WIDTH * 1.5f));
        tubeSecond = new Tube(WorldUtils.createTopTube(world, Constants.WIDTH * 2 + Constants.TUBE_WIDTH));
        stage.addActor(tubeFirst);
        stage.addActor(tubeSecond);
    }

    public void setUpSpinner() {
        spinner = new Spinner(WorldUtils.createSpinner(world));
        stage.addActor(spinner);
    }

    public void setUpBackground() {
        Background background = new Background();
        stage.addActor(background);
    }

    public void setUpGround() {
        ground = new Ground(WorldUtils.createGround(world));
        stage.addActor(ground);
    }


    public void setUpScoreLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont bitmapFont = new BitmapFont();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = new Color(0xff8a00ff);
        scoreLabel = new Label("" + SCORE, labelStyle);
        scoreLabel.setFontScale(0.17f);
        scoreLabel.setHeight(4f);
        scoreLabel.setPosition(Constants.WIDTH / 2 - scoreLabel.getWidth() / 2, Constants.HEIGHT * 4 / 5 - scoreLabel.getHeight() / 2);
        scoreLabel.setAlignment(Align.center);
        stage.addActor(scoreLabel);
    }

    public void setUpOnPause() {
        onPause = new Image(AssetsManager.getTextureRegion(Constants.PAUSE_IMAGE_NAME));
        onPause.setVisible(false);
        onPause.setSize(Constants.WIDTH, Constants.ONPAUSE_HEIGHT);
        onPause.setPosition(Constants.WIDTH / 2 - onPause.getWidth() / 2, Constants.HEIGHT / 2 - onPause.getHeight() / 2);
        onPause.setOrigin(onPause.getWidth() / 2, onPause.getHeight() / 2);
        onPause.addAction(setOnStateImageAction(1.2f));
        stage.addActor(onPause);
    }

    public void setUpOnFinish() {
        onFinish = new Image(AssetsManager.getTextureRegion(Constants.FINISH_IMAGE_NAME));
        onFinish.setVisible(false);
        onFinish.setSize(Constants.WIDTH, Constants.ONFINISH_HEIGHT);
        onFinish.setPosition(Constants.WIDTH / 2 - onFinish.getWidth() / 2, Constants.HEIGHT / 2 - onFinish.getHeight() / 5);
        onFinish.setOrigin(onFinish.getWidth() / 2, onFinish.getHeight() / 2);
        onFinish.addAction(setOnStateImageAction(1.2f));
        stage.addActor(onFinish);
    }

    public void setUpOnResume() {
        onResume = new Image(AssetsManager.getTextureRegion(Constants.RESUME_IMAGE_NAME));
        onResume.setAlign(Align.center);
        onResume.setSize(Constants.ONRESUME_WIDTH, Constants.ONRESUME_HEIGHT);
        onResume.setOrigin(onResume.getWidth() / 2, onResume.getHeight() / 2);
        onResume.setPosition(Constants.WIDTH / 2 - onResume.getWidth() / 2, Constants.HEIGHT / 2 - onResume.getHeight() * 1.5f);
        onResume.addAction(setOnStateImageAction(0.7f));
        stage.addActor(onResume);
    }

    public void setUpPauseButton() {
        pauseButton = new GameButton(Constants.SQUARE_BUTTON_SIZE, Constants.SQUARE_BUTTON_SIZE, "pause", true);
        pauseButton.setPosition(1, Constants.HEIGHT - pauseButton.getHeight() - 1);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (pauseButton.isChecked()) {
                    changeGameState(GameState.PAUSE);
                } else {
                    changeGameState(GameState.RESUME);
                }
            }
        });
        stage.addActor(pauseButton);
    }

    public void setUpPlayButton() {
        playButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "playbtn", false);
        playButton.setPosition(Constants.WIDTH /5  + playButton.getWidth(), Constants.HEIGHT/2 - playButton.getHeight()*1.55f);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                aGame.setScreen(new GameScreen(aGame));
            }
        });
        playButton.setVisible(false);
        stage.addActor(playButton);
    }

    public void setUpHomeButton() {
        homeButton = new GameButton(Constants.LARGE_SQUARE_BUTTON_SIZE, Constants.LARGE_SQUARE_BUTTON_SIZE, "home", false);
        homeButton.setPosition(Constants.WIDTH /3 - homeButton.getWidth()/2, Constants.HEIGHT/2 - homeButton.getHeight()*1.55f);
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                aGame.setScreen(new MenuScreen(aGame));
            }
        });
        homeButton.setVisible(false);
        stage.addActor(homeButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        onScreenClicked(delta);

        stage.draw();

        switch (state) {
            case RESUME:
                if (isFirstClick) {
                    stage.act(delta);
                } else {
                    spinner.act(delta);
                    onResume.act(delta);
                }
                break;
            case PAUSE:
                onPause.act(delta);
                break;
            case RUN:
                stage.act(delta);
                checkForSpinnerState();
                doPhysicsStep(delta);
                break;
            case FINISH:
                stage.act(delta);
                doPhysicsStep(delta);
                break;
        }

        //renderer.render(world, stage.getCamera().combined);
    }

    public void checkForSpinnerState() {
        if (spinner.getSpinnerBodyX() < -1 || spinner.getSpinnerBodyX() > Constants.WIDTH + 1) {
            state = GameState.FINISH;
            changeGameState(GameState.FINISH);
        }
        if (tubeFirst.getTubeBodyX() <= spinner.getSpinnerBodyX() && !tubeFirst.isSpinnerScoreWrited()) {
            tubeFirst.setSpinnerScoreWrited(true);
            scoreLabel.setText("" + ++SCORE);
            return;
        }
        if (tubeSecond.getTubeBodyX() <= spinner.getSpinnerBodyX() && !tubeSecond.isSpinnerScoreWrited()) {
            tubeSecond.setSpinnerScoreWrited(true);
            scoreLabel.setText("" + ++SCORE);
            return;
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

    public void onScreenClicked(float delta) {
        if (Gdx.input.justTouched()) {
            Vector2 screenCoords = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if (pauseButton.getRectangle().contains(screenCoords) || state == GameState.PAUSE) {
                return;
            }
            if (state == GameState.RESUME) {
                changeGameState(GameState.RUN);
            }
            spinner.jump(delta);
        }
    }

    public void changeGameState(GameState state) {
        switch (state) {
            case RESUME:
                this.state = GameState.RESUME;
                onPause.setVisible(false);
                onResume.setVisible(true);
                pauseButton.setVisible(true);
                break;
            case RUN:
                onResume.setVisible(false);
                this.state = GameState.RUN;
                isFirstClick = false;
                break;
            case PAUSE:
                this.state = GameState.PAUSE;
                onPause.setVisible(true);
                onResume.setVisible(false);
                break;
            case FINISH:
                onFinish.setVisible(true);
                pauseButton.setVisible(false);
                playButton.setVisible(true);
                homeButton.setVisible(true);
                if (SCORE > preferences.getMaxScore()){
                    preferences.setMaxScore(SCORE);
                    scoreLabel.setText("New record! \n" + SCORE);
                } else {
                    scoreLabel.setText("Score" + SCORE + '\n' + "Best: " + preferences.getMaxScore());
                }
                preferences.setUserMoney(preferences.getUserMoney() + SCORE);
                break;
        }
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
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("CONTACT");
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public RepeatAction setOnStateImageAction(float duration) {
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.scaleTo(1.3f, 1.3f, duration));
        sequenceAction.addAction(Actions.scaleTo(1f, 1f, duration));
        RepeatAction infiniteLoop = new RepeatAction();
        infiniteLoop.setCount(RepeatAction.FOREVER);
        infiniteLoop.setAction(sequenceAction);
        return infiniteLoop;
    }
}
