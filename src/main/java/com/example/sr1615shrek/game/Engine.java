package com.example.sr1615shrek.game;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.collisions.visitors.VisitorService;
import com.example.sr1615shrek.config.database.LevelsMapsReader;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.model.Junk;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.view.BoardPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class Engine {

    private final Board board;

    private final BoardPresenter boardPresenter;

    private final CollisionDetector collisionDetector;

    private final VisitorService visitorService;

    @Value("${engine.startingDaleksAmount}")
    private int startingDaleksAmount;

    private final Random random = new Random();

    @Autowired
    public Engine(BoardPresenter boardPresenter,
                  Board board,
                  CollisionDetector collisionDetector,
                  VisitorService visitorService){
        this.board = board;
        this.boardPresenter = boardPresenter;
        this.collisionDetector = collisionDetector;
        this.visitorService = visitorService;
        this.board.getDeadDaleksSubject().subscribe(this::onDalekDeath);
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
                this.board.getEntityMoveSubject(),
                this.visitorService.getDoctorVisitor());

        addEntityToBoardOnRandomPosition(doctor);
        this.board.setDoctor(doctor);
    }

    private void addDoctorToBoardRandom() {
        addDoctorOnPosition(getRandomVector());
    }

    private void addDoctorToBoardFromFile(int levelID) {
        addDoctorOnPosition(LevelsMapsReader.getDoctorPosition(levelID).get());
    }

    private void addDaleksToBoardRandom(){
        for(int i = 0; i < startingDaleksAmount; i++) {
            addEntityToBoardOnRandomPosition(new Dalek(getRandomVector(),
                    this.board.getEntityMoveSubject(),
                    this.board.getDeadDaleksSubject(),
                    this.visitorService.getDalekVisitor()));
        }
    }

    private void addDaleksToBoardFromFile(int levelID) {
       LevelsMapsReader.getDaleksPositions(levelID).forEach(position -> board.addEntity(new Dalek(position,
                   this.board.getEntityMoveSubject(),
                   this.board.getDeadDaleksSubject(),
                   this.visitorService.getDalekVisitor()))
       );
    }

    private void updateBoardPresenter() {
        this.boardPresenter.updateMap(this.board.getEntities());
    }

    public void startRandom(){
        addDoctorToBoardRandom();
        addDaleksToBoardRandom();
        updateBoardPresenter();
    }

    public void startCampaign(int levelID) {
        addDoctorToBoardFromFile(levelID);
        addDaleksToBoardFromFile(levelID);
        updateBoardPresenter();
    }

    public void startTurn(Direction direction) {
        this.board.getDoctor().move(direction);
        this.board.getEntities()
                .stream()
                .filter(Dalek.class::isInstance)
                .forEach(entity -> ((Dalek) entity).move(this.board.getDoctor().getPosition()));
        this.boardPresenter.updateMap(this.board.getEntities());

        this.isGameEnd();
    }

    private void onDalekDeath(Dalek dalek){
        Vector2d position = dalek.getPosition();
        this.board.removeEntityFromBoard(dalek);
        if(this.board.getEntitiesOnVector(position).isEmpty()){
            Junk junk = new Junk(position, this.visitorService.getJunkVisitor());
            this.board.getEntitiesOnVector(position).add(junk);
        }
    }
}
