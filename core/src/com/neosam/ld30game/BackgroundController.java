package com.neosam.ld30game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by neosam on 23.08.14.
 */
public class BackgroundController {
    private OrthographicCamera camera;
    private Texture texture;
    private float offsetX = -15, offsetY = -10;
    private float sizeX = 60, sizeY = 60;
    private float scrollSpeed = 0.9f;

    public BackgroundController(OrthographicCamera camera, Texture texture) {
        this.camera = camera;
        this.texture = texture;
    }

    public void draw(Batch batch) {
        batch.begin();
        batch.draw(texture, scrollSpeed * camera.position.x + offsetX,
                scrollSpeed * camera.position.y + offsetY,
                sizeX, sizeY);
        batch.end();
    }
}
