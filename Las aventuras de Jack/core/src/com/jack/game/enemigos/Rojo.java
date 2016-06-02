package com.jack.game.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.jack.game.JackGame;
import com.jack.game.pantallas.PantallaJuego;
import com.jack.game.sprites.Jack;

/**
 * Created by JuanAJ on 27/05/2016.
 */
public class Rojo extends Enemigo{
    public enum State {ANDANDO}
    public State estadoActual;
    public State estadoAnterior;
    private float stateTime;
    private Animation animacionAndando;
    private Array<TextureRegion> frames;
    private boolean setDestruido;
    private boolean destruido;
    private Jack jack;
    PantallaJuego pantallaJuego;

    public Rojo(PantallaJuego screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("verde"), 32, -11, 16, 24));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("verde"), 32, -11, 16, 24));
        animacionAndando = new Animation(0.2f, frames);
        estadoActual = estadoAnterior = State.ANDANDO;

        stateTime = 0;
        setBounds(getX(), getY(), 16 / JackGame.PPM, 16 / JackGame.PPM);
        setDestruido = false;
        destruido = false;

    }

    @Override
    protected void definirEnemigo() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / JackGame.PPM);
        fdef.filter.categoryBits = JackGame.ENEMIGO_BIT;
        fdef.filter.maskBits = JackGame.SUELO_BIT |
                JackGame.LADRILLO_BIT |
                JackGame.ENEMIGO_BIT |
                JackGame.OBJETO_BIT |
                JackGame.JACK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Creo la cabeza del enemigo
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / JackGame.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / JackGame.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / JackGame.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / JackGame.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = JackGame.CABEZA_ENEMIGO_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;

        switch (estadoActual){
            case ANDANDO:
            default:
                region = animacionAndando.getKeyFrame(stateTime, true);
                break;
        }

        if(velocidad.x > 0 && region.isFlipX() == false){
            region.flip(true, false);
        }
        if(velocidad.x < 0 && region.isFlipX() == true){
            region.flip(true, false);
        }
        stateTime = estadoActual == estadoAnterior ? stateTime + dt : 0;

        //Actualiza el estado anterior
        estadoAnterior = estadoActual;
        return region;
    }

    @Override
    public void update(float dt) {
        if(setDestruido && !destruido){
            world.destroyBody(b2body);
            destruido = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("verde"), 32, -11, 16, 24));
            stateTime = 0;

        }
        else if(!destruido) {
            if (b2body.getPosition().x < jack.b2body.getPosition().x + 70 / JackGame.PPM) {
                b2body.setActive(true);
            } else {
                b2body.setActive(false);
            }


            setRegion(getFrame(dt));
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - 8 / JackGame.PPM);
            b2body.setLinearVelocity(velocidad);
            stateTime += dt;

            if (b2body.getPosition().x > jack.b2body.getPosition().x - 50 / JackGame.PPM) {
                if (b2body.getPosition().x > jack.b2body.getPosition().x) {
                    velocidad.x = -0.5f;

                } else {
                    velocidad.x = +0.5f;

                }
            }
        }

    }

    @Override
    public void golpearCabeza(Jack jack) {
        pantallaJuego = new PantallaJuego();
        if (pantallaJuego.isSonido == true) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/juego/game_over.wav"));
            sound.play();
        }
        jack.golpedo();

        }

    public void  draw(Batch batch){
        if(!destruido || stateTime <1)
            super.draw(batch);
    }

}
