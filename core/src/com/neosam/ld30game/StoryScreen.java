package com.neosam.ld30game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by neosam on 24.08.14.
 */
public class StoryScreen implements Screen, InputProcessor {
    private final String[] imageNames;
    private IngameScreenListener ingameScreenListener;
    private Stage stage;
    private int currentImage = -1;
    private Texture currentTexture;
    private float lockTime = 0;

    public StoryScreen(String[] imageNames, IngameScreenListener ingameScreenListener) {
        this.imageNames = imageNames;
        this.ingameScreenListener = ingameScreenListener;
        final Viewport viewport = new ExtendViewport(640, 680);
        stage = new Stage();
        nextFrame();
        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void render(float delta) {
        final Batch batch = stage.getSpriteBatch();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (currentTexture != null) {
            batch.draw(currentTexture, 0, 0, 640, 480);
        }
        lockTime -= delta;
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void nextFrame() {
        currentImage++;
        if (currentTexture != null) {
            currentTexture.dispose();
        }
        if (currentImage >= imageNames.length) {
            ingameScreenListener.finished();
            return;
        }
        final AssetManager assetManager = new AssetManager();
        assetManager.load(imageNames[currentImage], Texture.class);
        assetManager.finishLoading();
        currentTexture = assetManager.get(imageNames[currentImage], Texture.class);
        lockTime = 0.5f;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            ingameScreenListener.finished();
        }
        if (lockTime < 0) {
            nextFrame();
        }
        return true;

    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
