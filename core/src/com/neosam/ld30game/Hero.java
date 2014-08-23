package com.neosam.ld30game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by neosam on 23.08.14.
 */
public class Hero extends AnimatedPhysicsActor {

    public Hero(World world, Vector2 size, TextureAtlas textureAtlas, String atlasSuffix, String atlasPrefix) {
        super(world, size, textureAtlas, atlasSuffix, atlasPrefix);
    }
}
