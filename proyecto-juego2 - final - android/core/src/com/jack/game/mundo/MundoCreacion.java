package com.jack.game.mundo;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.jack.game.JackGame;
import com.jack.game.enemigos.Enemigo;
import com.jack.game.enemigos.Rojo;
import com.jack.game.enemigos.Verde;
import com.jack.game.pantallas.PantallaJuego;
import com.jack.game.sprites.Ladrillos;
import com.jack.game.enemigos.Marron;
//import com.jack.game.sprites.Monedas;

/**
 * Created by JuanAJ on 28/04/2016.
 */
public class MundoCreacion {
    private Array<Marron> marrones;
    private Array<Verde> verdes;
    private Array<Rojo> rojos;

    public MundoCreacion(PantallaJuego screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Suelo
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX()+rect.getWidth()/2) / JackGame.PPM, (rect.getY()+rect.getHeight()/2)/ JackGame.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth()/2 / JackGame.PPM, rect.getHeight()/2/JackGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Pinchos
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX()+rect.getWidth()/2) / JackGame.PPM, (rect.getY()+rect.getHeight()/2)/ JackGame.PPM);

            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth()/2 / JackGame.PPM, rect.getHeight()/2/JackGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = JackGame.PINCHOS_BIT;
            body.createFixture(fdef);
        }

        //Ladrillos
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            new Ladrillos(screen, rect);
        }

        //Muro para el malo
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX()+rect.getWidth()/2) / JackGame.PPM, (rect.getY()+rect.getHeight()/2)/ JackGame.PPM);

            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth()/2 / JackGame.PPM, rect.getHeight()/2/JackGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = JackGame.OBJETO_BIT;
            body.createFixture(fdef);
        }

        //Enemigo marron
        marrones = new Array<Marron>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            marrones.add(new Marron(screen, rect.getX() / JackGame.PPM, rect.getY() / JackGame.PPM));
        }

        //Enemigo verde
        verdes = new Array<Verde>();
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            verdes.add(new Verde(screen, rect.getX() / JackGame.PPM, rect.getY() / JackGame.PPM));
        }

        //Enemigo rojo
        rojos = new Array<Rojo>();
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            rojos.add(new Rojo(screen, rect.getX() / JackGame.PPM, rect.getY() / JackGame.PPM));
        }

        //Objecto final nivel 0
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / JackGame.PPM, (rect.getY() + rect.getHeight() / 2) / JackGame.PPM);

            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / JackGame.PPM, rect.getHeight() / 2 / JackGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = JackGame.OBJECT_FINAL;
            body.createFixture(fdef);
        }

        //Objecto final juego
        for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / JackGame.PPM, (rect.getY() + rect.getHeight() / 2) / JackGame.PPM);

            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / JackGame.PPM, rect.getHeight() / 2 / JackGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = JackGame.OBJECT_FINAL_JUEGO;
            body.createFixture(fdef);
        }
    }

    public Array<Enemigo> getEnemigos(){
        Array<Enemigo> enemigos = new Array<Enemigo>();
        enemigos.addAll(marrones);
        enemigos.addAll(verdes);
        enemigos.addAll(rojos);
        return enemigos;
    }
}
