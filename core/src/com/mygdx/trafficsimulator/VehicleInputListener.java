package com.mygdx.trafficsimulator;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

public class VehicleInputListener extends InputListener {

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//        Vehicle vehicle = (Vehicle) event.getListenerActor();
//        Vector2 nextPoint = vehicle.getNextRoadPoint();
//
//        vehicle.addAction(Actions.moveToAligned(
//                nextPoint.x,
//                nextPoint.y,
//                Align.center,
//                0.1f
//        ));

        return false;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
//        switch (keycode) {
//            case Input.Keys.W:
//                event.getListenerActor().addAction(Actions.moveBy(0f, 10f));
//                break;
//            case Input.Keys.A:
//                event.getListenerActor().addAction(Actions.moveBy(-10f, 0f));
//                break;
//            case Input.Keys.S:
//                event.getListenerActor().addAction(Actions.moveBy(0f, -10f));
//                break;
//            case Input.Keys.D:
//                event.getListenerActor().addAction(Actions.moveBy(10f, 0f));
//                break;
//            default:
//                return false;
//        }
        return false;
    }

}
