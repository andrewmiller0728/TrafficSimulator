package com.mygdx.trafficsimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

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

    public Vector2 getPoint(int index) {
        return points[index % points.length];
    }

    public Vector2[] getPoints() {
        return points;
    }

    public void addVehicle(Vehicle vehicle, int lanePoint) {
        vehicles[lanePoint % vehicles.length] = vehicle;
    }

    public Vehicle getVehicle(int index) {
        return vehicles[index % vehicles.length];
    }

    public int getVehicleIndex(Vehicle vehicle) {
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicle.equals(getVehicle(i))) {
                return i;
            }
        }
        return -1;
    }

    public boolean requestMove(Vehicle vehicle) {
        if (getVehicleIndex(vehicle) != -1 && getVehicle(getVehicleIndex(vehicle) + 1) == null) {
            if (getVehicleIndex(vehicle) == vehicles.length - 1) {
                vehicles[getVehicleIndex(vehicle)] = null;
                vehicles[0] = vehicle;
            }
            addVehicle(vehicle, getVehicleIndex(vehicle) + 1);
            vehicles[getVehicleIndex(vehicle)] = null;
            return true;
        }
        return false;
    }

    public Vector2 getLanePoint(Vehicle vehicle) {
        for (Vehicle listVehicle : vehicles) {
            if (listVehicle.equals(vehicle)) {
                return vehicle.getPositionVector();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "CircleLane{" +
                "circle=" + circle.toString() +
                ", pointCount=" + points.length +
                '}';
    }

}
