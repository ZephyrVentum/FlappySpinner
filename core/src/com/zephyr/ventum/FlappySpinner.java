package com.zephyr.ventum;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.zephyr.ventum.screens.MenuScreen;
import com.zephyr.ventum.utils.AssetsManager;
import com.zephyr.ventum.utils.GamePreferences;

public class FlappySpinner extends Game {

	private Music music;

	@Override
	public void create () {
		startMusic();
		AssetsManager.initAssets();
		GamePreferences.getInstance();
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	private void startMusic() {
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.play();
	}

	private void stopSound() {
		music.stop();
		music.dispose();
	}
	
	@Override
	public void dispose () {
		stopSound();
	}
}
