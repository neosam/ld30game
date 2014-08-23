package com.neosam.ld30game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by neosam on 23.08.14.
 */
public class AnimatedActor extends Actor {
    private Animation currentAnimation;
    private TextureRegion currentFrame;
    private Map<String, Animation> animationMap = new HashMap<String, Animation>();
    private float duration;

    public AnimatedActor(Vector2 size) {
        setOrigin(size.x / 2, size.y / 2);
        setSize(size.x, size.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        duration += delta;

        if (currentAnimation != null) {
            currentFrame = currentAnimation.getKeyFrame(duration);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (currentFrame != null) {
            final float x = getX();
            final float y = getY();
            final float width = getWidth();
            final float height = getHeight();
            final float originX = getOriginX();
            final float originY = getOriginY();
            batch.draw(currentFrame, x - originX, y - originY, width, height);
        }
    }

    public void addAnimation(String finalAnimationName, Animation animation) {
        animationMap.put(finalAnimationName, animation);
    }

    public void activateAnimation(String name) {
        if (animationMap.containsKey(name)) {
            final Animation newAnimation = animationMap.get(name);
            if (currentAnimation != newAnimation) {
                currentAnimation = animationMap.get(name);
                duration = 0;
            }
        }
    }
}
