package com.jack.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jack.game.Controlador;
import com.jack.game.JackGame;
/*import com.jack.game.item.ItemDef;
import com.jack.game.item.Items;*/
import com.jack.game.interfaz.Hud;
import com.jack.game.enemigos.Enemigo;
import com.jack.game.sprites.Jack;
import com.jack.game.mundo.MundoCreacion;
import com.jack.game.mundo.MundoListeners;


/**
 * Created by JuanAJ on 28/04/2016.
 */
public class PantallaJuego implements Screen {
    private JackGame game;
    private TextureAtlas atlas;
    private Hud hud;
    private Jack player;
    private OrthographicCamera gamecam;
    private Viewport gameport;
    private TmxMapLoader cargaMapa;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Controlador controlador;

    //Variables Box2d
    private World world;
    private Box2DDebugRenderer b2dr;
    private MundoCreacion creator;

    public static int nivel;
    public static boolean isSonido;
    private static boolean isCargado;
    public static Music musica;

    public PantallaJuego(JackGame game){
        controlador = new Controlador(game.batch);

        if (isCargado = true){
            //carga nivel guardado de fichero preferencias
        }

        isSonido = true;
        this.game = game;
        atlas = new TextureAtlas("personajes.pack");
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(JackGame.V_ANCHO / JackGame.PPM, JackGame.V_ALTURA / JackGame.PPM, gamecam);
        hud = new Hud(game.batch);
        cargaMapa = new TmxMapLoader();
        map = cargaMapa.load("mapas/level"+nivel+".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / JackGame.PPM);
        gamecam.position.set(gameport.getWorldWidth()/2, gameport.getWorldHeight()/2, 0);
        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new MundoCreacion(this);
        player = new Jack(this);

        world.setContactListener(new MundoListeners());

        if (nivel == 0) {
            musica = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/musica_fondo.ogg"));
            if (isSonido==true){
                musica.setLooping(true);
                musica.play();
                musica.setVolume(1);
            } else {
                musica.setVolume(0);
            }
        }
        if (nivel == 1) {
            musica = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/musica_nivel1.wav"));
            if (isSonido==true){
                musica.setLooping(true);
                musica.play();
                musica.setVolume(1);
            } else {
                musica.setVolume(0);
            }
        }
        if (nivel == 2) {
            musica = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/musica_nivel2.mp3"));
            if (isSonido==true){
                musica.setLooping(true);
                musica.play();
                musica.setVolume(1);
            } else {
                musica.setVolume(0);
            }
        }
        if (nivel == 3) {
            musica = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/musica_nivel3.mp3"));
            if (isSonido==true){
                musica.setLooping(true);
                musica.play();
                musica.setVolume(1);
            } else {
                musica.setVolume(0);
            }
        }



    }

    public PantallaJuego() {

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {
        game.paused = false;
    }

    public void handleInput(float dt){
        if (player.estadoActual != Jack.State.MUERTO) {
            if (controlador.isUpPressed())
                //Cambiar para dar un salto solo
                player.jump();
            //player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            if (controlador.isRightPressed() && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (controlador.isLeftPressed() && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        else
            musica.setVolume(1);
    }

    public void update(float dt){
        handleInput(dt);
        world.step(1/60f, 6, 2);
        player.update(dt);
        for(Enemigo enemigo : creator.getEnemigos()) {
            enemigo.update(dt);

        }

        hud.update(dt);

        if (player.estadoActual == Jack.State.SUPERADONIVEL){
            musica.stop();
        }
        if (player.estadoActual == Jack.State.FINALJUEGO){
            musica.stop();
        }
        if (player.estadoActual != Jack.State.MUERTO){

            gamecam.position.x = player.b2body.getPosition().x;
        }

        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam);

        handleKeyboard();
        activaSonido();
        desactivaSonido();

        if(isSonido == false) {
            musica.setVolume(0);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //Me permite ver las colisiones para trabajar mejor
        //b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for(Enemigo enemigo : creator.getEnemigos())
            enemigo.draw(game.batch);

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()){
            game.setScreen(new GameOver(game));
            musica.stop();
            dispose();
        }

        if (nivel2()){
            nivel++;
            Hud.setNivelActual(nivel);
            game.setScreen(new NivelSuperado(game));
            musica.stop();
            dispose();
        }

        if (finalJuego()){
            nivel=0;
            Hud.setNivelActual(nivel);
            game.setScreen(new JuegoFinalizado(game));
            musica.stop();
            dispose();
        }

        //Si el tiempo llega a 0 saltara la pantalla de GameOver
        if (hud.getContadorMundo()<=0){
            musica.stop();
            game.setScreen(new GameOver(game));

        }

        controlador.draw();

    }

    //Una vez el jugador tiene el estado de muerto, cuando pasan tres segundos salta la pantalla de GameOver
    public boolean gameOver(){
        if(player.estadoActual == Jack.State.MUERTO && player.getStateTimer()>3){
            return true;
        }
        return false;
    }

    public boolean nivel2(){
        if(player.estadoActual == Jack.State.SUPERADONIVEL && player.getStateTimer()>3){
            return true;
        }
        return false;
    }

    public boolean finalJuego(){
        if(player.estadoActual == Jack.State.FINALJUEGO && player.getStateTimer()>3){
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
        controlador.resize(width, height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {
        game.paused = true;
    }

    @Override
    public void resume() {
        game.paused = false;
    }

    @Override
    public void hide() {
        game.paused = true;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    private void handleKeyboard(){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MenuPausa(game, this));
            musica.setVolume(0);
        }
    }

    private void activaSonido(){
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            isSonido=true;
            musica.setVolume(1);
            System.out.println(isSonido);
        }
    }

    private void desactivaSonido(){
        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            isSonido=false;
            musica.setVolume(0);
            System.out.println(isSonido);
        }
    }

}
