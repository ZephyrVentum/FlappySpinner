package com.zephyr.ventum.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zephyr.ventum.actors.Background;
import com.zephyr.ventum.actors.GameButton;
import com.zephyr.ventum.models.Skin;
import com.zephyr.ventum.utils.AssetsManager;
import com.zephyr.ventum.utils.Constants;

import java.util.ArrayList;

/**
 * Created by sashaklimenko on 7/26/17.
 */

public class MarketScreen implements Screen {

    private Game aGame;
    private GameButton nextButton, previousButton;
    private Image skinImage, skinImageRotation;
    private Stage stage;
    private ArrayList<Skin> skins = new ArrayList<Skin>();

    private int position = 0;

    public MarketScreen(Game aGame) {
        this.aGame = aGame;
        stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
        Gdx.input.setInputProcessor(stage);

        initSkins();

        setUpBackground();
        setUpSkinImages();
        setUpNextButton();
        setUpPreviousButton();
        setUpMoney();
    }

    public void initSkins() {
        for (int i = 0; i < Constants.SKIN_COUNT; i++) {
            skins.add(new Skin(
                    Constants.SPINNER_SKINES[i],
                    Constants.SKINS_NAMES[i],
                    AssetsManager.getTextureRegion(Constants.SPINNER_SKINES[i]),
                    10));
        }
    }

    public void setUpBackground() {
        Background background = new Background();
        stage.addActor(background);
    }

    public void setUpSkinImages() {
        skinImage = new Image(skins.get(position).getTextureRegion());
        skinImageRotation = new Image(skins.get(position).getTextureRegion());

        skinImage.setSize(3f, 3f);
        skinImage.setOrigin(skinImage.getWidth() / 2, skinImage.getHeight() / 2);
        skinImage.setPosition(Constants.WIDTH / 4 - skinImage.getWidth() / 2, Constants.HEIGHT / 2);

        skinImageRotation.setSize(3f, 3f);
        skinImageRotation.setOrigin(skinImageRotation.getWidth() / 2, skinImageRotation.getHeight() / 2);
        skinImageRotation.setPosition(Constants.WIDTH * 3 / 4 - skinImageRotation.getWidth() / 2, Constants.HEIGHT / 2);

        SequenceAction rotateAction = new SequenceAction();
        rotateAction.addAction(Actions.rotateBy(360, 0.5f, Interpolation.linear));
        RepeatAction infiniteLoop = new RepeatAction();
        infiniteLoop.setCount(RepeatAction.FOREVER);
        infiniteLoop.setAction(rotateAction);

        skinImageRotation.addAction(infiniteLoop);
        stage.addActor(skinImageRotation);
        stage.addActor(skinImage);
    }

    public void setUpNextButton() {
        nextButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "playbtn", false);
        nextButton.setPosition(Constants.WIDTH * 3 / 4 - nextButton.getWidth() * 3 / 5,
                Constants.HEIGHT / 2 - nextButton.getHeight() * 2.5f);
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(position == Constants.SKIN_COUNT -1){
                    position = 0;
                } else {
                    position++;
                }
                changeShownSkin();
            }
        });
        stage.addActor(nextButton);
    }


    public void setUpPreviousButton() {
        previousButton = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "previous", false);
        previousButton.setPosition(Constants.WIDTH / 4 - previousButton.getWidth() * 2 / 5,
                Constants.HEIGHT / 2 - previousButton.getHeight() * 2.5f);
        previousButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (position == 0){
                    position = Constants.SKIN_COUNT -1;
                } else {
                    position--;
                }
                changeShownSkin();
            }
        });
        stage.addActor(previousButton);
    }

    public void changeShownSkin(){
        skinImage.setDrawable(new TextureRegionDrawable(skins.get(position).getTextureRegion()));
        skinImageRotation.setDrawable(skinImage.getDrawable());
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
