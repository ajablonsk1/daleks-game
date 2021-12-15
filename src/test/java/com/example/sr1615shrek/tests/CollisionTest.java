package com.example.sr1615shrek.tests;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import com.example.sr1615shrek.test_models.DynamicEntityModel;
import com.example.sr1615shrek.test_models.StaticEntityModel;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollisionTest {

    private BehaviorSubject<List<Entity>> collisionSubject;
    private BehaviorSubject<DynamicEntity> entityMoveSubject;
    private BehaviorSubject<Dalek> deadDalekSubject;

    @BeforeEach
    void setUp(){
        this.collisionSubject = BehaviorSubject.create();
        this.entityMoveSubject = BehaviorSubject.create();
        this.deadDalekSubject = BehaviorSubject.create();
    }

//    @Test
//    public void collisionStaticWithDynamic(){
//
//        // Given
//        Board board = new Board(10,
//                10,
//                collisionSubject,
//                entityMoveSubject,
//                deadDalekSubject);
//        Entity staticEntity = new StaticEntityModel(new Vector2d(2, 3));
//        DynamicEntity dynamicEntity = new DynamicEntityModel(new Vector2d(2, 2),
//                board.getEntityMoveSubject());
//        CollisionDetector collisionDetector = new CollisionDetector(board);
//        board.addEntity(staticEntity);
//        board.addEntity(dynamicEntity);
//
//        // When
//        dynamicEntity.move(Direction.NORTH);
//
//        // Then
//        assertEquals(new Vector2d(2, 2), dynamicEntity.getPosition());
//        assertEquals(new Vector2d(0, 0), staticEntity.getPosition());
//    }
//
//    @Test
//    public void collisionDynamicWithDynamic(){
//
//        // Given
//        Board board = new Board(10,
//                10,
//                collisionSubject,
//                entityMoveSubject,
//                deadDalekSubject);
//        DynamicEntity firstDynamicEntity = new DynamicEntityModel(new Vector2d(2,3),
//                board.getEntityMoveSubject());
//        DynamicEntity secondDynamicEntity = new DynamicEntityModel(new Vector2d(2,2),
//                board.getEntityMoveSubject());
//        CollisionDetector collisionDetector = new CollisionDetector(board);
//        board.addEntity(firstDynamicEntity);
//        board.addEntity(secondDynamicEntity);
//
//        // When
//        secondDynamicEntity.move(Direction.NORTH);
//
//        // Then
//        assertEquals(new Vector2d(2, 3), firstDynamicEntity.getPosition());
//        assertEquals(new Vector2d(2, 2), secondDynamicEntity.getPosition());
//    }
}
