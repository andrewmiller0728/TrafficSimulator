package com.mygdx.trafficsimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    private Stage stage;
    private Vehicle vehicleA, vehicleB, vehicleC;
    private CircleLane circleLane;

    @Override
    public void show() {
        circleLane = new CircleLane(Gdx.graphics.getHeight() * (3f / 8f), (int)(360 * 1f / 2f));
        vehicleA = new Vehicle(new Vector2(64f, 128f), circleLane, 0.1f);
        vehicleB = new Vehicle(new Vector2(64f, 128f), circleLane, 0.5f);
        vehicleC = new Vehicle(new Vector2(64f, 128f), circleLane, 1.0f);

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(vehicleA);
        stage.addActor(vehicleB);
        stage.addActor(vehicleC);
//        stage.setKeyboardFocus(vehicle);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.1f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        vehicleA.driveToNextPoint();
        vehicleB.driveToNextPoint();
        vehicleC.driveToNextPoint();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
