package com.neosam.ld30game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by neosam on 23.08.14.
                */
public class Hero extends AnimatedPhysicsActor implements CollisionCallback {

    public Hero(World world, Vector2 size, TextureAtlas textureAtlas, String atlasSuffix, String atlasPrefix) {
        super(world, size, textureAtlas, atlasSuffix, atlasPrefix);
        addListener(new HeroInputListener(this));
    }


    @Override
    public Object whenUserdataIs() {
        return this;
    }

    @Override
    public void collisionStartedWith(Fixture fixture) {
        if (fixture.getUserData() == null) {
            return;
        }
        if (fixture.getUserData().equals("portal")) {
            Gdx.app.log("Hero", "Touched portal " + fixture.getBody().getUserData());
        }
    }
}
