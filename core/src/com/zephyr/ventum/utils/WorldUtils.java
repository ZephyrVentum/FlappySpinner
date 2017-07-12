package com.zephyr.ventum.utils;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by sashaklimenko on 7/12/17.
 */

public class WorldUtils {

    public static World createWorld() {
        return new World(Constants.GRAVITY, true);
    }

}
