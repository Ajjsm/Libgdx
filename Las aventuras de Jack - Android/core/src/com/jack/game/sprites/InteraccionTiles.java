package com.jack.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.jack.game.JackGame;
import com.jack.game.pantallas.PantallaJuego;

/**
 * Created by JuanAJ on 28/04/2016.
 */
public abstract class InteraccionTiles {
    protected World world;
    protected TiledMap map;
    protected Rectangle rectangulo;
    protected Body body;
    protected PantallaJuego screen;
    protected Fixture fixture;

    public InteraccionTiles(PantallaJuego screen, Rectangle rectangulo){
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.rectangulo = rectangulo;

        BodyDef def = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        def.type = BodyDef.BodyType.StaticBody;
        def.position.set((rectangulo.getX()+ rectangulo.getWidth()/2) / JackGame.PPM, (rectangulo.getY()+ rectangulo.getHeight()/2)/ JackGame.PPM);

        body = world.createBody(def);

        shape.setAsBox(rectangulo.getWidth()/2 / JackGame.PPM, rectangulo.getHeight()/2/JackGame.PPM);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);

    }

    public abstract void cabezaGolpeo();
    public void setFiltroCategoria(short filtroBit){
        Filter filter = new Filter();
        filter.categoryBits = filtroBit;
        fixture.setFilterData(filter);

    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return  layer.getCell((int)(body.getPosition().x * JackGame.PPM / 16),
                (int)(body.getPosition().y * JackGame.PPM / 16));
    }

}
