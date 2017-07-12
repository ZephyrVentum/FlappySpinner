package com.zephyr.ventum.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.WorldUtils;

/**
 * Created by ZiFir on 08.07.2017.
 */

public class GameScreen implements Screen {

    private Stage stage;
    private Game aGame;
    private World world;

    public GameScreen(Game game) {
        Box2D.init();
        Gdx.input.setInputProcessor(stage);
        aGame = game;
        stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));

        world = WorldUtils.createWorld();

        Image spinner = new Image(new Texture(Gdx.files.internal("spinner.png")));
        spinner.setOrigin(spinner.getWidth() / 2, spinner.getHeight() / 2);
        spinner.setPosition(Constants.WIDTH/2, Constants.HEIGHT/2);
        SequenceAction rotateAction = new SequenceAction();
        rotateAction.addAction(Actions.rotateBy(360,0.5f, Interpolation.linear));

        RepeatAction infiniteLoop = new RepeatAction();
        infiniteLoop.setCount(RepeatAction.FOREVER);
        infiniteLoop.setAction(rotateAction);
        spinner.addAction(infiniteLoop);
        stage.addActor(spinner);
        //stage.addAction(Actions.fadeOut(1));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        world.step(delta, 6, 2);
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
