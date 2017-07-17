package com.zephyr.ventum.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.TextureHolder;

/**
 * Created by sashaklimenko on 7/12/17.
 */

public class Background extends Actor {

    private static final int SPEED = 4;

    private TextureRegion textureRegion;
    private Vector2 backgroundPosition1, backgroundPosition2;

    public Background() {
        textureRegion = TextureHolder.getTextureRegion(Constants.BACKGROUND_IMAGE_NAME);
        backgroundPosition1 = new Vector2(0, 0);
        backgroundPosition2 = new Vector2(Constants.WIDTH, 0);
    }

    @Override
    public void act(float delta) {
        if (isLeftSideReached(delta)){
            resetBackgroundPositions(-delta);
        } else {
            updateBackgroundPositions(-delta);
        }
    }

    private void resetBackgroundPositions(float delta){
        backgroundPosition1.x = backgroundPosition2.x;
        backgroundPosition2.x = Constants.WIDTH + delta*SPEED;
    }

    private void updateBackgroundPositions(float delta) {
        backgroundPosition1.x += delta * SPEED;
        backgroundPosition2.x += delta * SPEED;
    }

    private boolean isLeftSideReached(float delta) {
        return ((backgroundPosition2.x - delta * SPEED) <= 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, backgroundPosition1.x, backgroundPosition1.y, Constants.WIDTH, Constants.HEIGHT);
        batch.draw(textureRegion, backgroundPosition2.x, backgroundPosition2.y, Constants.WIDTH, Constants.HEIGHT);
    }
}
