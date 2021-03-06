package com.example.sr1615shrek.tests;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.collisions.visitors.VisitorService;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.StaticEntity;
import com.example.sr1615shrek.entity.SubjectService;
import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.model.Junk;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CollisionTest {

    private BehaviorSubject<DynamicEntity> entityMoveSubject;
    private BehaviorSubject<Dalek> deadDalekSubject;

    @Autowired
    private CollisionDetector collisionDetector;

    @Autowired
    private Board board;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private SubjectService subjectService;

    @BeforeEach
    void setUp() {
        this.entityMoveSubject = subjectService.getEntityMoveSubject();
        this.deadDalekSubject = subjectService.getDeadDaleksSubject();
        this.board.getEntities().forEach(board::removeEntityFromBoard);
    }

    @Test
    public void collisionDalekWithDalek(){

        // Given
        DynamicEntity dalek1 = new Dalek(new Vector2d(2, 2),
                entityMoveSubject,
                deadDalekSubject,
                visitorService.getDalekVisitor());
        DynamicEntity dalek2 = new Dalek(new Vector2d(2, 3),
                entityMoveSubject,
                deadDalekSubject,
                visitorService.getDalekVisitor());
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
        DynamicEntity dalek = new Dalek(new Vector2d(2, 2),
                entityMoveSubject,
                deadDalekSubject,
                visitorService.getDalekVisitor());
        Doctor doctor = new Doctor(new Vector2d(2, 3),
                entityMoveSubject,
                visitorService.getDoctorVisitor());
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
        DynamicEntity dalek = new Dalek(new Vector2d(2, 2),
                entityMoveSubject,
                deadDalekSubject,
                visitorService.getDalekVisitor());
        StaticEntity junk = new Junk(new Vector2d(2, 3),
                visitorService.getJunkVisitor());
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
        StaticEntity junk = new Junk(new Vector2d(2, 3),
                visitorService.getJunkVisitor());
        Doctor doctor = new Doctor(new Vector2d(2, 2),
                entityMoveSubject,
                visitorService.getDoctorVisitor());
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
        Doctor doctor = new Doctor(new Vector2d(19, 19),
                entityMoveSubject,
                visitorService.getDoctorVisitor());
        board.addEntity(doctor);

        // When
        doctor.move(Direction.NORTH);

        // Then
        assertEquals(new Vector2d(19, 19), doctor.getPosition());
    }
}
