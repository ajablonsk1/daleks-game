package com.example.sr1615shrek.test_models;

import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class DynamicEntityModel extends DynamicEntity {

    public DynamicEntityModel(Vector2d position, BehaviorSubject<DynamicEntity> positionSubject) {
        super(position, positionSubject);
    }

    @Override
    public void accept(Visitor visitor) {
//        visitor.collisionWithDynamicEntity(this);
    }

    @Override
    public void collision(Entity entity) {
//        entity.accept(new DynamicVisitor());
    }
}
