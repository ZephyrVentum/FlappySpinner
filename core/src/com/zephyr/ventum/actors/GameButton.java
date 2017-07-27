package com.zephyr.ventum.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.zephyr.ventum.utils.AssetsManager;

/**
 * Created by sashaklimenko on 7/17/17.
 */

public class GameButton extends Button {

    private Rectangle rectangle;

    public GameButton(float WIDTH, float HEIGHT, String drawable, boolean isCheckable) {

        Skin skin = new Skin();
        skin.addRegions(AssetsManager.getTextureAtlas());

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = skin.getDrawable(drawable);
        buttonStyle.down = skin.getDrawable(drawable + "_pressed");
        if (isCheckable) {
            buttonStyle.checked = skin.getDrawable(drawable + "_pressed");
        }
        setStyle(buttonStyle);
        setSize(WIDTH, HEIGHT);

        rectangle = new Rectangle(getX(),getY(),getWidth(),getHeight());
    }



    public Rectangle getRectangle() {
        rectangle.set(getX(),getY(),getWidth(),getHeight());
        return rectangle;
    }
}
