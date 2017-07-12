package com.zephyr.ventum.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ZiFir on 08.07.2017.
 */

public class Constants {

    public static final int HEIGHT = 800;
    public static final int WIDTH = 480;

    public static final Vector2 GRAVITY = new Vector2(0, 40);

    public static final String PHYSICS_PATH = "physics.xml";
    public static final String SPRITES_PATH = "sprites.txt";

    public static final String BACKGROUND_IMAGE_PATH = "bg.png";
    public static final String GROUND_IMAGE_PATH = "ground.png";

    public static final String BACKGROUND_IMAGE_NAME = "background";
    public static final String GROUND_IMAGE_NAME = "ground";

    public static final String SPINNER_NAME = "spinner";

    public static final String BOTTOM_TUBE_NAME = "bottom_tube";
    public static final String TOP_TUBE_NAME = "top_tube";

    public static final String GAME_OVER_NAME = "gameover";

    public static final String BUTTON_HOME_NAME = "home";
    public static final String BUTTON_HOME_PRESSED_NAME = "home_pressed";

    public static final String BUTTON_LEADERBOARD_NAME = "leaderboard";
    public static final String BUTTON_LEADERBOARD_PRESSED_NAME = "leaderboard_pressed";

    public static final String BUTTON_PAUSE_NAME = "pause";
    public static final String BUTTON_PAUSE_PRESSED_NAME = "pause_pressed";

    public static final String BUTTON_PLAY_NAME = "play";
    public static final String BUTTON_PLAY_PRESSED_NAME = "play_pressed";

    public static final String BUTTON_MUSIC_NAME = "music";
    public static final String BUTTON_MUSIC_PRESSED_NAME = "music_pressed";

    public static final String BUTTON_MUSIC_OFF_NAME = "music_off";
    public static final String BUTTON_MUSIC_OFF_PRESSED_PRESSED_NAME = "music_off_pressed";

    public static final String BUTTON_SETTINGS_NAME = "settings";
    public static final String BUTTON_SETTINGS_PRESSED_NAME = "settings_pressed";

    public static final String MUSIC_PATH = "music.mp3";
    public static final String SOUND_PATH = "sfx_wing.ogg";

    public static final Vector2 SPINNER_POSSITIONS = new Vector2(
            WIDTH / 2 - TextureHolder.getTextureRegion(SPINNER_NAME).getRegionWidth() / 2,
            HEIGHT / 2 - TextureHolder.getTextureRegion(SPINNER_NAME).getRegionHeight() / 2);

}
