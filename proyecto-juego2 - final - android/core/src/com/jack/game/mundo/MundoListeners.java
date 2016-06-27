package com.jack.game.mundo;

import com.badlogic.gdx.physics.box2d.*;
import com.jack.game.JackGame;
import com.jack.game.enemigos.Enemigo;
import com.jack.game.sprites.InteraccionTiles;
import com.jack.game.sprites.Jack;

/**
 * Created by JuanAJ on 20/05/2016.
 */
public class MundoListeners implements ContactListener {
    private JackGame jackGame;

    @Override
    public void beginContact(Contact contact) {
        jackGame = new JackGame();
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case JackGame.JACK_CABEZA_BIT | JackGame.LADRILLO_BIT:
           // case JackGame.JACK_CABEZA_BIT | JackGame.COIN_BIT:
                if(fixA.getFilterData().categoryBits == JackGame.JACK_CABEZA_BIT)
                    ( (InteraccionTiles) fixB.getUserData()).cabezaGolpeo();
                else
                    ( (InteraccionTiles) fixA.getUserData()).cabezaGolpeo();
                break;
            //Si la cabeza del enemigo es colisionada con el cuerpo de jack se invocara la funcion golpearCabeza(matamos al malo)
            case (JackGame.CABEZA_ENEMIGO_BIT | JackGame.JACK_BIT):
                if(fixA.getFilterData().categoryBits == JackGame.CABEZA_ENEMIGO_BIT)
                    ((Enemigo)fixA.getUserData()).golpearCabeza((Jack) fixB.getUserData());
                else
                    ((Enemigo)fixB.getUserData()).golpearCabeza((Jack) fixA.getUserData());
                break;

            //Si el enemigo es colisionado en un tile en el que halla un objeto, la direccion en la que se mueve cambiara
            case (JackGame.ENEMIGO_BIT | JackGame.OBJETO_BIT):
                if(fixA.getFilterData().categoryBits == JackGame.ENEMIGO_BIT)
                    ((Enemigo) fixA.getUserData()).invertirVelocidad(true, false);
                else
                    ((Enemigo)fixB.getUserData()).invertirVelocidad(true, false);
                break;

            //Si Jack contacta con su cuerpo en el malo se morirá
            case (JackGame.JACK_BIT | JackGame.ENEMIGO_BIT):
                if(fixA.getFilterData().categoryBits == JackGame.JACK_BIT)
                    ((Jack) fixA.getUserData()).golpedo();
                else
                    ((Jack) fixB.getUserData()).golpedo();
                break;

            //Si un enemigo choca con otro enemigo cambia de dirección
            case JackGame.ENEMIGO_BIT | JackGame.ENEMIGO_BIT:
                ((Enemigo) fixA.getUserData()).invertirVelocidad(true, false);
                ((Enemigo) fixB.getUserData()).invertirVelocidad(true, false);
                break;

            //Cuando el jugador choca con el objeto final del nivel 1 cambiara a la pantalla de nivel superado
            case JackGame.JACK_BIT | JackGame.OBJECT_FINAL:
                if(fixA.getFilterData().categoryBits == JackGame.JACK_BIT)
                    ((Jack) fixA.getUserData()).superado();
                else
                    ((Jack) fixB.getUserData()).superado();
                break;

            //Cuando el jugador choca con el objeto final del juego cambiara a la pantalla de nivel superado
            case JackGame.OBJECT_FINAL_JUEGO | JackGame.JACK_BIT:
                if(fixA.getFilterData().categoryBits == JackGame.JACK_BIT)
                    ((Jack) fixA.getUserData()).finalizado();
                else
                    ((Jack) fixB.getUserData()).finalizado();
                break;

            case (JackGame.JACK_BIT | JackGame.PINCHOS_BIT):
                if(fixA.getFilterData().categoryBits == JackGame.JACK_BIT)
                    ((Jack) fixA.getUserData()).golpedo();
                else
                    ((Jack) fixB.getUserData()).golpedo();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
