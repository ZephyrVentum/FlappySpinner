package com.zephyr.ventum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by sashaklimenko on 7/6/17.
 */

public class TextureHolder {

    private Texture spinnerTexture, bottomTubeTexture, topTubeTexture, backgroundTexture, groundTexture;


    TextureHolder(){
        spinnerTexture = new Texture(Gdx.files.internal("bird.png"));
    }

    public Texture getSpinnerTexture() {
        return spinnerTexture;
    }

    public Texture getBottomTubeTexture() {
        return bottomTubeTexture;
    }

    public Texture getTopTubeTexture() {
        return topTubeTexture;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public Texture getGroundTexture() {
        return groundTexture;
    }
}
