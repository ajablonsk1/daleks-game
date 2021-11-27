package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.PositionObserver;

import java.util.LinkedList;
import java.util.List;

public abstract class DynamicEntity implements Entity {

    private Vector2d position;

    private List<PositionObserver> observers = new LinkedList<>();


    @Override
    public void onNext() {
        //TODO
    }

    public void move(Direction direction){
        Vector2d oldPosition = this.position.copy();
        this.position = this.position.add(direction.getVector());
        observers.forEach(observer -> observer.onPositionChange(this, oldPosition));
    }

    public void addObserver(PositionObserver positionObserver){
        this.observers.add(positionObserver);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2d position) {
        this.position = position;
    }
}
