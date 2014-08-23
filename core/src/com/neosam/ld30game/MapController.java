package com.neosam.ld30game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

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
}
