package com.zephyr.ventum.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.zephyr.ventum.models.Skin;

import java.util.ArrayList;

/**
 * Created by ZiFir on 08.07.2017.
 */

public class Constants {

    public static final int HEIGHT = 40;
    public static final int WIDTH = 24;

    public static final float WORLD_TO_SCREEN = 1;


    public static final Vector2 GROUND_SIZE = new Vector2(WIDTH, 3.5f);

    public static final float TUBE_WIDTH = 3.5f;
    public static final float TUBE_HEIGHT = 30f;
    public static final float TUBE_SPACING = 7.7f;
    public static final float MAX_TUBE_SPACING = 7.7f;
    public static final float MIN_TUBE_SPACING = 4.5f;
    public static final float TUBE_SPEED = -9f;

    public static final float LOGO_HEIGHT = 11.45f;
    public static final float ONPAUSE_HEIGHT = 11.2f;
    public static final float ONFINISH_HEIGHT = 5.85f;
    public static final float ONRESUME_HEIGHT = 3.075f;
    public static final float ONRESUME_WIDTH = 14f;

    public static final float SPINNER_SIZE = 1f;
    public static final float SPENNER_DENSITY = 0.55f;
    public static final Vector2 SPINNER_JUMP_IMPULSE = new Vector2(0, 40f);


    public static final int FONT_SIZE = 65;
    public static final int LARGE_FONT_SIZE = 65;
    public static final int MEDIUM_FONT_SIZE = 45;
    public static final int SMALL_FONT_SIZE = 65;

    public static final Vector2 GRAVITY = new Vector2(0, -64);

    public static final float RECTANGLE_BUTTON_WIDTH = 7.5f;
    public static final float RECTANGLE_BUTTON_HEIGHT = 3.625f;

    public static final float SQUARE_BUTTON_SIZE = 3.25f;
    public static final float LARGE_SQUARE_BUTTON_SIZE = 3.625f;

    public static final String PHYSICS_PATH = "physics.xml";
    public static final String SPRITES_PATH = "sprites.txt";

    public static final String BACKGROUND_IMAGE_PATH = "bg.png";
    public static final String GROUND_IMAGE_PATH = "ground.png";
    public static final String SKY_IMAGE_PATH = "sky.png";
    public static final String LOGO_IMAGE_PATH = "logo.png";
    public static final String PAUSE_IMAGE_PATH = "on_pause.png";
    public static final String FINISH_IMAGE_PATH = "gameover.png";
    public static final String RESUME_IMAGE_PATH = "on_resume.png";

    public static final String BACKGROUND_IMAGE_NAME = "background";
    public static final String GROUND_IMAGE_NAME = "ground";
    public static final String SKY_IMAGE_NAME = "sky";
    public static final String LOGO_IMAGE_NAME = "logo";
    public static final String PAUSE_IMAGE_NAME = "onpause";
    public static final String RESUME_IMAGE_NAME = "onresume";
    public static final String FINISH_IMAGE_NAME = "onfinish";

    public static final String SPINNER_STANDARD_SKIN = "standart_skin";
    public static final String SPINNER_SILVER_SKIN = "silver_skin";
    public static final String SPINNER_RAINBOW_SKIN = "rainbow_skin";
    public static final String SPINNER_KITTY_SKIN = "kitty_skin";
    public static final String SPINNER_ARMY_SKIN = "army_skin";
    public static final String SPINNER_FIRE_SKIN = "fire_skin";
    public static final String SPINNER_LIGHT_SKIN = "light_skin";
    public static final String SPINNER_GOLDEN_SKIN = "golden_skin";
    public static final String SPINNER_HYPNO_SKIN = "hypno_skin";

    public static final String BOTTOM_TUBE_NAME = "bottomtube";
    public static final String TOP_TUBE_NAME = "toptube";

    public static final String COIN_NAME = "coin";

    public static final String HIT_SOUND_PATH = "contact.wav";
    public static final String SCORE_SOUND_PATH = "coin.wav";
    public static final String BUTTON_SOUND_PATH = "click.wav";
    public static final String MARKET_SOUND_PATH = "market.wav";
    public static final String GAMEOVER_SOUND_PATH = "death.wav";
    public static final String JUMP_SOUND_PATH = "jump.ogg";
    public static final String MUSIC_PATH = "music.mp3";

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


    public static final Vector2 SPINNER_POSITIONS = new Vector2(
            WIDTH / 2 - AssetsManager.getTextureRegion(SPINNER_STANDARD_SKIN).getRegionWidth() * 0.05f / 2,
            HEIGHT / 2 - AssetsManager.getTextureRegion(SPINNER_STANDARD_SKIN).getRegionHeight() * 0.05f / 2);

    public static final Vector2 GROUND_POSITIONS = new Vector2(0, 0);

    public static final String[] SPINNER_SKINES = {
            Constants.SPINNER_STANDARD_SKIN,
            Constants.SPINNER_LIGHT_SKIN,
            Constants.SPINNER_SILVER_SKIN,
            Constants.SPINNER_GOLDEN_SKIN,
            Constants.SPINNER_RAINBOW_SKIN,
            Constants.SPINNER_FIRE_SKIN,
            Constants.SPINNER_ARMY_SKIN,
            Constants.SPINNER_HYPNO_SKIN,
            Constants.SPINNER_KITTY_SKIN
    };

    public static int SKIN_COUNT = SPINNER_SKINES.length;


    public static final String[] SKINS_NAMES = {
            "Standard Skin",
            "Neon Light",
            "Silver Name",
            "Golden Skin",
            "Rainbow",
            "Sacred Fire",
            "ATO Skin",
            "Hypno Spinner",
            "Hello Kitty"
    };
}
