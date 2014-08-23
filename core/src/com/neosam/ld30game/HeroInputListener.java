package com.neosam.ld30game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by neosam on 23.08.14.
 */
public class HeroInputListener extends InputListener {
    private Hero hero;
    private Settings settings = Settings.settings;

    public HeroInputListener(Hero hero) {
        this.hero = hero;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if (keycode == settings.leftKey) {
            hero.startRun(Direction.left);
        } else if (keycode == settings.rightKey) {
            hero.startRun(Direction.right);
        } else if (keycode == settings.jumpKey) {
            hero.jump();
        }else {
            return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if (keycode == settings.leftKey) {
            hero.stopRun();
        } else if (keycode == settings.rightKey) {
            hero.stopRun();
        } else {
            return false;
        }
        return true;
    }
}
