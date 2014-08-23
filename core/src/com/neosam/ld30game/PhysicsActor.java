package com.neosam.ld30game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by neosam on 23.08.14.
 */
public class PhysicsActor extends Actor {
    private final Vector2 size;
    private World world;
    private Body body;

    public PhysicsActor(World world, Vector2 size) {
        this.world = world;
        this.size = size;
        createBody();
    }

    private void createBody() {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        final PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(size.x / 2, size.y / 2);
        final FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.shape = bodyShape;
        final Fixture bodyFixture = body.createFixture(bodyFixtureDef);

        bodyShape.dispose();
    }

    @Override
    public void act(float delta) {
        setPosition(body.getPosition().x, body.getPosition().y);
    }
}
