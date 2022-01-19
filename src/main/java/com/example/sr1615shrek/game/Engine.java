package com.example.sr1615shrek.game;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.collisions.visitors.VisitorService;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.EntityInitializer;
import com.example.sr1615shrek.entity.SubjectService;
import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.model.Junk;
import com.example.sr1615shrek.entity.model.powerups.PowerUp;
import com.example.sr1615shrek.entity.model.powerups.PowerUpHistory;
import com.example.sr1615shrek.entity.model.powerups.Teleport;
import com.example.sr1615shrek.entity.model.powerups.TimeReverse;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.view.BoardPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class Engine {

    private final Board board;

    private final BoardPresenter boardPresenter;

    private final CollisionDetector collisionDetector;

    private final VisitorService visitorService;

    private final SubjectService subjectService;

    private final PowerUpHistory powerUpHistory;

    private final EntityInitializer entityInitializer;

    private int tour = 0;

    @Autowired
    public Engine(BoardPresenter boardPresenter,
                  Board board,
                  CollisionDetector collisionDetector,
                  VisitorService visitorService,
                  SubjectService subjectService,
                  PowerUpHistory powerUpHistory,
                  EntityInitializer entityInitializer){
        this.board = board;
        this.boardPresenter = boardPresenter;
        this.collisionDetector = collisionDetector;
        this.visitorService = visitorService;
        this.subjectService = subjectService;
        this.powerUpHistory = powerUpHistory;
        this.entityInitializer = entityInitializer;
        subscribeSubjects();
    }


    private boolean isGameWin() {
        return this.board.getEntities()
                .stream()
                .filter(entity -> entity.getClass() == Dalek.class)
                .findAny()
                .isEmpty();
    }

    private boolean isGameLose() {
        return !this.board.getDoctor().isAlive();
    }

    private void isGameEnd() {
        if(this.isGameLose()) {
            this.board.getEntities().forEach(board::removeEntityFromBoard);
            this.boardPresenter.showPopUpWindowForLose();
        } else if(this.isGameWin()) {
            this.board.getEntities().forEach(board::removeEntityFromBoard);
            this.boardPresenter.showPopUpWindowForWin();
        }
    }


    private void updateBoardPresenter() {
        this.boardPresenter.updateMap(this.board.getEntities());
    }

    public void startRandom(){
        this.entityInitializer.addDoctorToBoardRandom();
        this.entityInitializer.addDaleksToBoardRandom();
        updateBoardPresenter();
    }

    public void startCampaign(int levelID) {
        this.entityInitializer.addDoctorToBoardFromDb(levelID);
        this.entityInitializer.addDaleksToBoardFromDb(levelID);
        updateBoardPresenter();
    }

    public void startTurn(Direction direction) {
        this.tour += 1;
        this.board.getDoctor().move(direction);
        getDaleksStream().forEach(entity -> ((Dalek) entity).move(this.board.getDoctor().getPosition()));
        this.entityInitializer.spawnPowerUp(this.tour);
        this.boardPresenter.updateMap(this.board.getEntities());

        this.isGameEnd();
    }

    private void onDaleksDeath(Dalek dalek){
        Vector2d position = dalek.getPosition();
        this.board.removeEntityFromBoard(dalek);
        if(this.board.getEntitiesOnVector(position).isEmpty()){
            Junk junk = new Junk(position, this.visitorService.getJunkVisitor());
            this.board.getEntitiesOnVector(position).add(junk);
        }
    }

    private void onTeleportDeath(Teleport teleport){
        onPowerUpDeath(teleport);
        this.board.getDoctor().addTeleport(teleport);
    }

    private void onTimeReverseDeath(TimeReverse timeReverse){
        onPowerUpDeath(timeReverse);
        this.board.getDoctor().addTimeReverse(timeReverse);
    }

    private void onPowerUpDeath(PowerUp powerUp){
        this.board.removeEntityFromBoard(powerUp);
        this.powerUpHistory.push(this.tour, powerUp);
    }

    public void useTimeReverse(){
        Doctor doctor = this.board.getDoctor();
        if(!doctor.isTimeReverseListEmpty()){

            PowerUp timeReverse = doctor.getTimeReverse();
            getDaleksStream().forEach(entity -> timeReverse.execute((DynamicEntity) entity));
            timeReverse.execute(doctor);

            doctor.removeTimeReverseFromEq();
            this.powerUpHistory.pop(this.tour--).forEach(this::handleTimeReverseForPowerUps);
        }
        this.boardPresenter.updateMap(this.board.getEntities());
    }

    private void handleTimeReverseForPowerUps(PowerUp powerUp){
        if(board.getEntities().contains(powerUp)){
            this.board.removeEntityFromBoard(powerUp);
        } else{
            this.board.addEntity(powerUp);
        }
    }

    public void useTeleport(){
        updateDaleksAfterTeleport();
        this.tour += 1;
        if(!board.getDoctor().isTeleportListEmpty()){
            board.getDoctor().useTeleport();
        }
        this.boardPresenter.updateMap(this.board.getEntities());
    }

    private void updateDaleksAfterTeleport(){
        getDaleksStream().forEach(dalek -> ((DynamicEntity) dalek).move(dalek.getPosition()));
    }

    private Stream<Entity> getDaleksStream(){
        return this.board.getEntities()
                .stream()
                .filter(Dalek.class::isInstance);
    }

    private void subscribeSubjects(){
        this.subjectService.getDeadDaleksSubject().subscribe(this::onDaleksDeath);
        this.subjectService.getDeadTeleportSubject().subscribe(this::onTeleportDeath);
        this.subjectService.getDeadTimeReverseSubject().subscribe(this::onTimeReverseDeath);
    }
}
