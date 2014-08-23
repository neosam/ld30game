package com.neosam.ld30game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
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

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private Hero hero;

    private AssetManager assetManager;

    private MapController map;

    public IngameScreen() {
        initializeEssencials();
        loadAssets();
        initializePhysics();
        initializeMap();
        initializeActors();
    }

    private void initializeMap() {
        map = new MapController("map.tmx");
        map.applyPhysics(world);
    }

    private void loadAssets() {
        assetManager = new AssetManager();
        assetManager.load("hero.txt", TextureAtlas.class);
        assetManager.finishLoading();
    }

    private void initializeActors() {
        final TextureAtlas textureAtlas = assetManager.get("hero.txt", TextureAtlas.class);
        hero = new Hero(world, new Vector2(2, 4), textureAtlas, "hero_", "_");
        final Vector2 playerSpawnPoint = map.getTriggerPoint("player_spawn");
        hero.getBody().setTransform(playerSpawnPoint, 0);
        stage.addActor(hero);
        stage.setKeyboardFocus(hero);
    }

    private void initializePhysics() {
        world = new World(new Vector2(0, -100), true);
        debugRenderer = new Box2DDebugRenderer();
    }

    private void initializeEssencials() {
        final Viewport viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);
        camera = (OrthographicCamera) viewport.getCamera();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        final Batch batch = stage.getSpriteBatch();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        world.step(delta, 2, 6);
        camera.position.set(hero.getX(), hero.getY(), 0);
        stage.act(delta);
        map.draw(batch);
        stage.draw();
        debugRenderer.render(world, camera.combined);
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
