package com.example.sr1615shrek.game;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board implements PositionObserver {

    private Observable<Entity> entities;

    private final Map<Vector2d, List<Entity>> collisions = new HashMap<>();

    private final int height;

    private final int width;

    private final CollisionDetector collisionDetector;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.collisionDetector = new CollisionDetector(this);
    }

    public Observable<Entity> getEntities(){
        return this.entities;
    }

    @Override
    public void onPositionChange(Entity entity, Vector2d oldPosition) {
        collisions.get(oldPosition).remove(entity);
        addEntity(entity);
        collisionDetector.detectCollisions(collisions.get(entity.getPosition()));
    }

    public void addEntity(Entity entity) {
        if(collisions.get(entity.getPosition()) == null) {
            List<Entity> entities = new LinkedList<>();
            collisions.put(entity.getPosition(), entities);
        }
        collisions.get(entity.getPosition()).add(entity);
    }

    public void removeEntityFromBoard(Entity entity) {
        collisions.get(entity.getPosition()).remove(entity);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

