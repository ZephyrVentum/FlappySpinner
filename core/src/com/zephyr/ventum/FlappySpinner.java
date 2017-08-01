package com.zephyr.ventum;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.zephyr.ventum.screens.MenuScreen;
import com.zephyr.ventum.utils.AssetsManager;
import com.zephyr.ventum.utils.AudioManager;
import com.zephyr.ventum.utils.GamePreferences;

public class FlappySpinner extends Game {

	@Override
	public void create () {
		AssetsManager.initAssets();
		GamePreferences.getInstance();
		AudioManager.getInstance().init();

		startMusic();
		
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	private void startMusic() {
		AudioManager.getInstance().playMusic();
	}

	@Override
	public void dispose () {
		AudioManager.getInstance().dispose();
	}
}
