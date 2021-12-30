package com.example.sr1615shrek.tests;

import com.example.sr1615shrek.collisions.visitors.DalekVisitor;
import com.example.sr1615shrek.collisions.visitors.DoctorVisitor;
import com.example.sr1615shrek.collisions.visitors.JunkVisitor;
import com.example.sr1615shrek.collisions.visitors.VisitorService;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Junk;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoardTest {

    private BehaviorSubject<DynamicEntity> entityMoveSubject;
    private BehaviorSubject<Dalek> deadDalekSubject;
    private VisitorService visitorService;

    @Autowired
    private Board board;

    @BeforeEach
    void setUp() {
        this.visitorService = new VisitorService(new DalekVisitor(),
                new DoctorVisitor(),
                new JunkVisitor());
        this.entityMoveSubject = board.getEntityMoveSubject();
        this.deadDalekSubject = board.getDeadDaleksSubject();
        this.board.getEntities().forEach(board::removeEntityFromBoard);
    }

    @Test
    public void addAndGetEntities(){

        // Given
        Entity dalek = new Dalek(new Vector2d(2, 2), entityMoveSubject, deadDalekSubject,visitorService.getDalekVisitor());
        Entity junk = new Junk(new Vector2d(2, 3), visitorService.getJunkVisitor());

        // When
        board.addEntity(dalek);
        board.addEntity(junk);

        // Then
        assertEquals(dalek, board.getEntities().get(0));
        assertEquals(junk, board.getEntities().get(1));
    }

    @Test
    void removeEntityFromTheBoard(){

        // Given
        Entity dalek = new Dalek(new Vector2d(2, 2), entityMoveSubject, deadDalekSubject,visitorService.getDalekVisitor());
        Entity junk = new Junk(new Vector2d(2, 3), visitorService.getJunkVisitor());


        // When
        board.addEntity(dalek);
        board.addEntity(junk);
        board.removeEntityFromBoard(dalek);

        // Then
        assertEquals(junk, board.getEntities().get(0));
        assertEquals(1, board.getEntities().size());
    }
}
