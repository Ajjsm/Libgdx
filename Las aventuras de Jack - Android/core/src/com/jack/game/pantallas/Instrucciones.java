package com.jack.game.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jack.game.JackGame;

/**
 * Created by JuanAJ on 27/05/2016.
 */
public class Instrucciones implements Screen{
    private Viewport viewport;
    private Stage stage;
    private FreeTypeFontGenerator generator;
    private BitmapFont fuenteIntroduccion;
    Label.LabelStyle estiloEtiqueta;

    private Game game;

    public Instrucciones(Game game){
        estiloEtiqueta = new Label.LabelStyle();
        estiloEtiqueta.font = getFuenteIntroduccion();
        estiloEtiqueta.fontColor = Color.WHITE;

        this.game = game;
        viewport = new FitViewport(JackGame.V_ANCHO +300, JackGame.V_ALTURA + 300, new OrthographicCamera());
        stage = new Stage(viewport, ((JackGame) game).batch);


        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("¡AYUDA A JACK! \n Consigue llegar hasta el final rompiendo \n todos los muros que puedas \n antes de que se agote el tiempo \n para conseguir la maxima puntuacion, \n pero cuidado con romper mas de la cuenta \n ¡LOS NECESITARAS PARA LLEGAR AL FINAL!", estiloEtiqueta);
        Label playAgainLabel = new Label("Click para iniciar juego", estiloEtiqueta);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            game.setScreen(new MenuPrincipal((JackGame) game));
            dispose();
        }
        Gdx.gl.glClearColor(.8f, .498039f, .196078f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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


    private BitmapFont getFuenteIntroduccion(){
        FileHandle fontFile = Gdx.files.internal("font/instrucciones2.ttf");
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)Math.ceil(24);
        generator = new FreeTypeFontGenerator(fontFile);
        generator.scaleForPixelHeight((int)Math.ceil(32));
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        fuenteIntroduccion = generator.generateFont(parameter);
        return fuenteIntroduccion;
    }
}
