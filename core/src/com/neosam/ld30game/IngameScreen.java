package com.neosam.ld30game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by neosam on 23.08.14.
 */
public class IngameScreen implements Screen {
    private int WORLD_WIDTH = 30;
    private int WORLD_HEIGHT = 20;

    private Stage stage;
    private OrthographicCamera camera;

    public IngameScreen() {
        initializeEssencials();
    }

    private void initializeEssencials() {
        final Viewport viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);
        camera = (OrthographicCamera) viewport.getCamera();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        final ExtendViewport viewport = (ExtendViewport) stage.getViewport();
        viewport.setWorldSize(width, height);
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

    }
}
