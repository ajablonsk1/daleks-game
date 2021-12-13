package com.example.sr1615shrek.entity.model;

import com.example.sr1615shrek.collisions.visitors.DalekVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class Dalek extends DynamicEntity {

    private final BehaviorSubject<Dalek> deadDaleksSubject;

    private final DalekVisitor dalekVisitor;

    public Dalek(Vector2d position,
                 BehaviorSubject<DynamicEntity> positionSubject,
                 BehaviorSubject<Dalek> deadDaleksSubject,
                 DalekVisitor dalekVisitor) {
        super(position, positionSubject);
        this.deadDaleksSubject = deadDaleksSubject;
        this.dalekVisitor = dalekVisitor;
    }

    public void passingAway(){
        deadDaleksSubject.onNext(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.collisionWithDalek(this);
    }

    @Override
    public void collision(Entity entity) {
        entity.accept(this.dalekVisitor);
    }
}
