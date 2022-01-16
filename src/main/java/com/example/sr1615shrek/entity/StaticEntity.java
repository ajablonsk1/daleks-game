package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.position.Vector2d;

public abstract class StaticEntity implements Entity{

    private Vector2d position;

    public StaticEntity(Vector2d position) {
        this.position = position;
    }

    public StaticEntity(){}

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2d position) {
        this.position = position;
    }

}
