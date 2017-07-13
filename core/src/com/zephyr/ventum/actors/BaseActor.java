package com.zephyr.ventum.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zephyr.ventum.utils.Constants;

/**
 * Created by sashaklimenko on 7/6/17.
 */

public class BaseActor extends Actor {

    protected float transformToScreen(float n) {
        return Constants.WORLD_TO_SCREEN * n;
    }
}
