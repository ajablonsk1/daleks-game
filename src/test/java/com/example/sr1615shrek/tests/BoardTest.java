package com.example.sr1615shrek.tests;

import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import com.example.sr1615shrek.test_models.DynamicEntityModel;
import com.example.sr1615shrek.test_models.StaticEntityModel;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {

    private BehaviorSubject<List<Entity>> collisionSubject;
    private BehaviorSubject<DynamicEntity> entityMoveSubject;

    @BeforeEach
    void setUp(){
        this.collisionSubject = BehaviorSubject.create();
        this.entityMoveSubject = BehaviorSubject.create();
    }

    @Test
    void addAndGetEntities(){

        // Given
        Board board = new Board(10,10, collisionSubject, entityMoveSubject);
        Entity staticPassiveEntity = new StaticEntityModel(new Vector2d(2, 2));
        Entity dynamicEntity = new DynamicEntityModel(new Vector2d(2, 3), board.getEntityMoveSubject());

        // When
        board.addEntity(staticPassiveEntity);
        board.addEntity(dynamicEntity);

        // Then
        assertEquals(staticPassiveEntity, board.getEntities().get(0));
        assertEquals(dynamicEntity, board.getEntities().get(1));
    }

    @Test
    void removeEntityFromTheBoard(){

        // Given
        Board board = new Board(10,10, collisionSubject, entityMoveSubject);
        Entity staticPassiveEntity = new StaticEntityModel(new Vector2d(2, 2));
        Entity dynamicEntity = new DynamicEntityModel(new Vector2d(2, 3), board.getEntityMoveSubject());


        // When
        board.addEntity(staticPassiveEntity);
        board.addEntity(dynamicEntity);
        board.removeEntityFromBoard(staticPassiveEntity);

        // Then
        assertEquals(dynamicEntity, board.getEntities().get(0));
        assertEquals(1, board.getEntities().size());
    }
}
