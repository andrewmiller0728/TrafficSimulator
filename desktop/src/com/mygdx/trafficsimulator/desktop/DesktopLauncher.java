package com.mygdx.trafficsimulator.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.trafficsimulator.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Andrew's Traffic Simulator";
		config.width = 1920;
		config.height = (int) (config.width * 9f / 16f);
		new LwjglApplication(new MyGdxGame(), config);
	}
}
