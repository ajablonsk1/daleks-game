package com.example.sr1615shrek;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import com.example.sr1615shrek.test_models.DynamicEntityModel;
import com.example.sr1615shrek.test_models.StaticActiveEntityModel;
import com.example.sr1615shrek.test_models.StaticPassiveEntityModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Sr1615ShrekApplicationTests {

    @Test
    void positionObserverTest() {
//        Board board = new Board(20, 30);
//        DynamicEntity entity = new DynamicEntity1();
//        entity.setPosition(new Vector2d(2, 2));
//        Map<Vector2d, List<Entity>> collisions = new HashMap<>();
//        List<Entity> entities = new LinkedList<>();
//        entities.add(entity);
//        collisions.put(new Vector2d(2, 2), entities);
//        board.setMap(collisions);
//        entity.addObserver(board);
//        entity.move(Direction.NORTH);
//        board.printHashMap();
//        assertNotNull(board.getCollisions().get(new Vector2d(2, 3)));
    }

    @Test
    void detectCollisionTest(){

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



