package com.neosam.ld30game;

import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by neosam on 23.08.14.
 */
public class CollisionController implements ContactListener {
    public Set<CollisionCallback> collisionCallbackSet = new HashSet<CollisionCallback>();



    public void addCollisionCallback(CollisionCallback callback) {
        collisionCallbackSet.add(callback);
    }

    @Override
    public void beginContact(Contact contact) {
        final Fixture fixture1 = contact.getFixtureA();
        final Fixture fixture2 = contact.getFixtureB();
        if (collisionCallbackSet.contains(fixture1.getBody().getUserData())) {
            ((CollisionCallback) fixture1.getBody().getUserData()).collisionStartedWith(fixture2);
        }
        if (collisionCallbackSet.contains(fixture2.getBody().getUserData())) {
            ((CollisionCallback) fixture2.getBody().getUserData()).collisionStartedWith(fixture1);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
