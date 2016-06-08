package com.jack.game.enemigos;

;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.jack.game.pantallas.PantallaJuego;
import com.jack.game.sprites.Jack;

/**
 * Created by JuanAJ on 21/05/2016.
 */
public abstract class Enemigo extends Sprite {
    protected World world;
    protected PantallaJuego screen;
    public Body b2body;
    public Vector2 velocidad;

    public Enemigo(PantallaJuego screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        definirEnemigo();
        velocidad = new Vector2(1, 0);

    }

    protected  abstract void definirEnemigo();
    public abstract void update(float dt);
    public abstract void golpearCabeza(Jack jack);

    public void invertirVelocidad(boolean x, boolean y){
        if(x)
            velocidad.x = -velocidad.x;
        if(y)
            velocidad.y = -velocidad.y;
    }
}
