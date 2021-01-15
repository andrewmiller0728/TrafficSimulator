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

    public Circle getCircle() {
        return circle;
    }

    public Vector2 getCenterVector() {
        return new Vector2(circle.x, circle.y);
    }

    public Vector2[] getPoints() {
        return points;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles[vehicle.getCurrLanePointIndex()] = vehicle;
    }

    public Vehicle getVehicle(int index) {
        return vehicles[index % points.length];
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
