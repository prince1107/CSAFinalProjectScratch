package com.almasb.fxglgames.drop.Components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class PlayerComponent extends Component {
    private double speed = 10;

    public Entity getPlayer(){
        return entity;
    }

    public void up(){
        entity.translateY(-speed);
    }
    public void down(){
        entity.translateY(speed);
    }
    public void left(){
        entity.translateX(-speed);
    }
    public void right(){
        entity.translateX(speed);
    }
    public void stop(){

    }
}
