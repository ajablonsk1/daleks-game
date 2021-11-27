package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;

public interface Entity {

    public void move(Direction direction);

    public Vector2d getPosition();

    public void setPosition(Vector2d position);

    public void onNext();
}
