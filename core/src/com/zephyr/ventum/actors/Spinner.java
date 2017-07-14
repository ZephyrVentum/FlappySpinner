package com.zephyr.ventum.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.zephyr.ventum.utils.Constants;
import com.zephyr.ventum.utils.TextureHolder;

/**
 * Created by sashaklimenko on 7/6/17.
 */

public class Spinner extends BaseActor {

    private TextureRegion textureRegion;
    private Body body;

    public Spinner(Body body) {
        this.body = body;
        textureRegion = TextureHolder.getTextureRegion(Constants.SPINNER_NAME);
        setSize(transformToScreen(Constants.SPINNER_SIZE), transformToScreen(Constants.SPINNER_SIZE));

        spinnerRotateAction();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion,
                body.getPosition().x,
                body.getPosition().y,
                getWidth() / 2,
                getHeight() / 2,
                getWidth(),
                getHeight(),
                1, 1, getRotation());
    }

    private void spinnerRotateAction() {
        SequenceAction rotateAction = new SequenceAction();
        rotateAction.addAction(Actions.rotateBy(360, 0.5f, Interpolation.linear));
        RepeatAction infiniteLoop = new RepeatAction();
        infiniteLoop.setCount(RepeatAction.FOREVER);
        infiniteLoop.setAction(rotateAction);
        this.addAction(infiniteLoop);
    }

    public void jump(float delta) {
        body.applyLinearImpulse(Constants.SPINNER_JUMP_IMPULSE, body.getWorldCenter(), true);
        //body.setTransform(body.getPosition().x, body.getPosition().y + delta*30f, 0);
       // body.setLinearDamping(0.5f);
    }
}
