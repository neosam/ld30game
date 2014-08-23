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

import java.util.Iterator;

/**
 * Created by neosam on 23.08.14.
 */
public class MapController {
    private TiledMap tiledMap;

    public MapController(String path) {
        tiledMap = new TmxMapLoader().load(path);
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
                        batch.draw(textureRegion, x, y, 1, 1);
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
                        addBody(world, x, y);
                    }
                }
            }
        }
    }

    private void addBody(World world, int x, int y) {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        final Body body = world.createBody(bodyDef);
        body.setTransform(x + 0.5f, y + 0.5f, 0);

        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.1f, 0.1f);
        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public Vector2 getTriggerPoint(String name) {
        final MapObject triggerObject = tiledMap.getLayers().get("trigger").getObjects().get(name);
        final int tileWidth = (Integer) tiledMap.getProperties().get("tilewidth");
        final int tileHeight = (Integer) tiledMap.getProperties().get("tileheight");
        return new Vector2((Float) triggerObject.getProperties().get("x") / tileWidth,
                (Float) triggerObject.getProperties().get("y") / tileHeight);
    }
}
