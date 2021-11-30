package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.PositionObserver;

import java.util.LinkedList;
import java.util.List;

public abstract class DynamicEntity implements Entity {

    private Vector2d position;

    private Vector2d lastPosition;

    private List<PositionObserver> observers = new LinkedList<>();

    public DynamicEntity(Vector2d position) {
        this.position = position;
    }

    public void move(Vector2d vector2d) {
        lastPosition = position.copy();
        this.position = this.position.add(vector2d);
        observers.forEach(observer -> observer.onPositionChange(this, lastPosition));
    }

    public void move(Direction direction){
        move(direction.getVector());
    }

    public void moveBack() {
        move(position.subtract(lastPosition).opposite());
    }

    //Adding an observer which will inform the board about position change
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

    @Override
    public EntityHierarchy getRank() {
        return EntityHierarchy.DYNAMIC;
    }
}
