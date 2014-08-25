package com.neosam.ld30game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by neosam on 24.08.14.
 */
public class MoveHeroScreen implements Screen {
    private Stage stage;
    private Texture background;
    private AssetManager assetManager;
    private IngameScreenListener ingameScreenListener;
    private float duration = 2f;

    public MoveHeroScreen(String backgroundPath, String spritePath, Vector2 from, Vector2 to,
                          IngameScreenListener ingameScreenListener) {
        this.ingameScreenListener = ingameScreenListener;
        Viewport viewport = new ExtendViewport(30, 20);
        stage = new Stage(viewport);
        assetManager = new AssetManager();
        assetManager.load(backgroundPath, Texture.class);
        assetManager.load(spritePath, Texture.class);
        assetManager.finishLoading();
        background = assetManager.get(backgroundPath, Texture.class);
        final Texture sprite = assetManager.get(spritePath, Texture.class);
        Actor actor = new SimpleActor(sprite);
        actor.setPosition(from.x, from.y);
        stage.addActor(actor);
        actor.addAction(Actions.moveTo(to.x, to.y, 1.5f));
        Gdx.input.setInputProcessor(new InputAdapter());
    }

    @Override
    public void render(float delta) {
        duration -= delta;
        if (duration < 0) {
            ingameScreenListener.finished();
        }
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        stage.getCamera().update();
        final Batch batch = stage.getSpriteBatch();
        batch.begin();
        batch.draw(background, 0, 0, 30, 20);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        final ExtendViewport viewport = (ExtendViewport) stage.getViewport();
        viewport.update(width, height, false);
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
        assetManager.dispose();
    }
}
