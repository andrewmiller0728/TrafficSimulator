package com.mygdx.trafficsimulator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class Vehicle extends Actor {

    private TextureRegion region;
    private CircleRoad road;
    private int roadPointIndex;

    public Vehicle(Vector2 size, Vector2 pos) {
        region = new TextureRegion(new Texture("car_red_top.png"));
        setBounds(
                region.getRegionX(), region.getRegionY(),
                region.getRegionWidth(), region.getRegionHeight()
        );
        setSize(size.x, size.y);
        setPosition(pos.x, pos.y, Align.center);
        addListener(new VehicleInputListener());
        road = null;
        roadPointIndex = 0;
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

    public CircleRoad getRoad() {
        return road;
    }

    public void setRoad(CircleRoad road) {
        this.road = road;
    }

    public Vector2 getNextRoadPoint() {
        if (roadPointIndex >= road.getPoints().length) {
            roadPointIndex = 0;
        }
        Vector2 point = road.getPoints()[roadPointIndex];
        roadPointIndex++;
        return point;
    }

}
