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
import com.example.sr1615shrek.game.PositionRandomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class EntityInitializer {

    private final Board board;

    private final SubjectService subjectService;

    private final VisitorService visitorService;

    private final PowerUpHistory powerUpHistory;

    private final Random random;

    private final LevelsMapsReader levelsMapsReader;

    private final PositionRandomizer positionRandomizer;

    @Value("${entityInitializer.startingDaleksAmount}")
    private int startingDaleksAmount;

    @Value("${entityInitializer.spawnPowerUpFrequency}")
    private int spawnPowerUpFrequency;

    @Autowired
    public EntityInitializer(Board board,
                             SubjectService subjectService,
                             VisitorService visitorService,
                             PowerUpHistory powerUpHistory,
                             LevelsMapsReader levelsMapsReader,
                             PositionRandomizer positionRandomizer){
        this.board = board;
        this.subjectService = subjectService;
        this.visitorService = visitorService;
        this.powerUpHistory = powerUpHistory;
        this.random = new Random();
        this.levelsMapsReader = levelsMapsReader;
        this.positionRandomizer = positionRandomizer;
    }

    public void spawnPowerUp(int tour){
        if(tour % this.spawnPowerUpFrequency == 0){
            int x = random.nextInt(2);
            PowerUp powerUp;
            if (x == 0) {
                powerUp = new Teleport(this.positionRandomizer.getRandomPosition(),
                        this.visitorService.getPowerUpVisitor(),
                        this.subjectService.getDeadTeleportSubject(),
                        this.positionRandomizer);
            } else {
                powerUp = new TimeReverse(this.positionRandomizer.getRandomPosition(),
                        this.visitorService.getPowerUpVisitor(),
                        this.subjectService.getDeadTimeReverseSubject());
            }


            addEntityToBoardOnRandomPosition(powerUp);
            this.powerUpHistory.push(tour, powerUp);
        }
    }

    private void addEntityToBoardOnRandomPosition(Entity entity) {
        entity.setPosition(this.positionRandomizer.getRandomPosition());
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
        addDoctorOnPosition(this.positionRandomizer.getRandomPosition());
    }

    public void addDoctorToBoardFromDb(int levelID) {
        addDoctorOnPosition(levelsMapsReader.getDoctorPosition(levelID).get());
    }

    public void addDaleksToBoardRandom(){
        for(int i = 0; i < startingDaleksAmount; i++) {
            addEntityToBoardOnRandomPosition(createDalekOnPosition(this.positionRandomizer.getRandomPosition()));
        }
    }

    public void addDaleksToBoardFromDb(int levelID) {
        levelsMapsReader.getDaleksPositions(levelID)
                .forEach(position -> board.addEntity(createDalekOnPosition(position)));
    }

    private Dalek createDalekOnPosition(Vector2d position){
        return new Dalek(position,
                this.subjectService.getEntityMoveSubject(),
                this.subjectService.getDeadDaleksSubject(),
                this.visitorService.getDalekVisitor());
    }
}
