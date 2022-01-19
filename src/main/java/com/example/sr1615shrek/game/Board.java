package com.example.sr1615shrek.game;

import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.SubjectService;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.*;

public class Board {

    private final Map<Vector2d, List<Entity>> entities = new HashMap<>();

    private Doctor doctor;

    private final int height;

    private final int width;

    private final SubjectService subjectService;

    public Board(int width,
                 int height,
                 SubjectService subjectService) {
        this.height = height;
        this.width = width;
        this.subjectService = subjectService;

        subjectService.getEntityMoveSubject().subscribe(this::onEntityPositionChange);

    }

    public List<Entity> getEntities(){
        return this.entities
                .values()
                .stream()
                .flatMap(Collection::stream)
                .toList();
    }

    public List<Entity> getEntitiesOnVector(Vector2d vector2d){
        return this.entities.get(vector2d);
    }

    public void addEntity(Entity entity) {
        this.entities.computeIfAbsent(entity.getPosition(), k -> new LinkedList<>());

        this.entities.get(entity.getPosition()).add(entity);

        subjectService.getCollisionSubject().onNext(entities.get(entity.getPosition()));
    }

    public BehaviorSubject<List<Entity>> getCollisionSubject() {
        return subjectService.getCollisionSubject();
    }

    private void onEntityPositionChange(DynamicEntity dynamicEntity){
        this.entities.get(dynamicEntity.getLastPosition()).remove(dynamicEntity);
        addEntity(dynamicEntity);
    }

    public void removeEntityFromBoard(Entity entity) {
        entities.get(entity.getPosition()).remove(entity);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }

    public Doctor getDoctor(){
        return this.doctor;
    }
}

