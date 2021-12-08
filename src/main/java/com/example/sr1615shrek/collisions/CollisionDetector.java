package com.example.sr1615shrek.collisions;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollisionDetector {

    private final Board board;

    @Autowired
    public CollisionDetector(Board board){
        this.board = board;
        this.board.getCollisionSubject().subscribe(this::detectCollisions);
    }

    // Detecting collision at the same vector
    public void detectCollisions(List<Entity> entitiesOnOnePosition){
        if(entitiesOnOnePosition.size() == 1) {
            Entity entity = entitiesOnOnePosition.get(0);

            if(detectEntityOffTheMap(entity.getPosition())) {
                // Solve if entity at the edge of board
            }
        } else {
            solveDetectedCollision(entitiesOnOnePosition.get(0),
                    entitiesOnOnePosition.get(1));
        }
    }

    // Calling the collision function in entity to solve the collision
    private void solveDetectedCollision(Entity firstEntity, Entity secondEntity){
        firstEntity.collision(secondEntity);
        secondEntity.collision(firstEntity);
    }

    // Checking if entities didn't go off the map
    private boolean detectEntityOffTheMap(Vector2d entityPosition){
        return entityPosition.getX() < 0
                || entityPosition.getY() < 0
                || entityPosition.getX() > this.board.getWidth() - 1
                || entityPosition.getY() > this.board.getHeight() - 1;
    }
}
