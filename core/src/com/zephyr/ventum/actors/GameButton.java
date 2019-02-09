package com.zephyr.ventum.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.zephyr.ventum.utils.AssetsManager;
import com.zephyr.ventum.utils.AudioManager;

/**
 * Created by sashaklimenko on 7/17/17.
 */

public class GameButton extends Button {

    private Rectangle rectangle;
    private AudioManager audioManager;
    private Skin skin;

    public GameButton(float WIDTH, float HEIGHT, String drawable, boolean isCheckable) {
        this(WIDTH, HEIGHT, drawable, null, isCheckable);
    }

    public GameButton(float WIDTH, float HEIGHT, String drawable, String pressedDrawable, boolean isCheckable) {
        audioManager = AudioManager.getInstance();
        skin = new Skin();
        skin.addRegions(AssetsManager.getTextureAtlas());

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        if (pressedDrawable != null) {
            buttonStyle.up = new TextureRegionDrawable(AssetsManager.getTextureRegion(drawable));
            buttonStyle.down = new TextureRegionDrawable(AssetsManager.getTextureRegion(pressedDrawable));
            if (isCheckable) {
                buttonStyle.checked = skin.getDrawable(drawable + "_pressed");
            }
        } else {
            buttonStyle.up = skin.getDrawable(drawable);
            buttonStyle.down = skin.getDrawable(drawable + "_pressed");
            if (isCheckable) {
                buttonStyle.checked = skin.getDrawable(drawable + "_pressed");
            }
        }
        setStyle(buttonStyle);
        setSize(WIDTH, HEIGHT);

        rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioManager.playSound(audioManager.getButtonSound());
            }
        });
    }

    public void changeDrawable(String drawable) {
        ButtonStyle buttonStyle = getStyle();
        buttonStyle.up = skin.getDrawable(drawable);
        buttonStyle.down = skin.getDrawable(drawable + "_pressed");
        setStyle(buttonStyle);
    }

    public Rectangle getRectangle() {
        rectangle.set(getX(), getY(), getWidth(), getHeight());
        return rectangle;
    }
}
