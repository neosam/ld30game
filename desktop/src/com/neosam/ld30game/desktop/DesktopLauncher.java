package com.neosam.ld30game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.neosam.ld30game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "The Fairy-Tale Tale - Ludum Dare 30 by neosam";
        config.addIcon("icon-128.png", Files.FileType.Internal);
        config.addIcon("icon-32.png", Files.FileType.Internal);
        config.addIcon("icon-16.png", Files.FileType.Internal);
		new LwjglApplication(new Main(), config);
	}
}
