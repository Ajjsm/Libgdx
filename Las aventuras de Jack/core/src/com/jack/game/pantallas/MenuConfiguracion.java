package com.jack.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jack.game.JackGame;


/**
 * Created by JuanAJ on 23/05/2016.
 */
public class MenuConfiguracion implements Screen{
    JackGame game;
    Stage stage;

    public static boolean isMusica;

    public MenuConfiguracion(JackGame game){
        isMusica = true;
        this.game = game;

    }

    private void loadScreen(){
        stage = new Stage();
        Table table = new Table();
        table.setPosition(JackGame.V_ANCHO / 1.7f, JackGame.V_ALTURA / 0.5f);
        table.setFillParent(true);
        table.setHeight(500);
        stage.addActor(table);

        Label label = new Label("Configuracion", game.getSkin());
        label.setPosition(label.getOriginX() + 15, label.getOriginY() - 60);

        TextButton buttonPlay = new TextButton("1024 x 840", game.getSkin());
        buttonPlay.setPosition(label.getOriginX(), label.getOriginY() - 120);
        buttonPlay.setWidth(200);
        buttonPlay.setHeight(40);
        buttonPlay.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.graphics.setWindowedMode(1024, 840);
                game.setScreen(new MenuConfiguracion(game));

            }
        });
        table.addActor(buttonPlay);

        // Botón
        TextButton buttonConfig = new TextButton("640 x 800", game.getSkin());
        buttonConfig.setPosition(label.getOriginX(), label.getOriginY() - 170);
        buttonConfig.setWidth(200);
        buttonConfig.setHeight(40);
        buttonConfig.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.graphics.setWindowedMode(800, 640);
                game.setScreen(new MenuConfiguracion(game));
            }
        });
        table.addActor(buttonConfig);

        // Botón
        TextButton buttonQuit = new TextButton("Volver", game.getSkin());
        buttonQuit.setPosition(label.getOriginX(), label.getOriginY() - 220);
        buttonQuit.setWidth(200);
        buttonQuit.setHeight(40);
        buttonQuit.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //game.dispose();
                game.setScreen(new MenuPrincipal(game));
            }
        });
        table.addActor(buttonQuit);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        loadScreen();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.35f, 0.16f, 0.14f, 0.5f);
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
}
