package com.mygdx.trafficsimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CircleRoad {

    private Circle circle;
    private Vector2[] points;

    public CircleRoad(float radius, int pointCount) {
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
    }

    public Circle getCircle() {
        return circle;
    }

    public Vector2[] getPoints() {
        return points;
    }
}
