package com.jack.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.jack.game.JackGame;
import com.jack.game.interfaz.Hud;
import com.jack.game.pantallas.PantallaJuego;

/**
 * Created by JuanAJ on 28/04/2016.
 */
public class Ladrillos extends InteraccionTiles {
    PantallaJuego pantallaJuego;
    public Ladrillos(PantallaJuego screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
        setFiltroCategoria(JackGame.LADRILLO_BIT);
    }

    @Override
    public void cabezaGolpeo() {
        Gdx.app.log("Ladrillo", "Colision");
        setFiltroCategoria(JackGame.DESTRUIDO_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
        pantallaJuego = new PantallaJuego();
        if (pantallaJuego.isSonido==true) {
            JackGame.manager.get("sonidos/juego/romper_muro.wav", Sound.class).play();
        }
    }

}
