package com.zephyr.ventum.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;

import java.util.HashMap;

/**
 * Created by sashaklimenko on 7/6/17.
 */

public class TextureHolder {

    private static HashMap<String, TextureRegion> textureRegionHashMap = new HashMap<String, TextureRegion>();
    private static TextureAtlas textureAtlas;

    public static void initAssets(){

        textureAtlas = new TextureAtlas(Constants.SPRITES_PATH);



        textureRegionHashMap.put(Constants.SPINNER_NAME,
                textureAtlas.findRegion(Constants.SPINNER_NAME));

        textureRegionHashMap.put(Constants.GROUND_IMAGE_NAME,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.GROUND_IMAGE_PATH))));

        textureRegionHashMap.put(Constants.LOGO_IMAGE_NAME,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.LOGO_IMAGE_PATH))));

        textureRegionHashMap.put(Constants.SKY_IMAGE_NAME,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.SKY_IMAGE_PATH))));


        textureRegionHashMap.put(Constants.BACKGROUND_IMAGE_NAME,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH))));

        textureRegionHashMap.put(Constants.GAME_OVER_NAME,
                textureAtlas.findRegion(Constants.GAME_OVER_NAME));

        textureRegionHashMap.put(Constants.TOP_TUBE_NAME,
                textureAtlas.findRegion(Constants.TOP_TUBE_NAME));

        textureRegionHashMap.put(Constants.BOTTOM_TUBE_NAME,
                textureAtlas.findRegion(Constants.BOTTOM_TUBE_NAME));

    }

    public static TextureRegion getTextureRegion(String key){
        return textureRegionHashMap.get(key);
    }

    public static TextureAtlas getTextureAtlas(){
        return textureAtlas;
    }

}
