package com.example.sr1615shrek.tests;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.collisions.visitors.DalekVisitor;
import com.example.sr1615shrek.collisions.visitors.DoctorVisitor;
import com.example.sr1615shrek.collisions.visitors.JunkVisitor;
import com.example.sr1615shrek.collisions.visitors.VisitorService;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.StaticEntity;
import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.model.Junk;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionTest {

    private BehaviorSubject<List<Entity>> collisionSubject;
    private BehaviorSubject<DynamicEntity> entityMoveSubject;
    private BehaviorSubject<Dalek> deadDalekSubject;
    private VisitorService visitorService;

    @BeforeEach
    void setUp() {
        this.collisionSubject = BehaviorSubject.create();
        this.entityMoveSubject = BehaviorSubject.create();
        this.deadDalekSubject = BehaviorSubject.create();
        this.visitorService = new VisitorService(new DalekVisitor(),
                new DoctorVisitor(),
                new JunkVisitor());
    }

    @Test
    public void collisionDalekWithDalek(){

        // Given
        Board board = new Board(10,
                10,
                collisionSubject,
                entityMoveSubject,
                deadDalekSubject,
                visitorService);
        DynamicEntity dalek1 = new Dalek(new Vector2d(2, 2),
                entityMoveSubject,
                deadDalekSubject,
                visitorService.getDalekVisitor());
        DynamicEntity dalek2 = new Dalek(new Vector2d(2, 3),
                entityMoveSubject,
                deadDalekSubject,
                visitorService.getDalekVisitor());
        CollisionDetector collisionDetector = new CollisionDetector(board);
        board.addEntity(dalek1);
        board.addEntity(dalek2);

        // When
        dalek1.move(dalek2.getPosition());

        // Then
        assertTrue(board.getEntities().
                stream().
                anyMatch(entity -> entity.getClass() == Junk.class));
        assertFalse(board.getEntities().
                stream().
                anyMatch(entity -> entity.getClass() == Dalek.class));
    }

    @Test
    public void collisionDoctorWithDalek(){

        // Given
        Board board = new Board(10,
                10,
                collisionSubject,
                entityMoveSubject,
                deadDalekSubject,
                visitorService);
        DynamicEntity dalek = new Dalek(new Vector2d(2, 2),
                entityMoveSubject,
                deadDalekSubject,
                visitorService.getDalekVisitor());
        Doctor doctor = new Doctor(new Vector2d(2, 3),
                entityMoveSubject,
                visitorService.getDoctorVisitor());
        CollisionDetector collisionDetector = new CollisionDetector(board);
        board.addEntity(dalek);
        board.addEntity(doctor);

        // When
        dalek.move(doctor.getPosition());

        // Then
        assertFalse(doctor.isAlive());
    }

    @Test
    public void collisionJunkWithDalek(){

        // Given
        Board board = new Board(10,
                10,
                collisionSubject,
                entityMoveSubject,
                deadDalekSubject,
                visitorService);
        DynamicEntity dalek = new Dalek(new Vector2d(2, 2),
                entityMoveSubject,
                deadDalekSubject,
                visitorService.getDalekVisitor());
        StaticEntity junk = new Junk(new Vector2d(2, 3),
                visitorService.getJunkVisitor());
        CollisionDetector collisionDetector = new CollisionDetector(board);
        board.addEntity(dalek);
        board.addEntity(junk);

        // When
        dalek.move(junk.getPosition());

        // Then
        assertTrue(board.getEntities().
                stream().
                anyMatch(entity -> entity.getClass() == Junk.class));
        assertEquals(1, board.getEntities().size());
        assertFalse(board.getEntities().
                stream().
                anyMatch(entity -> entity.getClass() == Dalek.class));
    }

    @Test
    public void collisionJunkWithDoctor(){

        // Given
        Board board = new Board(10,
                10,
                collisionSubject,
                entityMoveSubject,
                deadDalekSubject,
                visitorService);
        StaticEntity junk = new Junk(new Vector2d(2, 3),
                visitorService.getJunkVisitor());
        Doctor doctor = new Doctor(new Vector2d(2, 2),
                entityMoveSubject,
                visitorService.getDoctorVisitor());
        CollisionDetector collisionDetector = new CollisionDetector(board);
        board.addEntity(junk);
        board.addEntity(doctor);

        // When
        doctor.move(Direction.NORTH);

        // Then
        assertEquals(new Vector2d(2, 2), doctor.getPosition());
    }

    @Test
    public void endOfTheBoardCollision(){

        // Given
        Board board = new Board(10,
                10,
                collisionSubject,
                entityMoveSubject,
                deadDalekSubject,
                visitorService);
        Doctor doctor = new Doctor(new Vector2d(9, 9),
                entityMoveSubject,
                visitorService.getDoctorVisitor());
        CollisionDetector collisionDetector = new CollisionDetector(board);
        board.addEntity(doctor);

        // When
        doctor.move(Direction.NORTH);

        // Then
        assertEquals(new Vector2d(9, 9), doctor.getPosition());
    }
}
