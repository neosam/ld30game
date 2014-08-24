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
    private HeroCollisionListener heroCollisionListener;
    private float portalCollisionDisabledFor = 0;
    private int world = 1;
    private boolean portalCreateable = false;

    public Hero(World world, Vector2 size, TextureAtlas textureAtlas, String atlasSuffix, String atlasPrefix,
                HeroCollisionListener heroCollisionListener) {
        super(world, size, textureAtlas, atlasSuffix, atlasPrefix);
        this.heroCollisionListener = heroCollisionListener;
        addListener(new HeroInputListener(this));
    }


    @Override
    public Object whenUserdataIs() {
        return this;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        portalCollisionDisabledFor -= delta;
    }

    @Override
    public void collisionStartedWith(Fixture fixture) {
        if (fixture.getUserData() == null) {
            return;
        }
        if (portalCollisionDisabledFor < 0 && fixture.getUserData().equals("portal")) {
            Gdx.app.log("Hero", "Touched portal " + fixture.getBody().getUserData());
            heroCollisionListener.collisionWithPortal((String) fixture.getBody().getUserData());
            portalCollisionDisabledFor = 1f;
        }
        if (fixture.getUserData().equals("finish")) {
            heroCollisionListener.finishTouched();
        }
    }

    public void switchHero() {
        heroCollisionListener.switchHero();
    }

    public int getWorld() {
        return world;
    }

    public void setWorld(int world) {
        this.world = world;
    }

    public void createPortal() {
        if (portalCreateable) {
            portalCollisionDisabledFor = .5f;
            heroCollisionListener.createPortal();
        }
    }

    public boolean isPortalCreateable() {
        return portalCreateable;
    }

    public void setPortalCreateable(boolean portalCreateable) {
        this.portalCreateable = portalCreateable;
    }
}
