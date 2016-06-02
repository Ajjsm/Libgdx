package com.jack.game.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.jack.game.JackGame;
import com.jack.game.pantallas.PantallaJuego;



/**
 * Created by JuanAJ on 28/04/2016.
 */
public class Jack extends Sprite {
    public Jack() {

    }

    public enum  State { CALLENDO, SALTANDO, PARADO, CORRIENDO, MUERTO, SUPERADONIVEL, FINALJUEGO}
    public State estadoActual;
    public State estadoAnterior;

    public World world;
    public static Body b2body;
    private TextureRegion jackStand;
    private TextureRegion jackMuerto;
    private Animation jackCorre;
    private Animation jackSalta;

    private float stateTimer;
    private boolean correrDerecha;

    private boolean jackEstaMuerto;
    private boolean jackSuperaNivel;
    private boolean jackSuperaJuego;

    PantallaJuego pantallaJuego;


    public Jack(PantallaJuego screen){
        super(screen.getAtlas().findRegion("jack"));
        this.world = screen.getWorld();
        estadoActual = State.PARADO;
        estadoAnterior = State.PARADO;
        stateTimer = 0;
        correrDerecha = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i<4; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        jackCorre = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 4; i< 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        jackSalta = new Animation(0.1f, frames);

        defineJack();
        jackStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
        jackMuerto = new TextureRegion(screen.getAtlas().findRegion("jack"), 96, -11, 16, 16);

        setBounds(0,0,16/JackGame.PPM, 16/JackGame.PPM);
        setRegion(jackStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x-getWidth()/2, b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
    }

    public void jump(){
        if ( estadoActual != State.SALTANDO ) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            estadoActual = State.SALTANDO;
        }
    }

    public TextureRegion getFrame(float dt){
        estadoActual = getState();
        TextureRegion region;
        switch (estadoActual){
            case MUERTO:
                region = jackMuerto;
                break;
            case SALTANDO:
                region = jackSalta.getKeyFrame(stateTimer);
                break;
            case CORRIENDO:
                region = jackCorre.getKeyFrame(stateTimer, true);
                break;
            case CALLENDO:
            case PARADO:
            default:
                region = jackStand;
                break;

        }

        if ((b2body.getLinearVelocity().x<0 || !correrDerecha) && !region.isFlipX()){
            region.flip(true, false);
            correrDerecha = false;
        }

        else if ((b2body.getLinearVelocity().x>0||correrDerecha) && region.isFlipX()){
            region.flip(true,false);
            correrDerecha = true;
        }

        stateTimer = estadoActual == estadoAnterior ? stateTimer + dt : 0;
        estadoAnterior = estadoActual;
        return region;
    }

    public State getState(){
        if(jackSuperaJuego)
            return State.FINALJUEGO;
        if(jackSuperaNivel)
            return State.SUPERADONIVEL;
        if(jackEstaMuerto)
            return State.MUERTO;
        else if(b2body.getLinearVelocity().y>0 || (b2body.getLinearVelocity().y<0 && estadoAnterior == State.SALTANDO))
            return State.SALTANDO;

        else if(b2body.getLinearVelocity().y<0)
            return State.CALLENDO;

        else if (b2body.getLinearVelocity().x!=0)
            return State.CORRIENDO;
        else
            return State.PARADO;
    }

    public void defineJack(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / JackGame.PPM, 32 / JackGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / JackGame.PPM);
        fdef.filter.categoryBits = JackGame.JACK_BIT;

        //Le digo que tipo de objetos va a poder colisionar o actuar con el jugador
        fdef.filter.maskBits = JackGame.SUELO_BIT |
                JackGame.LADRILLO_BIT |
                JackGame.ENEMIGO_BIT |
                JackGame.OBJECT_FINAL_JUEGO |
                JackGame.OBJECT_FINAL |
                JackGame.CABEZA_ENEMIGO_BIT |
                JackGame.PINCHOS_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Le creo la cabeza al jugador
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / JackGame.PPM, 6 / JackGame.PPM), new Vector2(2 / JackGame.PPM, 6 / JackGame.PPM));
        fdef.filter.categoryBits = JackGame.JACK_CABEZA_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

    }

    public void golpedo(){
        pantallaJuego = new PantallaJuego();
        if (pantallaJuego.isSonido==true){
            JackGame.manager.get("sonidos/juego/game_over.wav", Sound.class).play();
        }
        pantallaJuego.musica.stop();
        jackEstaMuerto = true;
        Filter filter = new Filter();
        //Le cambio la mascara a Jack a una que no tiene colision con nada
        filter.maskBits = JackGame.SINCOLISION_BIT;
        for(Fixture fixture: b2body.getFixtureList())
            fixture.setFilterData(filter);

        b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
    }

    public void superado(){
        pantallaJuego = new PantallaJuego();
        if (pantallaJuego.isSonido==true) {
            JackGame.manager.get("sonidos/juego/nivel_superado.mp3", Sound.class).play();
        }
        jackSuperaNivel = true;
        Filter filter = new Filter();
        //Le cambio la mascara a Jack a una que no tiene colision con nada
        filter.maskBits = JackGame.SUELO_BIT;
        for(Fixture fixture: b2body.getFixtureList())
            fixture.setFilterData(filter);
        b2body.applyLinearImpulse(new Vector2(8f,0), b2body.getWorldCenter(), true);
    }

    public void finalizado(){
        pantallaJuego = new PantallaJuego();
        if (pantallaJuego.isSonido==true) {
            JackGame.manager.get("sonidos/juego/nivel_superado.mp3", Sound.class).play();
        }
        jackSuperaJuego = true;
        System.out.println("Juego finalizado");
        Filter filter = new Filter();
        filter.maskBits = JackGame.SUELO_BIT;
        for(Fixture fixture: b2body.getFixtureList())
            fixture.setFilterData(filter);
        b2body.applyLinearImpulse(new Vector2(8f,0), b2body.getWorldCenter(), true);
    }

    public float getStateTimer(){
        return stateTimer;
    }

}
