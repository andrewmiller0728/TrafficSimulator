package com.mygdx.trafficsimulator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MyGdxGame extends Game {

	private GameScreen screen;

	@Override
	public void create() {
		this.setScreen(screen = new GameScreen());
	}

	@Override
	public void render() {
		screen.render(Gdx.graphics.getDeltaTime());
	}
}
