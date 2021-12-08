package com.example.sr1615shrek.test_models;

import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.StaticEntity;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.collisions.visitors.Visitor;

public class DynamicVisitor implements Visitor {

    @Override
    public void collisionWithDynamicEntity(DynamicEntity dynamicEntity) {
        dynamicEntity.moveBack();
    }

    @Override
    public void collisionWithStaticEntity(StaticEntity staticEntity) {
        staticEntity.setPosition(new Vector2d(0,0));
    }
}
