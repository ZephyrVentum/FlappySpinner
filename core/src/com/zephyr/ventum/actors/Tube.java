package com.zephyr.ventum.actors;

import com.badlogic.gdx.Gdx;
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
        float deltaSpeed = Gdx.graphics.getDeltaTime() * Constants.TUBE_SPEED;

        batch.draw(topTubeTexture,
                topTubeBody.getPosition().x  - Constants.TUBE_WIDTH/2 + deltaSpeed,
                topTubeBody.getPosition().y - Constants.TUBE_HEIGHT/2,
                Constants.TUBE_WIDTH,
                Constants.TUBE_HEIGHT);

        batch.draw(bottomTubeTexture,
                bottomTubeBody.getPosition().x - Constants.TUBE_WIDTH/2 + deltaSpeed,
                bottomTubeBody.getPosition().y - Constants.TUBE_HEIGHT/2 ,
                Constants.TUBE_WIDTH,
                Constants.TUBE_HEIGHT);
    }

    private void resetTubePositions() {
        topTubeBody.setTransform(Constants.WIDTH + Constants.TUBE_WIDTH,
                WorldUtils.generateTubePosition(),
                0);
        bottomTubeBody.setTransform(Constants.WIDTH + Constants.TUBE_WIDTH,
                topTubeBody.getPosition().y - Constants.TUBE_SPACING - Constants.TUBE_HEIGHT,
                0);
    }

    public void startMove(){
        topTubeBody.setLinearVelocity(Constants.TUBE_SPEED, 0.0f);
        bottomTubeBody.setLinearVelocity(Constants.TUBE_SPEED, 0.0f);
    }

    public void stopMove(){
        topTubeBody.setLinearVelocity(0.0f, 0.0f);
        bottomTubeBody.setLinearVelocity(0.0f, 0.0f);
    }

    private boolean isLeftSideReached() {
        return ((topTubeBody.getPosition().x + Constants.TUBE_WIDTH) <= 0);
    }

}
