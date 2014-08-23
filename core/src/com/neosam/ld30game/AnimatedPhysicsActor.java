package com.neosam.ld30game;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by neosam on 23.08.14.
 */
public class AnimatedPhysicsActor extends PhysicsActor {
    private Animation currentAnimation;
    private TextureRegion currentFrame;
    private Map<String, Animation> animationMap = new HashMap<String, Animation>();

    private TextureAtlas textureAtlas;
    private String atlasSuffix = "";
    private String atlasPrefix = "";
    private float duration = 0;

    private Direction direction = Direction.left;

    public AnimatedPhysicsActor(World world, Vector2 size, TextureAtlas textureAtlas, String atlasPrefix, String atlasSuffix) {
        super(world, size);
        this.textureAtlas = textureAtlas;
        this.atlasPrefix = atlasPrefix;
        this.atlasSuffix = atlasSuffix;
        prepareDefaultAnimations();
    }

    private void prepareDefaultAnimations() {
        prepareAnimation(0.1f, atlasPrefix + "idle" + atlasSuffix, "idle");
        activateAnimation("idle");
    }

    private void prepareAnimation(float speed, String atlasAnmiationName, String finalAnimationName) {
        final Array<Sprite> sprites = textureAtlas.createSprites(atlasAnmiationName);
        final Animation animation = new Animation(speed, sprites);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        addAnimation(finalAnimationName, animation);
    }

    private void addAnimation(String finalAnimationName, Animation animation) {
        animationMap.put(finalAnimationName, animation);
    }

    private void activateAnimation(String name) {
        if (animationMap.containsKey(name)) {
            currentAnimation = animationMap.get(name);
            duration = 0;
        }
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
            final float x = getBody().getPosition().x;
            final float y = getBody().getPosition().y;
            final float width = getSize().x;
            final float height = getSize().y;
            final float originX = getOriginX();
            final float originY = getOriginY();
            switch (direction) {
                case right:
                    batch.draw(currentFrame, x - originX, y - originY, width, height);
                    break;
                case left:
                    batch.draw(currentFrame, x + originX, y - originY, -width, height);
                    break;
            }
        }
    }
}
