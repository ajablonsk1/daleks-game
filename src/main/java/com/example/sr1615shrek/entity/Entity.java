package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.PositionObserver;

public interface Entity {
    public void move(Direction direction);

    public void move(Vector2d vector2d);

    public void moveBack();

    public Vector2d getPosition();

    public void setPosition(Vector2d position);

    public void addObserver(PositionObserver positionObserver);

    public void onNext();

    public EntityHierarchy getRank();
}
