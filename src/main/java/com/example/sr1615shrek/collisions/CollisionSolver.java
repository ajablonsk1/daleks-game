package com.example.sr1615shrek.collisions;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.game.Board;

public class CollisionSolver {
    private final Board board;

    public CollisionSolver(Board board) {
        this.board = board;
    }

    public void staticActiveEntityWithDynamicEntity(Entity dynamicEntity, Entity staticActiveEntity) {
        board.removeEntityFromBoard(staticActiveEntity);
    }

    public void staticPassiveEntityWithDynamicEntity(Entity staticPassiveEntity, Entity dynamicEntity){
        dynamicEntity.moveBack();
    }

    public void DynamicEntityWithDynamicEntity(Entity firstDynamicEntity, Entity secondDynamicEntity) {
        secondDynamicEntity.moveBack();
    }

    public void endOfMap(Entity entity){
        entity.moveBack();
    }
}
