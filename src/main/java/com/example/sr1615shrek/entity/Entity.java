package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.position.Vector2d;

public interface Entity {

    void accept(Visitor visitor);

    void collision(Entity entity);

    Vector2d getPosition();

    void setPosition(Vector2d position);
}
