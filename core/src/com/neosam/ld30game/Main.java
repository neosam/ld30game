package com.neosam.ld30game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Main extends Game {

    @Override
    public void create() {
        final IngameScreenDef ingameScreenDef = new IngameScreenDef();
        ingameScreenDef.background1 = "background2.png";
        ingameScreenDef.background2 = "background.png";
        ingameScreenDef.map1 = "map2.tmx";
        ingameScreenDef.map2 = "map.tmx";
        ingameScreenDef.map2Offset = new Vector2(200, 0);
        ingameScreenDef.background2Offset = new Vector2(-30, 0);
        setScreen(new IngameScreen(ingameScreenDef));
    }
}
