package com.example.sr1615shrek.entity.model.powerups;

import com.example.sr1615shrek.collisions.visitors.PowerUpVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.StaticEntity;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.List;

public class TimeReverse extends StaticEntity implements PowerUp{

    private final PowerUpVisitor powerUpVisitor;

    private final BehaviorSubject<TimeReverse> deadTimeReverseSubject;

    public TimeReverse(Vector2d position,
                       PowerUpVisitor powerUpVisitor,
                       BehaviorSubject<TimeReverse> deadTimeReverseSubject) {
        super(position);
        this.powerUpVisitor = powerUpVisitor;
        this.deadTimeReverseSubject = deadTimeReverseSubject;
    }

    @Override
    public void execute(DynamicEntity dynamicEntity){
        List<Vector2d> lastPositions = dynamicEntity.getLastPositions();
        if(!lastPositions.isEmpty()){
            Vector2d lastPosition = lastPositions.get(lastPositions.size()-1);
            dynamicEntity.getLastPositions().remove(lastPositions.size()-1);
            dynamicEntity.moveTimeReverse(lastPosition);
        }
    }

    @Override
    public void undo(){
        deadTimeReverseSubject.onNext(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.collisionWithTimeReverse(this);
    }

    @Override
    public void collision(Entity entity) {
        entity.accept(this.powerUpVisitor);
    }
}
