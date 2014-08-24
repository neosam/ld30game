package com.neosam.ld30game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by neosam on 24.08.14.
 */
public class SimpleActor  extends Actor {
    private Texture texture;

    public SimpleActor(Texture texture) {
        this.texture = texture;
        setSize(2, 4);
        setOrigin(1, 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX() - getOriginX(), getY() - getOriginY(), getWidth(), getHeight());
    }
}
