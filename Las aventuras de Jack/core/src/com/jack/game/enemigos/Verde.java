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
 * Created by JuanAJ on 24/05/2016.
 */
public class Verde extends Enemigo {
    public enum State {ANDANDO}
    public State currentState;
    public State previousState;
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private Jack jack;
    PantallaJuego pantallaJuego;

    public Verde(PantallaJuego screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("verde"), 0, -11, 16, 24));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("verde"), 0, -11, 16, 24));
        walkAnimation = new Animation(0.2f, frames);
        currentState = previousState = State.ANDANDO;

        stateTime = 0;
        setBounds(getX(), getY(), 16 / JackGame.PPM, 16 / JackGame.PPM);
        setToDestroy = false;
        destroyed = false;



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
               // JackGame.COIN_BIT |
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

        switch (currentState){
            case ANDANDO:
            default:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;
        }

        if(velocidad.x > 0 && region.isFlipX() == false){
            region.flip(true, false);
        }
        if(velocidad.x < 0 && region.isFlipX() == true){
            region.flip(true, false);
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }

    @Override
    public void update(float dt) {
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("verde"), 16, -11, 16, 24));
            stateTime = 0;

        }
        else if(!destroyed) {
            setRegion(getFrame(dt));
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - 8 / JackGame.PPM);
            b2body.setLinearVelocity(velocidad);
            stateTime+= dt;

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
        if(!destroyed || stateTime <1)
            super.draw(batch);
    }
}
