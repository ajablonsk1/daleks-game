package com.example.sr1615shrek.game;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.collisions.visitors.DalekVisitor;
import com.example.sr1615shrek.collisions.visitors.DoctorVisitor;
import com.example.sr1615shrek.collisions.visitors.JunkVisitor;
import com.example.sr1615shrek.collisions.visitors.VisitorService;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Doctor;
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

    private VisitorService visitorService;

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
            this.boardPresenter.showPopUpWindowForLose();
        } else if(this.isGameWin()) {
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

    private void addDoctorToBoard(){
        Doctor doctor = new Doctor(getRandomVector(),
                this.board.getEntityMoveSubject(),
                this.visitorService.getDoctorVisitor());

        addEntityToBoardOnRandomPosition(doctor);
        this.board.setDoctor(doctor);
    }

    private void addDaleksToBoard(){
        for(int i = 0; i < startingDaleksAmount; i++) {
            addEntityToBoardOnRandomPosition(new Dalek(getRandomVector(),
                    this.board.getEntityMoveSubject(),
                    this.board.getDeadDaleksSubject(),
                    this.visitorService.getDalekVisitor()));
        }
    }

    public void start(){
        addDoctorToBoard();
        addDaleksToBoard();
        this.boardPresenter.updateMap(this.board.getEntities());
    }


    public void startTurn(Direction direction){
        this.board.getDoctor().move(direction);
        this.board.getEntities()
                .stream()
                .filter(entity -> entity.getClass() == Dalek.class)
                .forEach(entity -> ((Dalek) entity).move(this.board.getDoctor().getPosition()));
        this.boardPresenter.updateMap(this.board.getEntities());

        this.isGameEnd();
    }
}
