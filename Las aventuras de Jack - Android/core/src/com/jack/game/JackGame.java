package com.jack.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jack.game.pantallas.Instrucciones;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class JackGame extends Game {
	public static final int V_ANCHO = 400;
	public static final int V_ALTURA = 208;
	public static final float PPM = 100;

	public static final short SINCOLISION_BIT = 0;
	public static final short SUELO_BIT = 1;
	public static final short JACK_BIT = 2;
	public static final short LADRILLO_BIT = 4;
	public static final short DESTRUIDO_BIT = 16;
	public static final short OBJETO_BIT = 32;
	public static final short ENEMIGO_BIT = 64;
	public static final short CABEZA_ENEMIGO_BIT = 128;
	public static final short OBJECT_FINAL_JUEGO = 2048;
    public static final short JACK_CABEZA_BIT = 512;
	public static final short OBJECT_FINAL = 1024;
    public static final short PINCHOS_BIT = 256;
	public boolean paused;

	public SpriteBatch batch;
	public static AssetManager manager;
	private Skin skin;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("sonidos/musica/musica_fondo.ogg", Music.class);
		manager.load("sonidos/musica/musica_nivel1.wav", Music.class);
		manager.load("sonidos/juego/romper_muro.wav", Sound.class);
		manager.load("sonidos/juego/game_over.wav", Sound.class);
		manager.load("sonidos/juego/nivel_superado.mp3", Sound.class);
		manager.load("sonidos/juego/marron_muerto.wav", Sound.class);
		manager.load("sonidos/musica/musica_nivel2.mp3", Sound.class);
		manager.load("sonidos/musica/musica_nivel3.mp3", Sound.class);
		manager.finishLoading();
		setScreen(new Instrucciones(this));
	}

	@Override
	public void dispose(){
		super.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}

	public Skin getSkin() {
		if (skin == null) {
			skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		}
		return skin;
	}

}
