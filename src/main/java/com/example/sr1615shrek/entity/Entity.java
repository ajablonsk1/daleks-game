package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.PositionObserver;

public interface Entity {

    void move(Direction direction);

    void moveBack();

    Vector2d getPosition();

    void setPosition(Vector2d position);

    void addObserver(PositionObserver positionObserver);

    EntityHierarchy getRank();
}
