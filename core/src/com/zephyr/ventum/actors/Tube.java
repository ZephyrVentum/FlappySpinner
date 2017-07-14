package com.zephyr.ventum.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.TextureHolder;
import com.zephyr.ventum.utils.WorldUtils;

/**
 * Created by sashaklimenko on 7/14/17.
 */

public class Tube extends BaseActor {

    private TextureRegion topTubeTexture, bottomTubeTexture;
    private Body topTubeBody, bottomTubeBody;

    public Tube(Array<Body> bodies) {

        topTubeTexture = TextureHolder.getTextureRegion(Constants.TOP_TUBE_NAME);
        bottomTubeTexture = TextureHolder.getTextureRegion(Constants.BOTTOM_TUBE_NAME);

        topTubeBody = bodies.first();//first item
        bottomTubeBody = bodies.peek();//last item
    }

    @Override
    public void act(float delta) {
        if (isLeftSideReached()) {
            resetTubePositions();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(topTubeTexture,
                topTubeBody.getPosition().x - Constants.TUBE_WIDTH/2,
                topTubeBody.getPosition().y,
                Constants.TUBE_WIDTH,
                30);
        batch.draw(bottomTubeTexture,
                bottomTubeBody.getPosition().x - Constants.TUBE_WIDTH/2,
                bottomTubeBody.getPosition().y,
                Constants.TUBE_WIDTH,
                topTubeBody.getPosition().y -Constants.TUBE_SPACING - 30);
    }

    private void resetTubePositions() {
        topTubeBody.setTransform(Constants.WIDTH + Constants.TUBE_WIDTH, WorldUtils.generateTubePosition(), 0);
        bottomTubeBody.setTransform(Constants.WIDTH + Constants.TUBE_WIDTH, topTubeBody.getPosition().y - Constants.TUBE_SPACING - 30, 0);
    }

    private boolean isLeftSideReached() {
        return ((topTubeBody.getPosition().x + Constants.TUBE_WIDTH) <= 0);
    }

}
