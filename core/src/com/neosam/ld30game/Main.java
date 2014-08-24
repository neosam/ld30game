package com.neosam.ld30game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Main extends Game implements IngameScreenListener {
    private Screen newScreenOnNewAct = null;
    private int state = 0;

    @Override
    public void create() {
        loadStory1();
    }

    public void loadLevel0() {
        final IngameScreenDef ingameScreenDef = new IngameScreenDef();
        ingameScreenDef.background1 = "background2.png";
        ingameScreenDef.background2 = "background2.png";
        ingameScreenDef.map1 = "intro.tmx";
        ingameScreenDef.map2 = "intro.tmx";
        ingameScreenDef.map2Offset = new Vector2(200, 0);
        ingameScreenDef.background2Offset = new Vector2(-30, 0);
        ingameScreenDef.ingameScreenListener = this;
        IngameScreen ingameScreen = new IngameScreen(ingameScreenDef);
        ingameScreen.setPreventPlayerChange(true);
        newScreenOnNewAct = ingameScreen;
    }

    public void loadLevel1() {
        final IngameScreenDef ingameScreenDef = new IngameScreenDef();
        ingameScreenDef.background1 = "background2.png";
        ingameScreenDef.background2 = "background.png";
        ingameScreenDef.map1 = "level1-world1.tmx";
        ingameScreenDef.map2 = "level1-world2.tmx";
        ingameScreenDef.map2Offset = new Vector2(200, 0);
        ingameScreenDef.background2Offset = new Vector2(-30, 0);
        ingameScreenDef.ingameScreenListener = this;
        newScreenOnNewAct = new IngameScreen(ingameScreenDef);
    }

    public void loadLevel2() {
        final IngameScreenDef ingameScreenDef = new IngameScreenDef();
        ingameScreenDef.background1 = "background2.png";
        ingameScreenDef.background2 = "background.png";
        ingameScreenDef.map1 = "map2.tmx";
        ingameScreenDef.map2 = "map.tmx";
        ingameScreenDef.map2Offset = new Vector2(200, 0);
        ingameScreenDef.background2Offset = new Vector2(-30, 0);
        ingameScreenDef.ingameScreenListener = this;
        newScreenOnNewAct = new IngameScreen(ingameScreenDef);
    }

    public void loadStory1() {
        final String[] images = {
                "logo.png",
                "story/scene1/001.png",
                "story/scene1/002.png",
                "story/scene1/003.png",
                "story/scene1/004.png"
        };
        newScreenOnNewAct = new StoryScreen(images, this);
    }

    public void loadStory2() {
        final String[] images = {
                "story/scene2/001.png",
                "story/scene2/002.png",
                "story/scene2/003.png",
                "story/scene2/004.png",
                "story/scene2/005.5.png",
                "story/scene2/005.png",
                "story/scene2/006.png",
                "story/scene2/007.png",
                "story/scene2/008.png",
                "story/scene2/009.png",
                "story/scene2/010.png",
                "story/scene2/011.png",
                "story/scene2/012.png",
                "story/scene2/014.png",
                "story/scene2/015.png",
                "story/scene2/016.png",
                "story/scene2/017.5.png",
                "story/scene2/011.png",
                "story/scene2/012.png",
                "story/scene2/017.png",
                "story/scene2/018.5.png",
                "story/scene2/018.png",
                "story/scene2/019.5.png",
                "story/scene2/019.png",
        };
        newScreenOnNewAct = new StoryScreen(images, this);
    }

    public void loadStory3() {
        final String[] images = {
                "story/scene3/001.png",
                "story/scene3/002.png",
        };
        newScreenOnNewAct = new StoryScreen(images, this);
    }

    public void loadStory4() {
        final String[] images = {
                "story/scene4/001.png",
                "story/scene4/002.png",
                "story/scene4/003.png",
                "story/scene4/004.png",
                "story/scene4/005.png",
                "story/scene4/006.png",
                "story/scene4/007.png",
                "story/scene4/008.png",
        };
        newScreenOnNewAct = new StoryScreen(images, this);
    }

    @Override
    public void render() {
        if (newScreenOnNewAct != null) {
            final Screen oldScreen = getScreen();
            if (oldScreen != null) {
                oldScreen.dispose();
            }
            setScreen(newScreenOnNewAct);
            newScreenOnNewAct = null;
        }
        super.render();
    }

    @Override
    public void finished() {
        switch (state) {
            case 0:
                state = 10;
                loadLevel0();
                break;
            case 10:
                state = 20;
                loadStory2();
                break;
            case 20:
                state = 30;
                loadLevel1();
                break;
            case 30:
                state = 40;
                loadStory3();
                break;
            case 40:
                state = 50;
                loadLevel2();
                break;
            case 50:
                state = 60;
                loadStory4();
                break;
            default:
                System.exit(0);
        }
    }
}
