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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by neosam on 23.08.14.
 */
public class IngameScreen implements Screen, HeroCollisionListener {
    private IngameScreenDef ingameScreenDef;

    private int WORLD_WIDTH = 30;
    private int WORLD_HEIGHT = 20;

    private Stage stage;
    private OrthographicCamera camera;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private float freezePhysicsFor = 0;

    private Hero hero, hero2;
    private Portal customPortal1, customPortal2;
    private int currentHero = 1;

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
        background2.addOffset(-ingameScreenDef.background2Offset.x, -ingameScreenDef.background2Offset.y);
    }

    private void initializeMap() {
        map = new MapController(ingameScreenDef.map1);
        map2 = new MapController(ingameScreenDef.map2);
        map2.getOffset().x = ingameScreenDef.map2Offset.x;
        map2.getOffset().y = ingameScreenDef.map2Offset.y;
        map.applyPhysics(world);
        map2.applyPhysics(world);

        initializePortals(map, new Vector2(0, 0));
        initializePortals(map2, ingameScreenDef.map2Offset);
    }

    private void initializePortals(MapController map, Vector2 offset) {
        final Map<String, Vector2> portals = map.getPortals();
        for (Vector2 portalPosition: portals.values()) {
            createPortalActor(portalPosition, offset);
        }
    }

    private Portal createPortalActor(Vector2 portalPosition, Vector2 offset) {
        final Portal newPortal = new Portal(assetManager.get("hero.txt", TextureAtlas.class));
        newPortal.setPosition(portalPosition.x + offset.x, portalPosition.y + offset.y);
        stage.addActor(newPortal);
        return newPortal;
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
        hero = new Hero(world, new Vector2(2, 4), textureAtlas, "hero2_", "_", this);
        final Vector2 playerSpawnPoint = map.getTriggerPoint("player_spawn");
        hero.getBody().setTransform(playerSpawnPoint, 0);
        collisionController.addCollisionCallback(hero);
        hero.setPortalCreateable(true);
        stage.addActor(hero);

        hero2 = new Hero(world, new Vector2(2, 4), textureAtlas, "hero_", "_", this);
        final Vector2 player2SpawnPoint = map.getTriggerPoint("player2_spawn");
        hero2.getBody().setTransform(player2SpawnPoint, 0);
        collisionController.addCollisionCallback(hero2);
        hero2.setMultijumpable(true);
        hero2.setPortalCreateable(true);
        stage.addActor(hero2);
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
        freezePhysicsFor -= delta;
        if (freezePhysicsFor < 0) {
            world.step(delta, 2, 6);
        }
        final Hero hero = (currentHero == 1)? this.hero: hero2;
        camera.position.set(
                camera.position.x - (camera.position.x - hero.getX()) * cameraMovementFactorX * delta,
                camera.position.y - (camera.position.y - hero.getY()) * cameraMovementFactorY * delta,
                0);
        stage.act(delta);
        switch (currentWorld) {
            case 1:
                map.draw(batch);
                break;
            case 2:
                map2.draw(batch);
                break;
        }
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

    @Override
    public void collisionWithPortal(String portal) {
        MapController map = (currentWorld == 1) ? map2 : this.map;
        if (map.hasPortal(portal)) {
            swapWorlds(portal);
        }
    }

    @Override
    public void switchHero() {
        switch (currentHero) {
            case 1:
                stage.setKeyboardFocus(hero2);
                currentHero = 2;
                map.setDirtsActive(true);
                map2.setDirtsActive(true);
                break;
            case 2:
                stage.setKeyboardFocus(hero);
                currentHero = 1;
                map.setDirtsActive(false);
                map2.setDirtsActive(false);
                break;
        }
        Hero hero = (currentHero == 1) ? this.hero : hero2;
        Hero inactiveHero = (currentHero == 1) ? hero2 : this.hero;
        inactiveHero.getBody().setType(BodyDef.BodyType.StaticBody);
        hero.getBody().setType(BodyDef.BodyType.DynamicBody);


        if (hero.getWorld() != currentWorld) {
            swapWorlds(null);
        }
    }

    @Override
    public void createPortal() {
        Hero hero = (currentHero == 1) ? this.hero : hero2;
        MapController map = (currentWorld == 1) ? this.map : map2;
        map.setCustomPortal(world, hero.getX(), hero.getY());
        Portal customPortal = (currentWorld == 1) ? customPortal1 : customPortal2;
        if (customPortal == null) {
            customPortal = createPortalActor(new Vector2(hero.getX(), hero.getY()), new Vector2(0, 0));
            if (currentWorld == 1) {
                customPortal1 = customPortal;
            } else if(currentWorld == 2) {
                customPortal2 = customPortal;
            }
        } else {
            customPortal.setPosition(hero.getX(), hero.getY());
        }
    }

    @Override
    public void finishTouched() {
        ingameScreenDef.ingameScreenListener.finished();
    }

    private void swapWorlds(String portal) {
        MapController newMap;
        Hero hero = (currentHero == 1) ? this.hero : hero2;
        switch (currentWorld) {
            case 1:
                newMap = map2;
                currentWorld = 2;
                Gdx.app.log("Swap", "Swap worlds to 2");
                break;
            case 2:
                newMap = map;
                currentWorld = 1;
                Gdx.app.log("Swap", "Swap worlds to 1");
                break;
            default:
                /* should not happen.  But do nothing if so */
                return;
        }
        hero.setWorld(currentWorld);
        freezePhysicsFor = 0.5f;
        if (portal == null) {
            return;
        }
        final Vector2 destinationPortal = newMap.getPortal(portal);
        destinationPortal.add(newMap.getOffset());
        hero.setPositionNextAct(destinationPortal);
    }
}
