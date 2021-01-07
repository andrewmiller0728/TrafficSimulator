package com.mygdx.trafficsimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    private Stage stage;
    private Vehicle vehicle;
    private CircleRoad circleRoad;

    @Override
    public void show() {
        circleRoad = new CircleRoad(384, 60);
        vehicle = new Vehicle(
                new Vector2(64f, 128f),
                new Vector2(circleRoad.getCircle().x + circleRoad.getCircle().radius, circleRoad.getCircle().y)
        );
        vehicle.setRoad(circleRoad);

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(vehicle);
        stage.setKeyboardFocus(vehicle);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.1f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
