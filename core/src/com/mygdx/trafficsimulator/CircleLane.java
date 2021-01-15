package com.mygdx.trafficsimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

public class CircleLane {

    private Circle circle;
    private Vector2[] points;
    private Vehicle[] vehicles;

    public CircleLane(float radius, int pointCount) {
        circle = new Circle(
                Gdx.graphics.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f,
                radius
        );
        points = new Vector2[pointCount];
        float angle = 0f;
        for (int i = 0; i < pointCount; i++) {
            points[i] = new Vector2(
                    circle.x + MathUtils.cosDeg(angle) * circle.radius,
                    circle.y + MathUtils.sinDeg(angle) * circle.radius
            );
            angle += 360f / pointCount;
        }
        vehicles = new Vehicle[pointCount];
    }

    public boolean requestMove(Vehicle vehicle) {
        return getVehicle(getVehicleIndex(vehicle) + 1) == null;
    }

    public Circle getCircle() {
        return circle;
    }

    public Vector2 getCenterVector() {
        return new Vector2(circle.x, circle.y);
    }

    public Vector2 getPoint(int index) {
        return points[index];
    }

    public Vector2[] getPoints() {
        return points;
    }

    public void addVehicle(Vehicle vehicle, int initLanePoint) {
        if (getVehicle(initLanePoint) == null) {
            vehicles[initLanePoint % points.length] = vehicle;
        }
    }

    public Vehicle getVehicle(int index) {
        return vehicles[index % points.length];
    }

    public int getVehicleIndex(Vehicle vehicle) {
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicle.equals(vehicles[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return "CircleLane{" +
                "circle=" + circle +
                ", pointCount=" + points.length +
                '}';
    }

    public void printLane() {
        System.out.println(Arrays.toString(vehicles));
    }
}
