package com.example.sr1615shrek.entity.model;

import com.example.sr1615shrek.collisions.visitors.JunkVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.StaticEntity;
import com.example.sr1615shrek.entity.position.Vector2d;

public class Junk extends StaticEntity {

    private final JunkVisitor junkVisitor;

    private final String graphics = "J";

    public Junk(Vector2d position,
                JunkVisitor junkVisitor) {
        super(position);
        this.junkVisitor = junkVisitor;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.collisionWithJunk(this);
    }

    @Override
    public void collision(Entity entity) {
        entity.accept(junkVisitor);
    }

    @Override
    public String getGraphics() {
        return this.graphics;
    }
}
