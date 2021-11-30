package com.example.sr1615shrek.game;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;

import java.util.*;

public class Board implements PositionObserver {

    private final Map<Vector2d, List<Entity>> entities = new HashMap<>();

    private final int height;

    private final int width;

    private final CollisionDetector collisionDetector;

    public Board(int width, int height) {
        this.height = height;
        this.width = width;
        this.collisionDetector = new CollisionDetector(this);
    }

    public List<Entity> getEntities(){
        return this.entities.values().stream().flatMap(Collection::stream).toList();
    }

    // Adding entity to map
    public void addEntity(Entity entity) {
        if(entities.get(entity.getPosition()) == null) {
            List<Entity> entities = new LinkedList<>();
            this.entities.put(entity.getPosition(), entities);
        }
        entities.get(entity.getPosition()).add(entity);
    }

    // Removing entity from map
    public void removeEntityFromBoard(Entity entity) {
        entities.get(entity.getPosition()).remove(entity);
    }


    // Changing the entity position in hashMap and checking if there is a collision
    @Override
    public void onPositionChange(Entity entity, Vector2d oldPosition) {
        entities.get(oldPosition).remove(entity);
        addEntity(entity);
        this.collisionDetector.detectCollisions(entities.get(entity.getPosition()));
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

