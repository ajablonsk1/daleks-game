package com.example.sr1615shrek;

import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.DynamicEntity1;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class Sr1615ShrekApplicationTests {

    @Test
    void positionObserverTest() {
        Board board = new Board(20, 30);
        DynamicEntity entity = new DynamicEntity1();
        entity.setPosition(new Vector2d(2, 2));
        Map<Vector2d, List<Entity>> collisions = new HashMap<>();
        List<Entity> entities = new LinkedList<>();
        entities.add(entity);
        collisions.put(new Vector2d(2, 2), entities);
        board.setMap(collisions);
        entity.addObserver(board);
        entity.move(Direction.NORTH);
        board.printHashMap();
        assertNotNull(board.getCollisions().get(new Vector2d(2, 3)));
    }
}
