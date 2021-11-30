package com.example.sr1615shrek;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import com.example.sr1615shrek.test_models.DynamicEntityModel;
import com.example.sr1615shrek.test_models.StaticActiveEntityModel;
import com.example.sr1615shrek.test_models.StaticPassiveEntityModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @Test
    void vector2dTest(){
        Vector2d vector2d1 = new Vector2d(2, 3);
        Vector2d vector2d2 = new Vector2d(5, 8);

        Vector2d addResult = vector2d1.add(vector2d2);
        Vector2d subtractResult = vector2d1.subtract(vector2d2);

        assertEquals(new Vector2d(7, 11), addResult);
        assertEquals(new Vector2d(-3, -5), subtractResult);
    }

    @Test
    void getEntitiesTest(){
        Entity staticPassiveEntity = new StaticPassiveEntityModel(new Vector2d(2, 2));
        Entity dynamicEntity = new DynamicEntityModel(new Vector2d(2, 3));

        Board board = new Board(10, 10);
        board.addEntity(staticPassiveEntity);
        board.addEntity(dynamicEntity);
        List<Entity> entities = board.getEntities();

        assertEquals(staticPassiveEntity, entities.get(0));
        assertEquals(dynamicEntity, entities.get(1));
    }

    @Test
    void removeEntityFromTheBoardTest(){
        Entity staticPassiveEntity = new StaticPassiveEntityModel(new Vector2d(2, 2));
        Entity dynamicEntity = new DynamicEntityModel(new Vector2d(2, 3));

        Board board = new Board(10, 10);
        board.addEntity(staticPassiveEntity);
        board.addEntity(dynamicEntity);

        board.removeEntityFromBoard(staticPassiveEntity);

        assertEquals(dynamicEntity, board.getEntities().get(0));
        assertEquals(1, board.getEntities().size());
    }

    @Test
    void solvePassiveEntityWithDynamicEntityCollisionTest(){
        Entity staticPassiveEntity = new StaticPassiveEntityModel(new Vector2d(2, 2));
        Entity dynamicEntity = new DynamicEntityModel(new Vector2d(2, 3));

        Board board = new Board(30, 30);
        board.addEntity(staticPassiveEntity);
        board.addEntity(dynamicEntity);
        dynamicEntity.addObserver(board);


        dynamicEntity.move(Direction.SOUTH);


        assertEquals(staticPassiveEntity.getPosition(), new Vector2d(2, 2));
        assertEquals(dynamicEntity.getPosition(), new Vector2d(2, 3));
    }

    @Test
    void solveActiveEntityWithDynamicEntityCollisionTest(){
        Entity staticActiveEntity = new StaticActiveEntityModel(new Vector2d(2, 2));
        Entity dynamicEntity = new DynamicEntityModel(new Vector2d(2, 3));

        Board board = new Board(30, 30);
        board.addEntity(staticActiveEntity);
        board.addEntity(dynamicEntity);
        dynamicEntity.addObserver(board);


        dynamicEntity.move(Direction.SOUTH);


        assertEquals(dynamicEntity.getPosition(), new Vector2d(2, 2));
    }

    @Test
    void solveDynamicEntityWithDynamicEntityCollisionTest(){
        Entity firstDynamicEntity = new DynamicEntityModel(new Vector2d(2, 2));
        Entity secondDynamicEntity = new DynamicEntityModel(new Vector2d(2, 3));

        Board board = new Board(30, 30);
        board.addEntity(firstDynamicEntity);
        board.addEntity(secondDynamicEntity);
        secondDynamicEntity.addObserver(board);


        secondDynamicEntity.move(Direction.SOUTH);


        assertEquals(firstDynamicEntity.getPosition(), new Vector2d(2, 2));
        assertEquals(secondDynamicEntity.getPosition(), new Vector2d(2, 3));
    }
}



