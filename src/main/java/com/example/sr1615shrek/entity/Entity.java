package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.position.Vector2d;

public interface Entity {

    Vector2d getPosition();

    void setPosition(Vector2d position);

    EntityHierarchy getRank();
}
