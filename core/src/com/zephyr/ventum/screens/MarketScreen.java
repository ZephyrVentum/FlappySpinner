package com.zephyr.ventum.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zephyr.ventum.actors.Background;
import com.zephyr.ventum.actors.GameButton;
import com.zephyr.ventum.models.Skin;
import com.zephyr.ventum.utils.AssetsManager;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.GamePreferences;

import java.util.ArrayList;

/**
 * Created by sashaklimenko on 7/26/17.
 */

public class MarketScreen implements Screen {

    private Game aGame;
    private GameButton nextButton, previousButton, homeButton, buyBottom, useBottom;
    private Label moneyLabel, skinLabel, priceLabel;
    private Image skinImage, skinImageRotation, imageCoinPrice;
    private Stage stage;
    private GamePreferences preferences;
    private ArrayList<Skin> skins = new ArrayList<Skin>();

    private int position = 0;

    public MarketScreen(Game aGame) {
        this.aGame = aGame;
        preferences = new GamePreferences();
        stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
        Gdx.input.setInputProcessor(stage);

        initSkins();

        setUpBackground();
        setUpHomeButton();
        setUpSkinImages();
        setUpNextButton();
        setUpPreviousButton();
        setUpUseButton();
        setUpBuyButton();
        setUpLabels();

        changeShownSkin();
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
        skinImage.setPosition(Constants.WIDTH / 3 - skinImage.getWidth() / 2, Constants.HEIGHT / 2);

        skinImageRotation.setSize(3f, 3f);
        skinImageRotation.setOrigin(skinImageRotation.getWidth() / 2, skinImageRotation.getHeight() / 2);
        skinImageRotation.setPosition(Constants.WIDTH * 2 / 3 - skinImageRotation.getWidth() / 2, Constants.HEIGHT / 2);

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
                Constants.HEIGHT / 2 - nextButton.getHeight() * 3.5f);
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (position == Constants.SKIN_COUNT - 1) {
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
                Constants.HEIGHT / 2 - previousButton.getHeight() * 3.5f);
        previousButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (position == 0) {
                    position = Constants.SKIN_COUNT - 1;
                } else {
                    position--;
                }
                changeShownSkin();
            }
        });
        stage.addActor(previousButton);
    }

    public void setUpUseButton(){
        useBottom = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "use", true);
        useBottom.setPosition(Constants.WIDTH/2 - useBottom.getWidth()/2,
                skinImage.getY() - useBottom.getHeight() - 2f);
        useBottom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                preferences.setCurrentSkin(skins.get(position).getName());
                updateShownSkin();
            }
        });
        useBottom.setVisible(false);
        stage.addActor(useBottom);
    }

    public void setUpBuyButton(){
        buyBottom = new GameButton(Constants.RECTANGLE_BUTTON_WIDTH, Constants.RECTANGLE_BUTTON_HEIGHT, "market", false);
        buyBottom.setPosition(Constants.WIDTH*2/3 - buyBottom.getWidth()/2,
                skinImage.getY() - buyBottom.getHeight() - 2f);
        buyBottom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(preferences.getUserMoney() >= skins.get(position).getPrice()){
                    preferences.setUserMoney(preferences.getUserMoney() - skins.get(position).getPrice());
                    preferences.setSkinBought(skins.get(position).getName());
                    updateShownSkin();
                }
            }
        });
        buyBottom.setVisible(false);
        stage.addActor(buyBottom);
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

    public void changeShownSkin() {
        skinImage.setDrawable(new TextureRegionDrawable(skins.get(position).getTextureRegion()));
        skinImageRotation.setDrawable(skinImage.getDrawable());
        skinLabel.setText(skins.get(position).getLabel());
        priceLabel.setText("" + skins.get(position).getPrice());
        updateShownSkin();
    }

    public void updateShownSkin(){
        moneyLabel.setText("" + preferences.getUserMoney());
        if(preferences.isSkinBougth(skins.get(position).getName())){
            buyBottom.setVisible(false);
            imageCoinPrice.setVisible(false);
            priceLabel.setVisible(false);
            useBottom.setVisible(true);
        } else {
            buyBottom.setVisible(true);
            imageCoinPrice.setVisible(true);
            priceLabel.setVisible(true);
            useBottom.setVisible(false);
        }

        if (skins.get(position).getName().equals(preferences.getCurrentSkin())){
            useBottom.setChecked(true);
        } else {
            useBottom.setChecked(false);
        }
    }

    public void setUpLabels() {
        setUpSkinNameLabel();
        setUpMoneyLabel();
        setUpPriceLabel();
    }

    public void setUpPriceLabel(){
        imageCoinPrice = new Image(AssetsManager.getTextureRegion(Constants.COIN_NAME));
        imageCoinPrice.setSize(2.5f, 2.5f);
        imageCoinPrice.setPosition(Constants.WIDTH/3, skinImage.getY() - imageCoinPrice.getHeight()*1.25f - 2f);
        stage.addActor(imageCoinPrice);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getMediumFont();
        priceLabel = new Label("" + skins.get(position).getPrice(), labelStyle);
        priceLabel.setFontScale(0.065f);
        priceLabel.setSize(priceLabel.getWidth() * priceLabel.getFontScaleX(),priceLabel.getHeight() * priceLabel.getFontScaleY());
        priceLabel.setPosition(imageCoinPrice.getX() - priceLabel.getWidth(), skinImage.getY() - imageCoinPrice.getHeight()*1.25f  - 2f);
        stage.addActor(priceLabel);
    }

    public void setUpMoneyLabel(){
        Image imageCoin = new Image(AssetsManager.getTextureRegion(Constants.COIN_NAME));
        imageCoin.setSize(2.5f, 2.5f);
        imageCoin.setPosition(Constants.WIDTH - imageCoin.getWidth() - 1f, Constants.HEIGHT - imageCoin.getHeight() - 1f);
        stage.addActor(imageCoin);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getMediumFont();
        moneyLabel = new Label("" + preferences.getUserMoney(), labelStyle);
        moneyLabel.setFontScale(0.065f);
        moneyLabel.setSize(moneyLabel.getWidth() * moneyLabel.getFontScaleX(),moneyLabel.getHeight() * moneyLabel.getFontScaleY());
        moneyLabel.setPosition(imageCoin.getX() - moneyLabel.getWidth(), imageCoin.getY());
        stage.addActor(moneyLabel);
    }

    public void setUpSkinNameLabel(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getLargeFont();
        skinLabel = new Label(skins.get(position).getLabel(), labelStyle);
        skinLabel.setFontScale(0.065f);
        skinLabel.setSize(Constants.WIDTH, Constants.LARGE_FONT_SIZE * skinLabel.getFontScaleY() + 1);
        skinLabel.setAlignment(Align.center);
        skinLabel.setPosition(0, Constants.HEIGHT*2/3);

        stage.addActor(skinLabel);
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
