/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxglgames.drop;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxglgames.drop.Components.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
// NOTE: this import above is crucial, it pulls in many useful methods

/**
 * This is an FXGL version of the libGDX simple game tutorial, which can be found
 * here - https://github.com/libgdx/libgdx/wiki/A-simple-game
 *
 * The player can move the bucket left and right to catch water droplets.
 * There are no win/lose conditions.
 *
 * Note: for simplicity's sake all of the code is kept in this file.
 * In addition, most of typical FXGL API is not used to avoid overwhelming
 * FXGL beginners with a lot of new concepts to learn.
 *
 * Although the code is self-explanatory, some may find the comments useful
 * for following the code.
 *
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class DropApp extends GameApplication {

    /**
     * Types of entities in this game.
     */
    public enum Type {
        ENEMY, BUILDING
    }

    private Entity player;

    private PlayerComponent playerComponent;

    @Override
    protected void initSettings(GameSettings settings) {
        // initialize common game / window settings.
        settings.setTitle("Zombs");
        settings.setVersion("1.0");
        settings.setWidth(1000);
        settings.setHeight(1000);
    }





    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new gameFactory());
        player = spawn("Player");

        run(() -> spawn("Drops"), Duration.seconds(1));

        playerComponent = player.getComponent(PlayerComponent.class);
//        player.rota
//        loopBGM("bgm.mp3");
    }
    @Override
    protected void initInput(){
        getInput().addAction(new UserAction("Left"){
            @Override
            protected void onAction(){
                player.getComponent(PlayerComponent.class).left();
            }
            @Override
            protected void onActionEnd(){
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Right"){
            @Override
            protected void onAction(){
                player.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd(){
                player.getComponent(PlayerComponent.class).stop();
            }

        }, KeyCode.D);

        getInput().addAction(new UserAction("Down"){
            @Override
            protected void onAction(){
                player.getComponent(PlayerComponent.class).down();
            }
            @Override
            protected void onActionEnd(){
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Up"){
            @Override
            protected void onAction(){
                player.getComponent(PlayerComponent.class).up();
            }
            @Override
            protected void onActionEnd(){
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.W);
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(Type.BUILDING, Type.ENEMY, (bucket, droplet) -> {

            // code in this block is called when there is a collision between Type.BUCKET and Type.DROPLET
            // remove the collided droplet from the game
            droplet.removeFromWorld();

            // play a sound effect located in /resources/assets/sounds/
            play("drop.wav");

            var hp = bucket.getComponent(HealthIntComponent.class);

            System.out.println(hp.getValue());
            if (hp.getValue() > 1){
                hp.damage(1);
                return;
            }
            bucket.removeFromWorld();

        });
    }

    @Override
    protected void onUpdate(double tpf) {
    }



    private void spawnDroplet() {
        int xval = FXGLMath.random(0, getAppWidth() - 64);
        int yval = 0;
        int targetx = 200;
        int targety = 200;
        System.out.println(xval);
        entityBuilder()
                .type(Type.ENEMY)
                .at(xval, yval)
                .with(new ProjectileComponent(new Point2D((int) (targetx - xval), (int) (targety - yval)), 150))
                .viewWithBBox("droplet.png")
                .collidable()
                .buildAndAttach();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
