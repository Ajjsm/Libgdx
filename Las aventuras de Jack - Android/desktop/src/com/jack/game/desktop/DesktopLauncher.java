package com.jack.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jack.game.JackGame;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Las aventuras de Jack";
		config.addIcon("bicho_bola.png", Files.FileType.Internal);
		new LwjglApplication(new JackGame(), config);
	}
}
