package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.position.Vector2d;

public abstract class StaticEntity implements Entity{

    private Vector2d position;

    public Vector2d getPosition() {
        return position;
    }

    public void move(){
        //DO NOTHING
    }
}
