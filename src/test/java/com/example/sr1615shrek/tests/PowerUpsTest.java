package com.example.sr1615shrek.tests;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.collisions.visitors.VisitorService;
import com.example.sr1615shrek.entity.EntityInitializer;
import com.example.sr1615shrek.entity.SubjectService;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.model.powerups.PowerUpHistory;
import com.example.sr1615shrek.entity.model.powerups.Teleport;
import com.example.sr1615shrek.entity.model.powerups.TimeReverse;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import com.example.sr1615shrek.game.Engine;
import com.example.sr1615shrek.game.PositionRandomizer;
import com.example.sr1615shrek.view.BoardPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PowerUpsTest {

    private Engine engine;

    @Autowired
    private CollisionDetector collisionDetector;

    @Autowired
    private Board board;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    PowerUpHistory powerUpHistory;

    @Autowired
    EntityInitializer entityInitializer;

    @Autowired
    PositionRandomizer positionRandomizer;

    @Mock
    BoardPresenter boardPresenter;

    @BeforeEach
    void setUp() {
        this.board.getEntities().forEach(board::removeEntityFromBoard);
        engine = new Engine(boardPresenter,
                board,
                collisionDetector,
                visitorService,
                subjectService,
                powerUpHistory,
                entityInitializer);

    }

    @Test
    public void teleportTest(){

        //Given
        Vector2d teleportPosition = new Vector2d(2, 1);
        Teleport teleport = new Teleport(teleportPosition,
                visitorService.getPowerUpVisitor(),
                subjectService.getDeadTeleportSubject(),
                positionRandomizer);
        Doctor doctor = new Doctor(new Vector2d(2, 0),
                subjectService.getEntityMoveSubject(),
                visitorService.getDoctorVisitor());
        board.addEntity(doctor);
        board.setDoctor(doctor);
        board.addEntity(teleport);

        //When
        doctor.move(Direction.NORTH);
        doctor.useTeleport();

        //Then
        assertTrue(board.getEntitiesOnVector(teleportPosition).isEmpty());
        assertNotEquals(teleportPosition, doctor.getPosition());
    }

    @Test
    public void timeReverseTest(){

        //Given
        Vector2d timeReversePosition = new Vector2d(2, 1);
        Vector2d doctorInitialPosition = new Vector2d(2, 0);
        TimeReverse timeReverse = new TimeReverse(timeReversePosition,
                visitorService.getPowerUpVisitor(),
                subjectService.getDeadTimeReverseSubject());
        Doctor doctor = new Doctor(doctorInitialPosition,
                subjectService.getEntityMoveSubject(),
                visitorService.getDoctorVisitor());
        board.addEntity(doctor);
        board.setDoctor(doctor);
        board.addEntity(timeReverse);
        this.powerUpHistory.push(0, timeReverse);

        //When
        doctor.move(Direction.NORTH);
        engine.useTimeReverse();

        //Then
        assertFalse(board.getEntitiesOnVector(timeReversePosition).isEmpty());
        assertEquals(doctorInitialPosition, doctor.getPosition());
    }
}
