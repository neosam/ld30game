package com.neosam.ld30game;

import com.badlogic.gdx.Input;

/**
 * Created by neosam on 23.08.14.
 */
public class Settings {
    static Settings settings = new Settings();

    public int leftKey = Input.Keys.LEFT;
    public int rightKey = Input.Keys.RIGHT;
    public int jumpKey = Input.Keys.UP;

    public boolean jumpCheat = true;
}
