package com.mygdx.trafficsimulator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

public class Vehicle extends Actor {

    private CircleRoad road;
    private int nextRoadPointIndex;
    private Vector2 currRoadPoint;
    private float actionDuration;

    private TextureRegion region;

    public Vehicle(Vector2 size, CircleRoad road, float throttle) {
        this.road = road;
        nextRoadPointIndex = 1;
        currRoadPoint = road.getPoints()[0];
        actionDuration = MathUtils.map(
                0f, 1f,
                10f, 0.01f,
                (float) (1f / (1f + Math.pow(Math.E, -10f * (throttle - 0.5f)))) // TODO find a better function for throttle growth
        );

        region = new TextureRegion(new Texture("car_red_top.png"));
        setBounds(
                region.getRegionX(), region.getRegionY(),
                region.getRegionWidth(), region.getRegionHeight()
        );
        setSize(size.x, size.y);
        setOrigin(size.x / 2f, size.y / 2f);
        setPosition(currRoadPoint.x, currRoadPoint.y, Align.center);
        addListener(new VehicleInputListener());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(
                region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
    }

    public Vector2 peekNextRoadPoint() {
        if (nextRoadPointIndex >= road.getPoints().length) {
            nextRoadPointIndex = 0;
        }
        Vector2 nextRoadPoint = road.getPoints()[nextRoadPointIndex];
        nextRoadPointIndex++;
        return nextRoadPoint;
    }

    public void addMoveToNextRoadPointAction(float actionDuration) {
        currRoadPoint = peekNextRoadPoint();
        addAction(Actions.moveToAligned(
                currRoadPoint.x,
                currRoadPoint.y,
                Align.center,
                actionDuration
        ));
    }

    public void driveToNextPoint() {
        if (getActions().isEmpty()) {
            Vector2 prevPosition = currRoadPoint;
            addMoveToNextRoadPointAction(actionDuration);
            setRotation(currRoadPoint.cpy().sub(prevPosition).angleDeg() - 90f);
        }
    }

    public Vector2 getPositionVector() {
        return new Vector2(getX(Align.center), getY(Align.center));
    }

}
