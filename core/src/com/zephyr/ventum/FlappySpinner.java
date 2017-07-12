package com.zephyr.ventum;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.zephyr.ventum.screens.GameScreen;
import com.zephyr.ventum.screens.MenuScreen;
import com.zephyr.ventum.utils.TextureHolder;

public class FlappySpinner extends Game {

	private Music music;

	@Override
	public void create () {
		startMusic();
		TextureHolder.initAssets();
		this.setScreen(new GameScreen(this));
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
