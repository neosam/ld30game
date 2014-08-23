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
    private Settings settings = Settings.settings;

    private Animation currentAnimation;
    private TextureRegion currentFrame;
    private Map<String, Animation> animationMap = new HashMap<String, Animation>();

    private TextureAtlas textureAtlas;
    private String atlasSuffix = "";
    private String atlasPrefix = "";
    private float duration = 0;

    private Direction direction = Direction.left;
    private boolean running = false;
    private boolean jumping = false;
    private float maxSpeed = 30;
    private Vector2 leftImpulse = new Vector2(-5, 0);
    private Vector2 rightImpulse = new Vector2(5, 0);
    private Vector2 jumpImpulse = new Vector2(0, 60);

    public AnimatedPhysicsActor(World world, Vector2 size, TextureAtlas textureAtlas, String atlasPrefix, String atlasSuffix) {
        super(world, size);
        this.textureAtlas = textureAtlas;
        this.atlasPrefix = atlasPrefix;
        this.atlasSuffix = atlasSuffix;
        prepareDefaultAnimations();
    }

    private void prepareDefaultAnimations() {
        prepareAnimation(0.1f, atlasPrefix + "idle" + atlasSuffix, "idle");
        prepareAnimation(0.03f, atlasPrefix + "run" + atlasSuffix, "run");
        prepareAnimation(0.1f, atlasPrefix + "jump" + atlasSuffix, "jump");
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
            final Animation newAnimation = animationMap.get(name);
            if (currentAnimation != newAnimation) {
                currentAnimation = animationMap.get(name);
                duration = 0;
            }
        }
    }

    public void startRun(Direction direction) {
        this.direction = direction;
        running = true;
        refreshAnimation();
    }

    public void stopRun() {
        running = false;
        getBody().setLinearVelocity(0, getBody().getLinearVelocity().y);
        refreshAnimation();
    }

    private void refreshAnimation() {
        if (jumping) {
            activateAnimation("jump");
        } else if (running) {
            activateAnimation("run");
        } else {
            activateAnimation("idle");
        }
    }

    public void jump() {
        if (jumping == true && !settings.jumpCheat) {
            return;
        }
        getBody().applyLinearImpulse(jumpImpulse, getBody().getWorldCenter(), true);
        jumping = true;
        refreshAnimation();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        duration += delta;

        if (running) {
            if (direction == Direction.left) {
                getBody().applyLinearImpulse(leftImpulse, getBody().getWorldCenter(), true);
            } else if (direction == Direction.right) {
                getBody().applyLinearImpulse(rightImpulse, getBody().getWorldCenter(), true);
            }
            if (getBody().getLinearVelocity().x > maxSpeed) {
                getBody().setLinearVelocity(maxSpeed, getBody().getLinearVelocity().y);
            } else if (getBody().getLinearVelocity().x < -maxSpeed) {
                getBody().setLinearVelocity(-maxSpeed, getBody().getLinearVelocity().y);
            }
        }

        if (jumping) {
            if (getBody().getLinearVelocity().y == 0) {
                jumping = false;
                refreshAnimation();
            }
        }

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
