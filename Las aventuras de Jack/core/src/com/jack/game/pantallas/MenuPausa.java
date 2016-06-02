package com.jack.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jack.game.JackGame;

/**
 * Created by JuanAJ on 26/05/2016.
 */
public class MenuPausa implements Screen {
    JackGame game;
    PantallaJuego pantallaJuego;
    Stage stage;
    private Skin botonSkin;
    private TextureAtlas botonAtlas;
    TextButton.TextButtonStyle estilo;

    Label.LabelStyle estiloEtiqueta;
    BitmapFont fuente;


    public MenuPausa(JackGame game, PantallaJuego pantallaJuego){
        this.game = game;
        this.pantallaJuego = pantallaJuego;
        fuente = new BitmapFont();
        botonAtlas = new TextureAtlas("ui2/ui-orange.atlas");
        botonSkin = new Skin();
        botonSkin.addRegions(botonAtlas);
        estilo = new TextButton.TextButtonStyle();
        estilo.up = botonSkin.getDrawable("button_01");
        estilo.down = botonSkin.getDrawable("button_02");
        estilo.downFontColor = Color.RED;
        estilo.font = fuente;

        estiloEtiqueta = new Label.LabelStyle();
        estiloEtiqueta.font = fuente;
        estiloEtiqueta.fontColor = Color.BROWN;
    }

    private void cargarPantalla(){
        stage = new Stage();
        Table table = new Table();
        table.setPosition(JackGame.V_ANCHO / 1.75f, JackGame.V_ALTURA / 0.6f);
        table.setFillParent(true);
        table.setHeight(500);
        stage.addActor(table);

        Label label = new Label("Las Aventuras de Jack - PAUSA", estiloEtiqueta);

        TextButton botonResumen = new TextButton("Continuar", estilo);
        botonResumen.setPosition(label.getOriginX(), label.getOriginY() - 60);
        botonResumen.setWidth(200);
        botonResumen.setHeight(40);
        botonResumen.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pantallaJuego.musica.setVolume(1);
                dispose();
                game.setScreen(pantallaJuego);

            }
        });
        table.addActor(botonResumen);



        TextButton botonMenuPrincipal = new TextButton("Salir de la partida", estilo);
        botonMenuPrincipal.setPosition(label.getOriginX(), label.getOriginY() - 120);
        botonMenuPrincipal.setWidth(200);
        botonMenuPrincipal.setHeight(40);
        botonMenuPrincipal.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                game.setScreen(new MenuPrincipal(game));
            }
        });
        table.addActor(botonMenuPrincipal);

        TextButton botonSalir = new TextButton("Cerrar juego", estilo);
        botonSalir.setPosition(label.getOriginX(), label.getOriginY() - 180);
        botonSalir.setWidth(200);
        botonSalir.setHeight(40);
        botonSalir.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                System.exit(0);
            }
        });
        table.addActor(botonSalir);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        cargarPantalla();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, .627451f, .478431f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(dt);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
