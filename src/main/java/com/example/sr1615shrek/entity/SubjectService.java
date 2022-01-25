package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.powerups.Teleport;
import com.example.sr1615shrek.entity.model.powerups.TimeReverse;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.List;

public class SubjectService {

    private final BehaviorSubject<List<Entity>> collisionSubject;

    private final BehaviorSubject<DynamicEntity> entityMoveSubject;

    private final BehaviorSubject<Dalek> deadDaleksSubject;

    private final BehaviorSubject<Teleport> deadTeleportSubject;

    private final BehaviorSubject<TimeReverse> deadTimeReverseSubject;

    public SubjectService(BehaviorSubject<List<Entity>> collisionSubject,
                          BehaviorSubject<DynamicEntity> entityMoveSubject,
                          BehaviorSubject<Dalek> deadDaleksSubject,
                          BehaviorSubject<Teleport> deadTeleportSubject,
                          BehaviorSubject<TimeReverse> deadTimeReverseSubject) {
        this.collisionSubject = collisionSubject;
        this.entityMoveSubject = entityMoveSubject;
        this.deadDaleksSubject = deadDaleksSubject;
        this.deadTeleportSubject = deadTeleportSubject;
        this.deadTimeReverseSubject = deadTimeReverseSubject;
    }

    public BehaviorSubject<List<Entity>> getCollisionSubject() {
        return collisionSubject;
    }

    public BehaviorSubject<DynamicEntity> getEntityMoveSubject() {
        return entityMoveSubject;
    }

    public BehaviorSubject<Dalek> getDeadDaleksSubject() {
        return deadDaleksSubject;
    }

    public BehaviorSubject<Teleport> getDeadTeleportSubject() {
        return deadTeleportSubject;
    }

    public BehaviorSubject<TimeReverse> getDeadTimeReverseSubject() {
        return deadTimeReverseSubject;
    }
}
