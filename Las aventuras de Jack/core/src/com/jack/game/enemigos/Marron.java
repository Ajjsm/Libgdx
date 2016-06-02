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
 * Created by JuanAJ on 21/05/2016.
 */
public class Marron extends Enemigo {
    private  float stateTime;
    private Animation animacionCaminar;
    private Array<TextureRegion> frames;
    private boolean setDestruido;
    private boolean destruido;
    PantallaJuego pantallaJuego;

    public Marron(PantallaJuego screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("marron"), i * 16, -11, 16, 16));
        animacionCaminar = new com.badlogic.gdx.graphics.g2d.Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / JackGame.PPM, 16 / JackGame.PPM);
        setDestruido = false;
        destruido = false;
    }

    public void update(float dt){
        stateTime+= dt;
        if(setDestruido && !destruido){
               world.destroyBody(b2body);
            destruido = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("marron"), 32, -11, 16, 16));
            stateTime = 0;
        }
        else if(!destruido) {
            b2body.setLinearVelocity(velocidad);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(animacionCaminar.getKeyFrame(stateTime, true));
        }
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

    public void  draw(Batch batch){
        if(!destruido || stateTime <1)
            super.draw(batch);
    }

    @Override
    public void golpearCabeza(Jack jack) {
        if (pantallaJuego.isSonido == true) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/juego/marron_muerto.wav"));
            sound.play();
        }
        setDestruido = true;
    }

}
