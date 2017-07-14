package com.zephyr.ventum.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zephyr.ventum.actors.Background;
import com.zephyr.ventum.actors.Ground;
import com.zephyr.ventum.actors.Spinner;
import com.zephyr.ventum.actors.Tube;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.WorldUtils;

/**
 * Created by ZiFir on 08.07.2017.
 */

public class GameScreen implements Screen {

    private Stage stage;
    private Game aGame;
    private World world;

    private Spinner spinner;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;
    private Box2DDebugRenderer renderer = new Box2DDebugRenderer();
    ;


    public GameScreen(Game game) {
        Box2D.init();
        Gdx.input.setInputProcessor(stage);
        aGame = game;
        stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera(Constants.WIDTH, Constants.HEIGHT)));

        world = WorldUtils.createWorld();
        setUpBackground();
        setUpTube();
        setUpGround();
        setUpSpinner();


        //stage.addAction(Actions.fadeOut(1));
    }

    public void setUpTube() {
        Tube tubeFirst = new Tube(WorldUtils.createTopTube(world, Constants.WIDTH * 1.5f));
        Tube tubeSecond = new Tube(WorldUtils.createTopTube(world, Constants.WIDTH * 2 + Constants.TUBE_WIDTH));
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
        Ground ground = new Ground(WorldUtils.createGround(world));
        stage.addActor(ground);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        onScreenClicked(delta);

        stage.act(delta);
        stage.draw();

        doPhysicsStep(delta);
        renderer.render(world, stage.getCamera().combined);
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
