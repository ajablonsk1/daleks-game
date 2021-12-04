package com.example.sr1615shrek.game;

import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.*;

public class Board {

    private final Map<Vector2d, List<Entity>> entities = new HashMap<>();

    private final int height;

    private final int width;

    private final BehaviorSubject<List<Entity>> subject = BehaviorSubject.create();

    private final BehaviorSubject<DynamicEntity> entitySubject = BehaviorSubject.create();

    public Board(int width, int height) {
        this.height = height;
        this.width = width;
        this.entitySubject.subscribe(this::onEntityPositionChange);
    }

    public List<Entity> getEntities(){
        return this.entities
                .values()
                .stream()
                .flatMap(Collection::stream)
                .toList();
    }

    // Adding entity to map
    public void addEntity(Entity entity) {
        this.entities.computeIfAbsent(entity.getPosition(), k -> new LinkedList<>());

        this.entities.get(entity.getPosition()).add(entity);

        this.subject.onNext(entities.get(entity.getPosition()));
    }

    public BehaviorSubject<List<Entity>> getSubject() {
        return this.subject;
    }

    private void onEntityPositionChange(DynamicEntity dynamicEntity){
        this.entities.get(dynamicEntity.getLastPosition()).remove(dynamicEntity);
        addEntity(dynamicEntity);
    }

    // Removing entity from map
    public void removeEntityFromBoard(Entity entity) {
        entities.get(entity.getPosition()).remove(entity);
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

