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

    public enum Type {
        CAR_RED, CAR_GREEN, CAR_BLUE
    }

    private CircleLane lane;
    private int currLanePointIndex;
    private Vector2 prevLanePoint, currLanePoint;
    private float actionDuration;

    private TextureRegion region;

    public Vehicle(Vector2 size, CircleLane currLane, int initLanePoint, Type vehicleColor) {
        this.lane = currLane;
        currLanePointIndex = initLanePoint;
        currLanePoint = currLane.getPoints()[currLanePointIndex];
        currLane.addVehicle(this);
        prevLanePoint = null;
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
        setPosition(currLanePoint.x, currLanePoint.y, Align.center);
        addListener(new VehicleInputListener());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        if (prevLanePoint != null) {
            setRotation(currLanePoint.cpy().sub(prevLanePoint).angleDeg() - 90f); // TODO rotate slowly
        }
        batch.draw(
                region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
    }

    public float getDistance(Vehicle vehicle) {
        return this.getPositionVector().dst(vehicle.getPositionVector());
    }

    public Vector2 peekNextLanePoint() {
        if (currLanePointIndex + 1 >= lane.getPoints().length) {
            currLanePointIndex = (currLanePointIndex + 1) % lane.getPoints().length;
        }
        Vector2 nextLanePoint = lane.getPoints()[currLanePointIndex + 1];
        return nextLanePoint;
    }

    public void addMoveToNextLanePointAction(float actionDuration) {
        currLanePoint = peekNextLanePoint();
        currLanePointIndex++;
        addAction(Actions.moveToAligned(
                currLanePoint.x,
                currLanePoint.y,
                Align.center,
                actionDuration
        ));
    }

    public void driveToNextPoint() {
        Vehicle next = getNextVehicleInLane();
        if (next != null && getActions().isEmpty() && !isForwardBlocked()) {
            prevLanePoint = currLanePoint;
            addMoveToNextLanePointAction(actionDuration);
        }
    }

    public boolean isForwardBlocked() {
        float dist = this.getPositionVector().dst(getNextVehicleInLane().getPositionVector());
        return dist <= getHeight();
    }

    public Vehicle getNextVehicleInLane() {
        int i = this.getCurrLanePointIndex() + 1;
        while (lane.getVehicle(i) == null) {
            i++;
        }
        if (this.equals(lane.getVehicle(i))) {
            return null;
        }
        else {
            return lane.getVehicle(i);
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

    public int getCurrLanePointIndex() {
        return currLanePointIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return currLanePointIndex == vehicle.currLanePointIndex
                && lane.equals(vehicle.lane)
                && currLanePoint.equals(vehicle.currLanePoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lane, currLanePointIndex, currLanePoint);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "color=" + getColor().toString() +
                ", lane=" + lane +
                ", currLanePointIndex=" + currLanePointIndex +
                ", currLanePoint=" + currLanePoint +
                '}';
    }
}
