package com.zephyr.ventum;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.zephyr.ventum.interfaces.GameEventListener;

public class AndroidLauncher extends AndroidApplication implements GameEventListener {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new FlappySpinner(this), config);
	}


	@Override
	public void displayAd() {

	}

	@Override
	public void hideAd() {

	}

	@Override
	public void displayLeaderboard() {

	}

	@Override
	public void displayAchievements() {

	}

	@Override
	public void share() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out Flappy Spinner! \n https://github.com/ZephyrVentum/FlappySpinner");
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, "Share!"));
	}
}
