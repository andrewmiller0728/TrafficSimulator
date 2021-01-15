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

import java.util.Objects;

public class Vehicle extends Actor {

    private static int nextID = 0;
    private int id;

    public enum Type {
        CAR_RED, CAR_GREEN, CAR_BLUE
    }

    private CircleLane lane;
    private float actionDuration;
    private TextureRegion region;

    public Vehicle(Vector2 size, CircleLane currLane, int initLanePoint, Type vehicleColor) {
        id = nextID;
        nextID++;

        lane = currLane;
        currLane.addVehicle(this, initLanePoint);

        setThrottle(0f);

        switch (vehicleColor) {
            case CAR_RED:
                region = new TextureRegion(new Texture("car_red_top.png"));
                setColor(Color.RED);
                break;
            case CAR_GREEN:
                region = new TextureRegion(new Texture("car_green_top.png"));
                setColor(Color.GREEN);
                break;
            case CAR_BLUE:
                region = new TextureRegion(new Texture("car_blue_top.png"));
                setColor(Color.BLUE);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + vehicleColor);
        }

        setBounds(
                region.getRegionX(), region.getRegionY(),
                region.getRegionWidth(), region.getRegionHeight()
        );
        setSize(size.x, size.y);
        setOrigin(size.x / 2f, size.y / 2f);
        Vector2 currPos = lane.getPoint(initLanePoint);
        setPosition(currPos.x, currPos.y, Align.center);
        addListener(new VehicleInputListener());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
//        if (prevLanePoint != null) {
//            setRotation(currLanePoint.cpy().sub(prevLanePoint).angleDeg() - 90f); // TODO rotate slowly
//        }
        batch.draw(
                region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
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

    public void addMoveToNextLanePointAction(float actionDuration) {
        Vector2 nextLanePoint = lane.getPoint(lane.getVehicleIndex(this) + 1);
        addAction(Actions.moveToAligned(
                nextLanePoint.x,
                nextLanePoint.y,
                Align.center,
                actionDuration
        ));
    }

    public void driveToNextPoint() {
        if (getActions().isEmpty() && lane.requestMove(this)) {
            addMoveToNextLanePointAction(actionDuration);
        }
    }

    public Vector2 getPositionVector() {
        return new Vector2(getX(Align.center), getY(Align.center));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                '}';
    }
}
