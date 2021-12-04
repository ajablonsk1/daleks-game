package com.example.sr1615shrek.collisions;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.game.Board;

public class CollisionSolver {

    private final Board board;

    public CollisionSolver(Board board) {
        this.board = board;
    }

    public void staticActiveEntityWithDynamicEntity(Entity dynamicEntity,
                                                    Entity staticActiveEntity) {

        //SOLVE THE COLLISION, FOR EXAMPLE:
        board.removeEntityFromBoard(staticActiveEntity);
    }

    public void staticPassiveEntityWithDynamicEntity(Entity staticPassiveEntity,
                                                     Entity dynamicEntity){

        //SOLVE THE COLLISION, FOR EXAMPLE:
    }

    public void dynamicEntityWithDynamicEntity(Entity firstDynamicEntity,
                                               Entity secondDynamicEntity) {

        //SOLVE THE COLLISION, FOR EXAMPLE:
    }

    public void endOfMap(Entity entity){
    }
}
