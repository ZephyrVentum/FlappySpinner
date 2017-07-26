package com.zephyr.ventum.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zephyr.ventum.actors.Background;
import com.zephyr.ventum.actors.GameButton;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.WorldUtils;

/**
 * Created by sashaklimenko on 7/26/17.
 */

public class MarketScreen implements Screen {

    private Game aGame;
    private GameButton nextButton,previousButton;
    private Stage stage;

    public MarketScreen(Game aGame) {
        this.aGame = aGame;
        stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
        Gdx.input.setInputProcessor(stage);


        setUpBackground();
        setUpSpinnerSkin();
        setUpNextButton();
        setUpPreviousButton();
        setUpMoney();
    }

    public void setUpBackground() {
        Background background = new Background();
        stage.addActor(background);
    }

    public void setUpSpinnerSkin() {
        Image spinner = new Image(new Texture(Gdx.files.internal("silver_skin.png")));
        spinner.setScale(0.18f);
        spinner.setOrigin(spinner.getWidth() / 2, spinner.getHeight() / 2);
        spinner.setPosition(Constants.WIDTH / 2 - spinner.getWidth() / 2, Constants.HEIGHT/2 - spinner.getHeight() / 2);
        SequenceAction rotateAction = new SequenceAction();
        rotateAction.addAction(Actions.rotateBy(360, 0.5f, Interpolation.linear));
        RepeatAction infiniteLoop = new RepeatAction();
        infiniteLoop.setCount(RepeatAction.FOREVER);
        infiniteLoop.setAction(rotateAction);
        spinner.addAction(infiniteLoop);
        stage.addActor(spinner);
    }

    public void setUpNextButton() {
        nextButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "playbtn", false);
        nextButton.setPosition(Constants.WIDTH / 4 - nextButton.getWidth() * 2 / 5,
                Constants.HEIGHT / 2 - nextButton.getHeight() * 2.5f);
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        stage.addActor(nextButton);
    }


    public void setUpPreviousButton() {
        previousButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "playbtn", false);
        previousButton.setPosition(Constants.WIDTH * 3 / 4 - previousButton.getWidth() * 3 / 5,
                Constants.HEIGHT / 2 - previousButton.getHeight() * 2.5f);
        previousButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        stage.addActor(previousButton);
    }

    public void setUpMoney() {

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
