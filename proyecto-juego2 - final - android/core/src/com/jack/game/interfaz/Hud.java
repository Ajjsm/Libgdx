package com.jack.game.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jack.game.JackGame;
import com.jack.game.pantallas.PantallaJuego;


/**
 * Created by JuanAJ on 28/04/2016.
 */
public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewPort;

    private Integer contadorMundo;
    private float contadorTiempo;
    public static Integer puntuacion;
    private static Integer nivelActual;

    private Label cuentatrasLabel;
    private static Label puntuacionLabel;
    private Label tiempoLabel;
    private Label nivelLabel;
    private Label mundoLabel;
    private Label jackLabel;
    private Label pausa;
    private Label muteon;
    private Label muteof;
    private PantallaJuego pantallaJuego;

    private FreeTypeFontGenerator generator;
    private BitmapFont fuenteHud;
    Label.LabelStyle estiloEtiqueta;

    public Hud(SpriteBatch sb){
        estiloEtiqueta = new Label.LabelStyle();
        estiloEtiqueta.font = getFuenteIntroduccion();
        estiloEtiqueta.fontColor = Color.BLACK;

        pantallaJuego = new PantallaJuego();
        nivelActual = pantallaJuego.getNivel();
        contadorMundo = 60;
        contadorTiempo = 0;
        puntuacion = 0;
        viewPort = new FitViewport(JackGame.V_ANCHO, JackGame.V_ALTURA, new OrthographicCamera());
        stage = new Stage(viewPort, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        cuentatrasLabel = new Label(String.format("%03d", contadorMundo), estiloEtiqueta);
        puntuacionLabel = new Label(String.format("%06d", puntuacion), estiloEtiqueta);
        tiempoLabel = new Label("TIEMPO", estiloEtiqueta);
        nivelLabel = new Label(Integer.toString(nivelActual), estiloEtiqueta);
        mundoLabel = new Label("NIVEL", estiloEtiqueta);
        jackLabel = new Label("PUNTOS", estiloEtiqueta);
        pausa = new Label("ESC: Pausar ", estiloEtiqueta);
        muteon = new Label("N: Mute ON ", estiloEtiqueta);
        muteof = new Label("M: Mute OFF ",estiloEtiqueta);

        table.add(jackLabel).expandX().padTop(10);
        table.add(mundoLabel).expandX().padTop(10);
        table.add(tiempoLabel).expandX().padTop(10);
        table.row();
        table.add(puntuacionLabel).expandX();
        table.add(nivelLabel).expandX();
        table.add(cuentatrasLabel).expandX();
        /*table.row();
        table.add(pausa).padTop(80);
        table.row();
        table.add(muteon).padTop(10);
        table.row();
        table.add(muteof).padTop(10);*/

        stage.addActor(table);
    }

    public void update(float dt){
        contadorTiempo += dt;
        if(contadorTiempo >= 1){
            contadorMundo--;
            cuentatrasLabel.setText(String.format("%03d", contadorMundo));
            contadorTiempo = 0;
        }
    }

    public static void addScore(int value){
        puntuacion += value;
        puntuacionLabel.setText(String.format("%06d", puntuacion));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Integer getContadorMundo() {
        return contadorMundo;
    }

    public static void setNivelActual(Integer nivelActual) {
        Hud.nivelActual = nivelActual;
    }

    private BitmapFont getFuenteIntroduccion(){
        FileHandle fontFile = Gdx.files.internal("font/instrucciones2.ttf");

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)Math.ceil(24);
        generator = new FreeTypeFontGenerator(fontFile);

        generator.scaleForPixelHeight((int)Math.ceil(32));
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        fuenteHud = generator.generateFont(parameter);
        return fuenteHud;
    }
}
