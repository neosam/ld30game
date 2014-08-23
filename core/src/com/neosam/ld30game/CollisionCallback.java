package com.neosam.ld30game;

import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by neosam on 23.08.14.
 */
public interface CollisionCallback {
    public Object whenUserdataIs();
    public void collisionStartedWith(Fixture fixture);
}
