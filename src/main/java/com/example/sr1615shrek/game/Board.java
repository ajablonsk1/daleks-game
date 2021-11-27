package com.example.sr1615shrek.game;

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

    private Map<Vector2d, List<Entity>> collisions = new HashMap<>();

    private final int height;

    private final int width;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public Observable<Entity> getEntities(){
        return this.entities;
    }

    @Override
    public void onPositionChange(Entity entity, Vector2d oldPosition) {
        collisions.get(oldPosition).remove(entity);
        if(collisions.get(entity.getPosition()) == null) {
            List<Entity> entities = new LinkedList<>();
            collisions.put(entity.getPosition(), entities);
        }
        collisions.get(entity.getPosition()).add(entity);
    }

    public void addEntities(Observable<Entity> entities){
        this.entities.mergeWith(entities);
    }

    public void setMap(Map<Vector2d, List<Entity>> collisions){
        this.collisions = collisions;
    }

    public Map<Vector2d, List<Entity>> getCollisions(){
        return this.collisions;
    }

    public void printHashMap(){
        collisions.forEach((e, v) -> System.out.println(e + " " + v));
    }
}

