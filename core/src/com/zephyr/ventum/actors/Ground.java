package com.zephyr.ventum.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.TextureHolder;

/**
 * Created by sashaklimenko on 7/13/17.
 */

public class Ground extends BaseActor {

    private TextureRegion textureRegion;
    private Vector2 groundPositions1, groundPositions2;
    private Body body;

    private static final int SPEED = 120;

    public Ground(Body body) {
        this.body = body;
        textureRegion = TextureHolder.getTextureRegion(Constants.GROUND_IMAGE_NAME);
        groundPositions1 = new Vector2(0, 0);
        groundPositions2 = new Vector2(Constants.WIDTH, 0);
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
        groundPositions1.x = groundPositions2.x;
        groundPositions2.x = Constants.WIDTH + delta*SPEED;
    }

    private void updateBackgroundPositions(float delta) {
        groundPositions1.x += delta * SPEED;
        groundPositions2.x += delta * SPEED;
    }

    private boolean isLeftSideReached(float delta) {
        return ((groundPositions2.x - delta * SPEED) <= 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, groundPositions1.x, groundPositions1.y, transformToScreen(Constants.GROUND_SIZE.x),transformToScreen(Constants.GROUND_SIZE.y));
        batch.draw(textureRegion, groundPositions2.x, groundPositions2.y, transformToScreen(Constants.GROUND_SIZE.x),transformToScreen(Constants.GROUND_SIZE.y));
    }
}
