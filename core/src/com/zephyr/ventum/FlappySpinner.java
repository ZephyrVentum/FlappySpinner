package com.zephyr.ventum;

import com.badlogic.gdx.Game;
import com.zephyr.ventum.screens.MenuScreen;
import com.zephyr.ventum.utils.AssetsManager;
import com.zephyr.ventum.utils.AudioManager;
import com.zephyr.ventum.interfaces.GameEventListener;
import com.zephyr.ventum.utils.GameManager;
import com.zephyr.ventum.utils.GamePreferences;

public class FlappySpinner extends Game {

	public static GameManager gameManager;

	public FlappySpinner(GameEventListener gameEventListener) {
		gameManager = new GameManager();
		gameManager.setGameEventListener(gameEventListener);
	}

	@Override
	public void create () {
		gameManager.setPreferences(new GamePreferences());
		AssetsManager.initAssets();
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
		AssetsManager.dispose();
		AudioManager.getInstance().dispose();
	}
}
