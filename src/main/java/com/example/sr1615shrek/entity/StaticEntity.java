package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.PositionObserver;

public abstract class StaticEntity implements Entity{
    private final Vector2d position;

    public StaticEntity(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public void move(Direction direction) {
        //DOES NOTHING
    }

    @Override
    public void moveBack(){
        //DOES NOTHING
    }

    @Override
    public void addObserver(PositionObserver positionObserver){
        //DOES NOTHING
    }
}
