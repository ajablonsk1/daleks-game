package com.example.sr1615shrek.collisions;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CollisionDetector {

    private CollisionSolver collisionSolver;

    private Board board;

    public CollisionDetector(Board board){
        this.collisionSolver = new CollisionSolver();
        this.board = board;
    }

    public void detectCollisions(){

    }

    // TODO
//    public void setCollisions(Observable<Entity> entities) {
//        entities.forEach(entity -> collisions.put(entity.getPosition(), entity));
//    }
}
