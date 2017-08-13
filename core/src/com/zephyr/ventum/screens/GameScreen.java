package com.zephyr.ventum.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
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
import com.zephyr.ventum.FlappySpinner;
import com.zephyr.ventum.enums.GameState;
import com.zephyr.ventum.actors.Background;
import com.zephyr.ventum.actors.GameButton;
import com.zephyr.ventum.actors.Ground;
import com.zephyr.ventum.actors.Spinner;
import com.zephyr.ventum.actors.Tube;
import com.zephyr.ventum.utils.AudioManager;
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
    private Image onPause, onResume, onFinish, moneyImage;
    private GameButton pauseButton, playButton, homeButton, videoButton;
    private Label scoreLabel, moneyLabel, bonusLabel;
    private GamePreferences preferences;
    private AudioManager audioManager;

    private GameState state;

    private Ground ground;
    private Tube tubeFirst, tubeSecond;

    private int SCORE = 0;

    private boolean isFirstClick = true;
    private boolean isRelife;

    private Spinner spinner;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;
    private Box2DDebugRenderer renderer = new Box2DDebugRenderer();


    public GameScreen(Game game, int score, boolean isRelife) {
        Box2D.init();

        SCORE = score;
        this.isRelife = isRelife;

        preferences = new GamePreferences();
        audioManager = AudioManager.getInstance();

        aGame = game;
        stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
        world = WorldUtils.createWorld();
        world.setContactListener(this);

        FlappySpinner.gameManager.changeBackgroundColor("#e9fcd9");

        Gdx.input.setInputProcessor(stage);

        state = GameState.RESUME;

        setUpBackground();
        setUpTube();
        setUpSpinner();
        setUpGround();

        setUpOnPause();
        setUpOnResume();
        setUpOnFinish();

        setUpButtons();
        setUpLabels();
    }

    /**
     * SETTING GAME ACTORS
     */
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

    /**
     * SETTING ONSTATE IMAGES
     */
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
        onFinish.setSize(Constants.WIDTH - 5, Constants.ONFINISH_HEIGHT);
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

    /**
     * SETTING LABELS
     */
    public void setUpLabels() {
        setUpScoreLabel();
        setUpMoneyLabel();
        setUpBonusLabel();
    }

    public void setUpMoneyLabel() {
        moneyImage = new Image(AssetsManager.getTextureRegion(Constants.COIN_NAME));
        moneyImage.setSize(2.5f, 2.5f);
        moneyImage.setPosition(Constants.WIDTH * 2 / 3, onFinish.getY() + onFinish.getHeight() * 1.3f + moneyImage.getHeight() / 5.5f);
        moneyImage.setOrigin(moneyImage.getWidth() / 2, moneyImage.getHeight() / 2);
        moneyImage.setVisible(false);
        stage.addActor(moneyImage);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getMediumFont();
        moneyLabel = new Label(" " + 0, labelStyle);
        moneyLabel.setFontScale(0.065f);
        moneyLabel.setSize(moneyLabel.getWidth() * moneyLabel.getFontScaleX(), moneyLabel.getHeight() * moneyLabel.getFontScaleY());
        moneyLabel.setPosition(moneyImage.getX() - moneyLabel.getWidth(), moneyImage.getY());
        moneyLabel.setVisible(false);
        stage.addActor(moneyLabel);
    }

    public void setUpScoreLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getMediumFont();
        scoreLabel = new Label("" + SCORE, labelStyle);
        scoreLabel.setFontScale(0.065f);
        scoreLabel.setSize(scoreLabel.getWidth() * scoreLabel.getFontScaleX(), scoreLabel.getHeight() * scoreLabel.getFontScaleY());
        scoreLabel.setPosition(Constants.WIDTH / 2 - scoreLabel.getWidth() / 2, Constants.HEIGHT * 4 / 5 - scoreLabel.getHeight() / 2);
        scoreLabel.setAlignment(Align.center);
        stage.addActor(scoreLabel);
    }

    public void setUpBonusLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getMediumFont();
        bonusLabel = new Label("extra life", labelStyle);
        bonusLabel.setFontScale(0.065f);
        bonusLabel.setSize(bonusLabel.getWidth() * bonusLabel.getFontScaleX(), bonusLabel.getHeight() * bonusLabel.getFontScaleY());
        bonusLabel.setPosition(Constants.WIDTH / 4 - bonusLabel.getWidth() * 2 / 5, Constants.HEIGHT / 2 - Constants.RECTANGLE_BUTTON_HEIGHT * 1.5f * 1.65f);
        bonusLabel.setAlignment(Align.center);
        bonusLabel.setVisible(false);
        stage.addActor(bonusLabel);
    }

    /**
     * SETTING BUTTONS
     */
    public void setUpButtons() {
        setUpPauseButton();
        setUpPlayButton();
        setUpHomeButton();
        setUpVideoButton();
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
        playButton.setPosition(Constants.WIDTH * 3 / 4 - playButton.getWidth() * 3 / 5, Constants.HEIGHT / 2 - playButton.getHeight() * 1.65f);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                aGame.setScreen(new GameScreen(aGame, 0, false));
            }
        });
        playButton.setVisible(false);
        if (isRelife) {
            playButton.addAction(Actions.moveTo(Constants.WIDTH / 2 - playButton.getWidth() / 2, playButton.getY()));
        }
        stage.addActor(playButton);
    }

    public void setUpVideoButton() {
        videoButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "video", false);
        videoButton.setPosition(Constants.WIDTH / 4 - videoButton.getWidth() * 2 / 5, Constants.HEIGHT / 2 - videoButton.getHeight() * 1.65f);
        videoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                aGame.setScreen(new GameScreen(aGame, SCORE, true));
            }
        });
        videoButton.setVisible(false);
        stage.addActor(videoButton);
    }

    public void setUpHomeButton() {
        homeButton = new GameButton(Constants.SQUARE_BUTTON_SIZE - 0.4f, Constants.SQUARE_BUTTON_SIZE - 0.4f, "home", false);
        homeButton.setPosition(1, Constants.HEIGHT - homeButton.getHeight() - 1);
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                aGame.setScreen(new MenuScreen(aGame));
            }
        });
        homeButton.setVisible(false);
        stage.addActor(homeButton);
    }

    /**
     * GAME CONTROLLER
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        onScreenClicked();

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
            audioManager.playSound(audioManager.getScoreSound());
            return;
        }
        if (tubeSecond.getTubeBodyX() <= spinner.getSpinnerBodyX() && !tubeSecond.isSpinnerScoreWrited()) {
            tubeSecond.setSpinnerScoreWrited(true);
            scoreLabel.setText("" + ++SCORE);
            audioManager.playSound(audioManager.getScoreSound());
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

    public void onScreenClicked() {
        if (Gdx.input.justTouched()) {
            Vector2 screenCoords = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if (pauseButton.getRectangle().contains(screenCoords) || state == GameState.PAUSE || state == GameState.FINISH) {
                return;
            }
            if (state == GameState.RESUME) {
                changeGameState(GameState.RUN);
            }
            spinner.jump();
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
                audioManager.playSound(audioManager.getGameoverSound());
                onFinish.setVisible(true);
                pauseButton.setVisible(false);
                playButton.setVisible(true);
                if (!isRelife) {
                    videoButton.setVisible(true);
                    bonusLabel.setVisible(true);
                }
                homeButton.setVisible(true);
                moneyLabel.setText(" " + SCORE);
                moneyLabel.setVisible(true);
                moneyImage.setVisible(true);
                moneyImage.setX(moneyLabel.getX() + moneyLabel.getText().length + 0.7f);
                moneyImage.addAction(Actions.rotateBy(22.5f));
                SequenceAction sequenceAction = new SequenceAction();
                sequenceAction.addAction(Actions.rotateBy(-45, 0.4f, Interpolation.linear));
                sequenceAction.addAction(Actions.rotateBy(45, 0.4f, Interpolation.linear));
                RepeatAction infiniteLoop = new RepeatAction();
                infiniteLoop.setCount(RepeatAction.FOREVER);
                infiniteLoop.setAction(sequenceAction);
                moneyImage.addAction(infiniteLoop);
                if (SCORE > preferences.getMaxScore()) {
                    preferences.setMaxScore(SCORE);
                    scoreLabel.setText(" New record! \n" + "Score:" + SCORE);
                } else {
                    scoreLabel.setText("Score:" + SCORE + '\n' + "Best:" + preferences.getMaxScore());
                }
                scoreLabel.addAction(Actions.moveTo(Constants.WIDTH / 3 - 1 + scoreLabel.getWidth() / 2, onFinish.getY() + onFinish.getHeight() * 1.3f + 0.1f, 0.6f, Interpolation.linear));
                preferences.setUserMoney(preferences.getUserMoney() + SCORE);
                break;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if (state != GameState.FINISH) {
            pauseButton.setChecked(true);
        }
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
        stage.dispose();
    }

    @Override
    public void beginContact(Contact contact) {
        if (state != GameState.PAUSE && state != GameState.FINISH) {
            audioManager.playSound(audioManager.getHitSound());
        }
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
