package com.zephyr.ventum.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.TextureHolder;

/**
 * Created by sashaklimenko on 7/13/17.
 */

public class Ground extends BaseActor {

    private TextureRegion textureRegionGround, textureRegionSky;
    private Vector2 groundPositions1, groundPositions2;
    private Body ground, sky;

    private static final float SPEED = Constants.TUBE_SPEED;

    public Ground(Array<Body> bodies) {

        ground = bodies.first();//first item
        sky = bodies.peek();//last item

        textureRegionGround = TextureHolder.getTextureRegion(Constants.GROUND_IMAGE_NAME);
        textureRegionSky = TextureHolder.getTextureRegion(Constants.SKY_IMAGE_NAME);

        groundPositions1 = new Vector2(0, 0);
        groundPositions2 = new Vector2(Constants.WIDTH, 0);
    }

    @Override
    public void act(float delta) {
        if (isLeftSideReached(-delta)){
            resetBackgroundPositions(delta);
        } else {
            updateBackgroundPositions(delta);
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
        batch.draw(textureRegionGround, groundPositions1.x, groundPositions1.y, Constants.GROUND_SIZE.x, Constants.GROUND_SIZE.y);
        batch.draw(textureRegionGround, groundPositions2.x, groundPositions2.y, Constants.GROUND_SIZE.x, Constants.GROUND_SIZE.y);

        batch.draw(textureRegionSky, groundPositions1.x, sky.getPosition().y - Constants.GROUND_SIZE.y + 1, Constants.GROUND_SIZE.x, Constants.GROUND_SIZE.y - 1);
        batch.draw(textureRegionSky, groundPositions2.x, sky.getPosition().y - Constants.GROUND_SIZE.y + 1, Constants.GROUND_SIZE.x, Constants.GROUND_SIZE.y - 1);
    }
}
