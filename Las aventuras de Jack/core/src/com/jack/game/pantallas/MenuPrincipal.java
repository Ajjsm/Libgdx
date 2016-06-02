package com.jack.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.jack.game.JackGame;


/**
 * Created by JuanAJ on 23/05/2016.
 */
public class MenuPrincipal implements Screen{
    private JackGame game;
    private Stage stage;
    private Preferences preferences;
    private Label.LabelStyle estiloEtiqueta;
    private BitmapFont fuenteTitulo;
    public static boolean isMusica;

    public MenuPrincipal(JackGame game){
        isMusica = true;
        this.game = game;

        estiloEtiqueta = new Label.LabelStyle();
        estiloEtiqueta.font = getFuenteTitulo();
        estiloEtiqueta.fontColor = Color.BLACK;

    }



    private void cargarPantalla(){
        stage = new Stage();
        Table table = new Table();
        table.setPosition(JackGame.V_ANCHO / 6f, JackGame.V_ALTURA / 0.55f);
        table.setFillParent(true);
        table.setHeight(500);
        stage.addActor(table);

        Label etiqueta = new Label("LA AVENTURA!", estiloEtiqueta);

        Label etiqueta2 = new Label("JACK:", estiloEtiqueta);
        etiqueta.setPosition(etiqueta.getOriginX(), 30);

        etiqueta.setPosition(etiqueta.getOriginX(), etiqueta2.getOriginY()- 60);


        table.addActor(etiqueta);
        table.addActor(etiqueta2);

        TextButton buttonPlay = new TextButton("Jugar!", game.getSkin());
        buttonPlay.setPosition(etiqueta2.getOriginX(), etiqueta2.getOriginY() - 120);
        buttonPlay.setWidth(200);
        buttonPlay.setHeight(40);
        buttonPlay.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                game.setScreen(new PantallaJuego(game));
            }
        });
        table.addActor(buttonPlay);

        // Botón
        TextButton botonConfiguracion = new TextButton("Configurar", game.getSkin());
        botonConfiguracion.setPosition(etiqueta2.getOriginX(), etiqueta2.getOriginY() - 170);
        botonConfiguracion.setWidth(200);
        botonConfiguracion.setHeight(40);
        botonConfiguracion.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MenuConfiguracion(game));

            }
        });
        table.addActor(botonConfiguracion);

        // Botón
        TextButton botonSalir = new TextButton("Salir", game.getSkin());
        botonSalir.setPosition(etiqueta2.getOriginX(), etiqueta2.getOriginY() - 360);
        botonSalir.setWidth(200);
        botonSalir.setHeight(40);
        botonSalir.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.dispose();
                System.exit(0);
            }
        });
        table.addActor(botonSalir);

        TextButton botonEliminar = new TextButton("Eliminar", game.getSkin());
        botonEliminar.setPosition(etiqueta2.getOriginX(), etiqueta2.getOriginY() - 220);
        botonEliminar.setWidth(200);
        botonEliminar.setHeight(40);
        botonEliminar.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Dialog dialogo = new Dialog("Confirmacion", game.getSkin(), "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                        if (String.valueOf(obj) == "true") {
                            getPrefs();
                            preferences.putInteger("partidaGuardada", 0);
                            PantallaJuego.nivel = preferences.getInteger("partidaGuardada");
                        }

                    }
                };
                dialogo.text("ESTAS SEGURO?");
                dialogo.button("Si", true);
                dialogo.button("No", false);
                dialogo.key(Input.Keys.ENTER, true);

                dialogo.setPosition(150, 150);
                dialogo.setSize(400, 200);

                stage.addActor(dialogo);

            }
        });
        table.addActor(botonEliminar);

        TextButton botonCargar = new TextButton("Cargar datos", game.getSkin());
        botonCargar.setPosition(etiqueta.getOriginX(), etiqueta.getOriginY() - 270);
        botonCargar.setWidth(200);
        botonCargar.setHeight(40);
        botonCargar.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                getPrefs();
                System.out.println(preferences.getInteger("partidaGuardada"));
                PantallaJuego.nivel = preferences.getInteger("partidaGuardada");
            }
        });
        table.addActor(botonCargar);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        cargarPantalla();
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

    protected Preferences getPrefs() {
        if(preferences==null){
            preferences = Gdx.app.getPreferences("partidaGuardada");
        }
        return preferences;
    }

    private BitmapFont getFuenteTitulo(){
        FileHandle fontFile = Gdx.files.internal("font/cartoon.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 68;
        fuenteTitulo = generator.generateFont(parameter);
        return fuenteTitulo;
    }
}
