package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public abstract class DynamicEntity implements Entity {

    private Vector2d position;

    private Vector2d lastPosition;

    private BehaviorSubject<DynamicEntity> positionSubject;

    public DynamicEntity(Vector2d position, BehaviorSubject<DynamicEntity> positionSubject) {
        this.position = position;
        this.lastPosition = position;
        this.positionSubject = positionSubject;
    }

    public void move(Vector2d vector2d) {
        lastPosition = position.copy();
        this.position = this.position.add(vector2d);
        positionSubject.onNext(this);
    }

    public void move(Direction direction){
        move(direction.getVector());
    }

    public void moveBack() {
        move(position.subtract(lastPosition).opposite());
    }

    public Vector2d getLastPosition(){
        return this.lastPosition;
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
