package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.collisions.visitors.VisitorService;
import com.example.sr1615shrek.config.database.LevelsMapsReader;
import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.model.powerups.PowerUp;
import com.example.sr1615shrek.entity.model.powerups.PowerUpHistory;
import com.example.sr1615shrek.entity.model.powerups.Teleport;
import com.example.sr1615shrek.entity.model.powerups.TimeReverse;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class EntityInitializer {

    private final Board board;

    private final SubjectService subjectService;

    private final VisitorService visitorService;

    private final PowerUpHistory powerUpHistory;

    private final Random random;

    @Value("${entityInitializer.startingDaleksAmount}")
    private int startingDaleksAmount;

    @Value("${entityInitializer.spawnPowerUpFrequency}")
    private int spawnPowerUpFrequency;

    @Autowired
    public EntityInitializer(Board board,
                             SubjectService subjectService,
                             VisitorService visitorService,
                             PowerUpHistory powerUpHistory){
        this.board = board;
        this.subjectService = subjectService;
        this.visitorService = visitorService;
        this.powerUpHistory = powerUpHistory;
        this.random = new Random();
    }

    public void spawnPowerUp(int tour){
        if(tour % this.spawnPowerUpFrequency == 0){
            int x = random.nextInt(2);
            PowerUp powerUp;
            if (x == 0) {
                powerUp = new Teleport(getRandomVector(),
                        this.visitorService.getPowerUpVisitor(),
                        this.subjectService.getDeadTeleportSubject(),
                        this.board);
            } else {
                powerUp = new TimeReverse(getRandomVector(),
                        this.visitorService.getPowerUpVisitor(),
                        this.subjectService.getDeadTimeReverseSubject());
            }


            addEntityToBoardOnRandomPosition(powerUp);
            this.powerUpHistory.push(tour, powerUp);
        }
    }

    private boolean isVectorOccupied(Vector2d vector2d) {
        List<Entity> entityList = board.getEntitiesOnVector(vector2d);
        return entityList != null && !entityList.isEmpty();
    }

    private Vector2d getRandomVector() {
        return new Vector2d(
                random.nextInt(board.getWidth()),
                random.nextInt(board.getHeight())
        );
    }

    private void addEntityToBoardOnRandomPosition(Entity entity) {
        while(isVectorOccupied(entity.getPosition())) {
            entity.setPosition(getRandomVector());
        }

        board.addEntity(entity);
    }

    private void addDoctorOnPosition(Vector2d position) {
        Doctor doctor = new Doctor(position,
                this.subjectService.getEntityMoveSubject(),
                this.visitorService.getDoctorVisitor());
        addEntityToBoardOnRandomPosition(doctor);
        this.board.setDoctor(doctor);
    }

    public void addDoctorToBoardRandom() {
        addDoctorOnPosition(getRandomVector());
    }

    public void addDoctorToBoardFromDb(int levelID) {
        addDoctorOnPosition(LevelsMapsReader.getDoctorPosition(levelID).get());
    }

    public void addDaleksToBoardRandom(){
        for(int i = 0; i < startingDaleksAmount; i++) {
            addEntityToBoardOnRandomPosition(new Dalek(getRandomVector(),
                    this.subjectService.getEntityMoveSubject(),
                    this.subjectService.getDeadDaleksSubject(),
                    this.visitorService.getDalekVisitor()));
        }
    }

    public void addDaleksToBoardFromDb(int levelID) {
        LevelsMapsReader.getDaleksPositions(levelID).forEach(position -> board.addEntity(new Dalek(position,
                this.subjectService.getEntityMoveSubject(),
                this.subjectService.getDeadDaleksSubject(),
                this.visitorService.getDalekVisitor()))
        );
    }
}
