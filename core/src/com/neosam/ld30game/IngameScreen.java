package com.neosam.ld30game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private IngameScreenDef ingameScreenDef;

    private int WORLD_WIDTH = 30;
    private int WORLD_HEIGHT = 20;

    private Stage stage;
    private OrthographicCamera camera;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private Hero hero;

    private AssetManager assetManager;

    private MapController map, map2;
    private BackgroundController background, background2;
    private int currentWorld = 1;
    private float cameraMovementFactorX = 7f;
    private float cameraMovementFactorY = 30f;
    private CollisionController collisionController;

    public IngameScreen(IngameScreenDef ingameScreenDef) {
        this.ingameScreenDef = ingameScreenDef;
        initializeEssencials();
        loadAssets();
        initializePhysics();
        initializeMap();
        initializeBackground();
        initializeActors();
    }

    private void initializeBackground() {
        final Texture backgroundTexture1 = assetManager.get(ingameScreenDef.background1, Texture.class);
        background = new BackgroundController(camera, backgroundTexture1);
        final Texture backgroundTexture2 = assetManager.get(ingameScreenDef.background2, Texture.class);
        background2 = new BackgroundController(camera, backgroundTexture2);
    }

    private void initializeMap() {
        map = new MapController(ingameScreenDef.map1);
        map2 = new MapController(ingameScreenDef.map2);
        map2.getOffset().x = ingameScreenDef.map2Offset.x;
        map2.getOffset().y = ingameScreenDef.map2Offset.y;
        map.applyPhysics(world);
        map2.applyPhysics(world);
    }

    private void loadAssets() {
        assetManager = new AssetManager();
        assetManager.load("hero.txt", TextureAtlas.class);
        assetManager.load(ingameScreenDef.background1, Texture.class);
        assetManager.load(ingameScreenDef.background2, Texture.class);
        assetManager.finishLoading();
    }

    private void initializeActors() {
        final TextureAtlas textureAtlas = assetManager.get("hero.txt", TextureAtlas.class);
        hero = new Hero(world, new Vector2(2, 4), textureAtlas, "hero_", "_");
        final Vector2 playerSpawnPoint = map.getTriggerPoint("player_spawn");
        hero.getBody().setTransform(playerSpawnPoint, 0);
        collisionController.addCollisionCallback(hero);
        stage.addActor(hero);
        stage.setKeyboardFocus(hero);
    }

    private void initializePhysics() {
        world = new World(new Vector2(0, -100), true);
        debugRenderer = new Box2DDebugRenderer();
        collisionController = new CollisionController();
        world.setContactListener(collisionController);
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
        switch (currentWorld) {
            case 1:
                background.draw(batch);
                break;
            case 2:
                background2.draw(batch);
                break;
        }
        world.step(delta, 2, 6);
        camera.position.set(
                camera.position.x - (camera.position.x - hero.getX()) * cameraMovementFactorX * delta,
                camera.position.y - (camera.position.y - hero.getY()) * cameraMovementFactorY * delta,
                0);
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
