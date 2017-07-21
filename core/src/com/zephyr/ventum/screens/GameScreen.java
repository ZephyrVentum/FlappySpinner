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
import com.zephyr.ventum.utils.TextureHolder;
import com.zephyr.ventum.utils.WorldUtils;

/**
 * Created by ZiFir on 08.07.2017.
 */

public class GameScreen implements Screen {

    private Stage stage;
    private Game aGame;
    private World world;
    private Image onPause, onResume;
    private GameButton pauseButton;
    private Label scoreLabel;

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
        aGame = game;
        stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
        world = WorldUtils.createWorld();
        Gdx.input.setInputProcessor(stage);

        state = GameState.RESUME;

        setUpBackground();
        setUpTube();
        setUpGround();
        setUpSpinner();
        setUpPauseButton();
        setUpOnPause();
        setUpOnResume();
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
        scoreLabel.setFontScale(0.15f);
        scoreLabel.setHeight(2f);
        scoreLabel.setPosition(Constants.WIDTH/2 - scoreLabel.getWidth()/2, Constants.HEIGHT *4/5 -scoreLabel.getHeight()/2);
        scoreLabel.setAlignment(Align.center);
        stage.addActor(scoreLabel);
    }

    public void setUpOnPause() {
        onPause = new Image(TextureHolder.getTextureRegion(Constants.PAUSE_IMAGE_NAME));
        onPause.setVisible(false);
        onPause.setSize(Constants.WIDTH, Constants.ONPAUSE_HEIGHT);
        onPause.setPosition(Constants.WIDTH / 2 - onPause.getWidth() / 2, Constants.HEIGHT / 2 - onPause.getHeight() / 2);
        onPause.setOrigin(onPause.getWidth()/2, onPause.getHeight()/2);
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.scaleTo(1.3f,1.3f,1.2f));
        sequenceAction.addAction(Actions.scaleTo(1f,1f,1.2f));
        RepeatAction infiniteLoop = new RepeatAction();
        infiniteLoop.setCount(RepeatAction.FOREVER);
        infiniteLoop.setAction(sequenceAction);
        onPause.addAction(infiniteLoop);
        stage.addActor(onPause);
    }

    public void setUpOnResume(){
        onResume = new Image(TextureHolder.getTextureRegion(Constants.RESUME_IMAGE_NAME));
        onResume.setAlign(Align.center);
        onResume.setSize(Constants.ONRESUME_WIDTH,Constants.ONRESUME_HEIGHT);
        onResume.setOrigin(onResume.getWidth()/2, onResume.getHeight()/2);
        onResume.setPosition(Constants.WIDTH/2 - onResume.getWidth()/2, Constants.HEIGHT/2 - onResume.getHeight()*1.5f);
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.scaleTo(1.3f,1.3f,0.7f));
        sequenceAction.addAction(Actions.scaleTo(1f,1f,0.7f));
        RepeatAction infiniteLoop = new RepeatAction();
        infiniteLoop.setCount(RepeatAction.FOREVER);
        infiniteLoop.setAction(sequenceAction);
        onResume.addAction(infiniteLoop);
        stage.addActor(onResume);
    }

    public void setUpPauseButton() {
        pauseButton = new GameButton(Constants.SQUARE_BUTTON_SIZE, Constants.SQUARE_BUTTON_SIZE, "pause", true);
        pauseButton.setPosition(1, Constants.HEIGHT - pauseButton.getHeight() - 1);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (pauseButton.isChecked()) {
                    state = GameState.PAUSE;
                    onPause.setVisible(true);
                    onResume.setVisible(false);
                } else {
                    state = GameState.RESUME;
                    onPause.setVisible(false);
                    onResume.setVisible(true);
                }
            }
        });
        stage.addActor(pauseButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        onScreenClicked(delta);

        stage.draw();

        switch (state){
            case RESUME:
                if(isFirstClick) {
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
                checkForScore();
                stage.act(delta);
                doPhysicsStep(delta);
                break;
        }

        //renderer.render(world, stage.getCamera().combined);
    }

    public void checkForScore(){
        if (tubeFirst.getTubeBodyX() <= spinner.getSpinnerBodyX() && !tubeFirst.isSpinnerScoreWrited()){
            tubeFirst.setSpinnerScoreWrited(true);
            scoreLabel.setText("" + ++SCORE);
        }
        if (tubeSecond.getTubeBodyX() <= spinner.getSpinnerBodyX() && !tubeSecond.isSpinnerScoreWrited()){
            tubeSecond.setSpinnerScoreWrited(true);
            scoreLabel.setText("" + ++SCORE);
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
            if (state == GameState.RESUME){
                onResume.setVisible(false);
                state = GameState.RUN;
                isFirstClick = false;
            }
            spinner.jump(delta);
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
}
