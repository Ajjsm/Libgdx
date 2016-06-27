package com.jack.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jack.game.JackGame;

/**
 * Created by brentaureli on 10/8/15.
 */
public class NivelSuperado implements Screen {
    private Stage stage;
    private Preferences preferences;
    private JackGame game;
    public NivelSuperado(JackGame game) {
        this.game = game;
    }

    private void cargar(){
        stage = new Stage();
        Table table = new Table();
        table.setPosition(JackGame.V_ANCHO / 1.7f, JackGame.V_ALTURA / 0.5f);
        table.setFillParent(true);
        table.setHeight(500);
        stage.addActor(table);
        Label.LabelStyle fuente = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Label label = new Label("¡NIVEL SUPERADO!", fuente);

        label.setPosition(label.getOriginX() + 15, label.getOriginY() - 120);

        TextButton botonSig = new TextButton("Siguiente nivel", game.getSkin());
        botonSig.setPosition(label.getOriginX(), label.getOriginY() - 170);
        botonSig.setWidth(200);
        botonSig.setHeight(40);
        botonSig.center();
        botonSig.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new PantallaJuego(game));

            }
        });
        table.addActor(botonSig);

        TextButton botonGuardar = new TextButton("Guardar", game.getSkin());
        botonGuardar.setPosition(label.getOriginX(), label.getOriginY() - 220);
        botonGuardar.setWidth(200);
        botonGuardar.setHeight(40);
        botonGuardar.center();
        botonGuardar.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                getPrefs();

                preferences.putInteger("partidaGuardada", PantallaJuego.nivel);
                System.out.println("Print con pantalla actual" + preferences.getString("partidaGuardada"));
                preferences.flush();

            }
        });
        table.addActor(botonGuardar);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        cargar();
    }

    @Override
    public void render(float dt) {

        Gdx.gl.glClearColor(.8f, .498039f, .196078f, 0);
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
        stage.dispose();
    }

    protected Preferences getPrefs() {
        if(preferences==null){
            preferences = Gdx.app.getPreferences("partidaGuardada");
        }
        return preferences;
    }
}
