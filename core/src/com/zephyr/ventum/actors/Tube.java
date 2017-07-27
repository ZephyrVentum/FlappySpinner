package com.zephyr.ventum.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.AssetsManager;
import com.zephyr.ventum.utils.WorldUtils;

/**
 * Created by sashaklimenko on 7/14/17.
 */

public class Tube extends BaseActor {

    private TextureRegion topTubeTexture, bottomTubeTexture;
    private Body topTubeBody, bottomTubeBody;

    private boolean isSpinnerScoreWrited = false;

    public Tube(Array<Body> bodies) {

        topTubeTexture = AssetsManager.getTextureRegion(Constants.TOP_TUBE_NAME);
        bottomTubeTexture = AssetsManager.getTextureRegion(Constants.BOTTOM_TUBE_NAME);

        topTubeBody = bodies.first();//first item
        bottomTubeBody = bodies.peek();//last item
    }

    @Override
    public void act(float delta) {
        if (isLeftSideReached()) {
            resetTubePositions();
        }
    }

    public float getTubeBodyX(){
        return topTubeBody.getPosition().x;
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
        setSpinnerScoreWrited(false);
    }

    public boolean isSpinnerScoreWrited() {
        return isSpinnerScoreWrited;
    }

    public void setSpinnerScoreWrited(boolean spinnerScoreWrited) {
        isSpinnerScoreWrited = spinnerScoreWrited;
    }

    private boolean isLeftSideReached() {
        return ((topTubeBody.getPosition().x + Constants.TUBE_WIDTH) <= 0);
    }

}
