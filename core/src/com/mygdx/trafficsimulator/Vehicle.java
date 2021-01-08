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

    private CircleLane lane;
    private int nextLanePointIndex;
    private Vector2 currLanePoint;
    private float actionDuration;

    private TextureRegion region;

    public Vehicle(Vector2 size, CircleLane lane, float throttle) {
        this.lane = lane;
        nextLanePointIndex = 1;
        currLanePoint = lane.getPoints()[0];
        setThrottle(throttle);

        region = new TextureRegion(new Texture("car_red_top.png"));
        setBounds(
                region.getRegionX(), region.getRegionY(),
                region.getRegionWidth(), region.getRegionHeight()
        );
        setSize(size.x, size.y);
        setOrigin(size.x / 2f, size.y / 2f);
        setPosition(currLanePoint.x, currLanePoint.y, Align.center);
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

    public Vector2 peekNextLanePoint() {
        if (nextLanePointIndex >= lane.getPoints().length) {
            nextLanePointIndex = 0;
        }
        Vector2 nextLanePoint = lane.getPoints()[nextLanePointIndex];
        nextLanePointIndex++;
        return nextLanePoint;
    }

    public void addMoveToNextLanePointAction(float actionDuration) {
        currLanePoint = peekNextLanePoint();
        addAction(Actions.moveToAligned(
                currLanePoint.x,
                currLanePoint.y,
                Align.center,
                actionDuration
        ));
    }

    public void driveToNextPoint() {
        if (getActions().isEmpty()) {
            Vector2 prevPosition = currLanePoint;
            addMoveToNextLanePointAction(actionDuration);
            setRotation(currLanePoint.cpy().sub(prevPosition).angleDeg() - 90f); // TODO rotate slowly
        }
    }

    public void setThrottle(float t) {
        final float c = 0.01f, k = -10, x0 = 0.5f;
        actionDuration = MathUtils.map(
                0f, 1f,
                10f, 0.01f,
                MathUtils.clamp(
                        (float) (1f / (1f + (c * Math.pow(Math.E, k * (t - x0))))),
                        0f, 1f
                )
        );
    }

    public Vector2 getPositionVector() {
        return new Vector2(getX(Align.center), getY(Align.center));
    }


}
