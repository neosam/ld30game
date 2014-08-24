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
    private Vector2 positionNextAct;

    public PhysicsActor(World world, Vector2 size) {
        this.world = world;
        this.size = size;
        createBody();
        setOrigin(size.x / 2, size.y / 2);
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
        body.setUserData(this);

        bodyShape.dispose();
    }

    @Override
    public void act(float delta) {
        if (positionNextAct != null) {
            body.setTransform(positionNextAct, 0);
            positionNextAct = null;
        }
        setPosition(body.getPosition().x, body.getPosition().y);
    }


    public Body getBody() {
        return body;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setPositionNextAct(Vector2 positionNextAct) {
        this.positionNextAct = positionNextAct;
    }


}
