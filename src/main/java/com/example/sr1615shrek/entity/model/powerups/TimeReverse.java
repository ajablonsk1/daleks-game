package com.example.sr1615shrek.entity.model.powerups;

import com.example.sr1615shrek.collisions.visitors.TimeReverseVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.StaticEntity;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class TimeReverse extends StaticEntity implements PowerUp{

    private TimeReverseVisitor timeReverseVisitor;

    private BehaviorSubject<TimeReverse> deadTimeReverseSubject;

    public TimeReverse(Vector2d position,
                       TimeReverseVisitor timeReverseVisitor,
                       BehaviorSubject<TimeReverse> deadTimeReverseSubject) {
        super(position);
        this.timeReverseVisitor = timeReverseVisitor;
        this.deadTimeReverseSubject = deadTimeReverseSubject;
    }

    @Override
    public void execute(DynamicEntity dynamicEntity){
        if(!dynamicEntity.getLastPositions().isEmpty()){
            Vector2d lastPosition = dynamicEntity.getLastPositions().get(dynamicEntity.getLastPositions().size()-1);
            dynamicEntity.getLastPositions().remove(dynamicEntity.getLastPositions().size()-1);
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
        entity.accept(this.timeReverseVisitor);
    }
}
