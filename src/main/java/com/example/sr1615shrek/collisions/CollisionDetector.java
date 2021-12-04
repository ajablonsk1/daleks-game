package com.example.sr1615shrek.collisions;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.EntityHierarchy;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class CollisionDetector {

    private final CollisionSolver collisionSolver;

    private final Board board;

    @Autowired
    public CollisionDetector(Board board){
        this.collisionSolver = new CollisionSolver(board);
        this.board = board;
        this.board.getSubject().subscribe(this::detectCollisions);
    }

    // Detecting collision at the same vector
    public void detectCollisions(List<Entity> entitiesOnOnePosition){
        if(entitiesOnOnePosition.size() == 1) {
            Entity entity = entitiesOnOnePosition.get(0);

            if(detectEntityOffTheMap(entity.getPosition())) {
                this.collisionSolver.endOfMap(entity);
            }
        } else {
            detectTwoEntitiesCollisionProblem(entitiesOnOnePosition.get(0),
                    entitiesOnOnePosition.get(1));
        }
    }

    /* Checking the entities (with collision) hierarchy
       and call the valid function in collisionSolver */
    private void detectTwoEntitiesCollisionProblem(Entity firstEntity, Entity secondEntity){
        if(secondEntity.getRank().isMoreImportantThan(firstEntity.getRank())) {
            Entity tmpEntity = firstEntity;
            firstEntity = secondEntity;
            secondEntity = tmpEntity;
        }

        if(firstEntity.getRank() == EntityHierarchy.STATIC_PASSIVE
                && secondEntity.getRank() == EntityHierarchy.DYNAMIC) {
            this.collisionSolver.staticPassiveEntityWithDynamicEntity(firstEntity, secondEntity);
        } else if (firstEntity.getRank() == EntityHierarchy.DYNAMIC
                && secondEntity.getRank() == EntityHierarchy.STATIC_ACTIVE){
            this.collisionSolver.staticActiveEntityWithDynamicEntity(firstEntity, secondEntity);
        } else if(firstEntity.getRank() == EntityHierarchy.DYNAMIC
                && secondEntity.getRank() == EntityHierarchy.DYNAMIC){
            this.collisionSolver.dynamicEntityWithDynamicEntity(firstEntity, secondEntity);
        }
    }

    // Checking if entities didn't go off the map
    private boolean detectEntityOffTheMap(Vector2d entityPosition){
        return entityPosition.getX() < 0
                || entityPosition.getY() < 0
                || entityPosition.getX() > this.board.getWidth() - 1
                || entityPosition.getY() > this.board.getHeight() - 1;
    }
}
