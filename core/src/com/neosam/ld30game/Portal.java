package com.neosam.ld30game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by neosam on 23.08.14.
 */
public class Portal extends AnimatedActor {
    public Portal(TextureAtlas textureAtlas) {
        super(new Vector2(2, 2));
        final Array<Sprite> spriteArray = textureAtlas.createSprites("portal_");
        final Animation animation = new Animation(0.1f, spriteArray);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        addAnimation("default", animation);
        activateAnimation("default");
    }
}
