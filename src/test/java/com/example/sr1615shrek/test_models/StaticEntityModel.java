package com.example.sr1615shrek.test_models;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.StaticEntity;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.entity.visitors.Visitor;

public class StaticEntityModel extends StaticEntity {

    public StaticEntityModel(Vector2d position) {
        super(position);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.collisionWithStaticEntity(this);
    }

    @Override
    public void collision(Entity entity) {
        entity.accept(new StaticVisitor());
    }
}
