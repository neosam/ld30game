package com.neosam.ld30game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.*;

/**
 * Created by neosam on 23.08.14.
 */
public class MapController {
    private TiledMap tiledMap;
    private Vector2 offset = new Vector2(0, 0);
    private Map<String, Vector2> portals;
    private Set<Fixture> dirts = new HashSet<Fixture>();
    private boolean dirtsActive;
    private Body customsPortalBody;

    public MapController(String path) {
        tiledMap = new TmxMapLoader().load(path);
        fetchPortals();
    }

    private void fetchPortals() {
        portals = getTriggerStartWith("portal_");
    }

    public void draw(Batch batch) {
        batch.begin();
        final Iterator<MapLayer> layerIterator = tiledMap.getLayers().iterator();
        while (layerIterator.hasNext()) {
            final MapLayer mapLayer = layerIterator.next();
            if (mapLayer instanceof TiledMapTileLayer) {
                final TiledMapTileLayer tiledLayer = (TiledMapTileLayer) mapLayer;
                final int width = tiledLayer.getWidth();
                final int height = tiledLayer.getHeight();
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        final TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                        if (cell == null) {
                            continue;
                        }
                        final TextureRegion textureRegion = cell.getTile().getTextureRegion();
                        batch.draw(textureRegion, x + offset.x, y + offset.y, 1, 1);
                    }
                }
            }
        }
        batch.end();
    }

    public void applyPhysics(World world) {
        final Iterator<MapLayer> layerIterator = tiledMap.getLayers().iterator();
        while (layerIterator.hasNext()) {
            final MapLayer mapLayer = layerIterator.next();
            if (mapLayer instanceof TiledMapTileLayer) {
                final TiledMapTileLayer tiledLayer = (TiledMapTileLayer) mapLayer;
                final int width = tiledLayer.getWidth();
                final int height = tiledLayer.getHeight();
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        final TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                        if (cell == null) {
                            continue;
                        }
                        final boolean dirt = cell.getTile().getProperties().containsKey("dirt");
                        addBody(world, x + offset.x, y + offset.y, dirt);
                    }
                }
            }
        }

        for (String portalName: portals.keySet()) {
            final Vector2 portalPosition = portals.get(portalName);
            addPortalBody(world, portalName, portalPosition);
        }
    }

    private Body addPortalBody(World world, String portalName, Vector2 portalPosition) {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        final Body body = world.createBody(bodyDef);
        body.setTransform(portalPosition.x + offset.x, portalPosition.y + offset.y, 0);

        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.1f, 0.1f);
        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        final Fixture fixture = body.createFixture(fixtureDef);
        body.setUserData(portalName);
        fixture.setUserData("portal");
        shape.dispose();
        return body;
    }

    private void addBody(World world, float x, float y, boolean dirt) {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        final Body body = world.createBody(bodyDef);
        body.setTransform(x + 0.5f, y + 0.5f, 0);

        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.1f, 0.1f);
        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        final Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        if (dirt) {
            dirts.add(fixture);
        }
    }

    public Vector2 getTriggerPoint(String name) {
        final MapObject triggerObject = tiledMap.getLayers().get("trigger").getObjects().get(name);
        final int tileWidth = (Integer) tiledMap.getProperties().get("tilewidth");
        final int tileHeight = (Integer) tiledMap.getProperties().get("tileheight");
        return new Vector2((Float) triggerObject.getProperties().get("x") / tileWidth,
                (Float) triggerObject.getProperties().get("y") / tileHeight);
    }

    public Map<String, Vector2> getTriggerStartWith(String name) {
        final HashMap<String, Vector2> triggerObjects = new HashMap<String, Vector2>();
        final Iterator<MapObject> mapObjectIterator = tiledMap.getLayers().get("trigger").getObjects().iterator();
        final int tileWidth = (Integer) tiledMap.getProperties().get("tilewidth");
        final int tileHeight = (Integer) tiledMap.getProperties().get("tileheight");
        while (mapObjectIterator.hasNext()) {
            final MapObject mapObject = mapObjectIterator.next();
            if (mapObject != null && mapObject.getName() != null && mapObject.getName().startsWith(name)) {
                triggerObjects.put(mapObject.getName(), new Vector2(
                        (Float) mapObject.getProperties().get("x") / tileWidth,
                        (Float) mapObject.getProperties().get("y") / tileHeight));
            }
        }
        return triggerObjects;
    }

    public void setCustomPortal(World world, float x, float y) {
        if (customsPortalBody != null) {
            world.destroyBody(customsPortalBody);
        }
        final Vector2 portalPos = new Vector2(x - offset.x, y - offset.y);
        portals.put("portal_custom", portalPos);
        customsPortalBody = addPortalBody(world, "portal_custom", portalPos);
    }



    public Vector2 getOffset() {
        return offset;
    }

    public boolean hasPortal(String name) {
        return portals.containsKey(name);
    }

    public Vector2 getPortal(String portal) {
        return portals.get(portal).cpy();
    }

    public Map<String, Vector2> getPortals() {
        return portals;
    }

    public boolean isDirtsActive() {
        return dirtsActive;
    }

    public void setDirtsActive(boolean dirtsActive) {
        this.dirtsActive = dirtsActive;
        for (Fixture fixture: dirts) {
            //fixture.setSensor(dirtsActive);
            fixture.getBody().setActive(dirtsActive);
        }
    }
}
