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

public class AssetsManager {

    private static HashMap<String, TextureRegion> textureRegionHashMap = new HashMap<String, TextureRegion>();
    private static TextureAtlas textureAtlas;

    public static void initAssets() {

        textureAtlas = new TextureAtlas(Constants.SPRITES_PATH);

        //spinner skins
        textureRegionHashMap.put(Constants.SPINNER_STANDARD_SKIN,
                textureAtlas.findRegion(Constants.SPINNER_STANDARD_SKIN));

        textureRegionHashMap.put(Constants.SPINNER_SILVER_SKIN,
                textureAtlas.findRegion(Constants.SPINNER_SILVER_SKIN));

        textureRegionHashMap.put(Constants.SPINNER_GOLDEN_SKIN,
                textureAtlas.findRegion(Constants.SPINNER_GOLDEN_SKIN));

        textureRegionHashMap.put(Constants.SPINNER_LIGHT_SKIN,
                textureAtlas.findRegion(Constants.SPINNER_LIGHT_SKIN));

        textureRegionHashMap.put(Constants.SPINNER_FIRE_SKIN,
                textureAtlas.findRegion(Constants.SPINNER_FIRE_SKIN));

        textureRegionHashMap.put(Constants.SPINNER_ARMY_SKIN,
                textureAtlas.findRegion(Constants.SPINNER_ARMY_SKIN));

        textureRegionHashMap.put(Constants.SPINNER_RAINBOW_SKIN,
                textureAtlas.findRegion(Constants.SPINNER_RAINBOW_SKIN));

        textureRegionHashMap.put(Constants.SPINNER_KITTY_SKIN,
                textureAtlas.findRegion(Constants.SPINNER_KITTY_SKIN));

        textureRegionHashMap.put(Constants.SPINNER_HYPNO_SKIN,
                textureAtlas.findRegion(Constants.SPINNER_HYPNO_SKIN));

        //ground - sky - bg
        textureRegionHashMap.put(Constants.GROUND_IMAGE_NAME,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.GROUND_IMAGE_PATH))));

        textureRegionHashMap.put(Constants.SKY_IMAGE_NAME,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.SKY_IMAGE_PATH))));

        textureRegionHashMap.put(Constants.BACKGROUND_IMAGE_NAME,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH))));

        //images
        textureRegionHashMap.put(Constants.LOGO_IMAGE_NAME,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.LOGO_IMAGE_PATH))));

        textureRegionHashMap.put(Constants.PAUSE_IMAGE_NAME,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.PAUSE_IMAGE_PATH))));

        textureRegionHashMap.put(Constants.FINISH_IMAGE_NAME,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.FINISH_IMAGE_PATH))));

        textureRegionHashMap.put(Constants.RESUME_IMAGE_NAME,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.RESUME_IMAGE_PATH))));

        //tubes
        textureRegionHashMap.put(Constants.TOP_TUBE_NAME,
                textureAtlas.findRegion(Constants.TOP_TUBE_NAME));

        textureRegionHashMap.put(Constants.BOTTOM_TUBE_NAME,
                textureAtlas.findRegion(Constants.BOTTOM_TUBE_NAME));
        //
    }

    public static TextureRegion getTextureRegion(String key) {
        return textureRegionHashMap.get(key);
    }

    public static TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

}
