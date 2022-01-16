package com.example.sr1615shrek.game;

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
import com.example.sr1615shrek.entity.model.powerups.Teleport;
import com.example.sr1615shrek.entity.model.powerups.TimeReverse;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.view.AppController;
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

    private int tour = 0;

    @Autowired
    public Engine(BoardPresenter boardPresenter,
                  Board board,
                  CollisionDetector collisionDetector,
                  VisitorService visitorService){
        this.board = board;
        this.boardPresenter = boardPresenter;
        this.collisionDetector = collisionDetector;
        this.visitorService = visitorService;
        this.board.getDeadDaleksSubject().subscribe(this::onEntityDeath);
        this.board.getDeadTeleportSubject().subscribe(this::onEntityDeath);
        this.board.getDeadTimeReverseSubject().subscribe(this::onEntityDeath);
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


    public void startTurn(Direction direction) {
        this.board.getDoctor().move(direction);
        this.board.getEntities()
                .stream()
                .filter(Dalek.class::isInstance)
                .forEach(entity -> ((Dalek) entity).move(this.board.getDoctor().getPosition()));
        this.spawnPowerUp();
        this.boardPresenter.updateMap(this.board.getEntities());

        this.isGameEnd();
    }

    private void onEntityDeath(Entity entity){
        Vector2d position = entity.getPosition();
        if(entity.getClass() == Teleport.class){
            this.board.getDoctor().getTeleportList().add((Teleport) entity);
        }
        if(entity.getClass() == TimeReverse.class){
            this.board.getDoctor().getTimeReverseList().add((TimeReverse) entity);
        }
        this.board.removeEntityFromBoard(entity);
        if(entity.getClass() == Dalek.class) {
            if (this.board.getEntitiesOnVector(position).isEmpty()) {
                Junk junk = new Junk(position, this.visitorService.getJunkVisitor());
                this.board.getEntitiesOnVector(position).add(junk);
            }
        }
    }

    public void useTimeReverse(){
        if(board.getDoctor().getTimeReverseList().size() > 0){
            System.out.println(this.board.getEntities().size());
            this.board.getEntities()
                    .stream()
                    .filter(DynamicEntity.class::isInstance)
                    .forEach(entity -> this.board.getDoctor().getTimeReverseList().get(0).reverseTime((DynamicEntity) entity));
            this.board.getDoctor().useTimeReverse();
        }
        this.board.getDoctor().getLastPositions().forEach(System.out::println);
        this.boardPresenter.updateMap(this.board.getEntities());
    }

    public void useTeleport(){
        if(board.getDoctor().getTeleportList().size() > 0){
            board.getDoctor().useTeleport();
        }
        this.boardPresenter.updateMap(this.board.getEntities());
    }

    private void spawnPowerUp(){
        this.tour += 1;
        if(this.tour % 3 == 0){
            int x = random.nextInt(2);
            if(x == 0){
                addEntityToBoardOnRandomPosition(new Teleport(getRandomVector(),
                        this.visitorService.getTeleportVisitor(),
                        this.board.getDeadTeleportSubject(),
                        this.board));
            } else{
                addEntityToBoardOnRandomPosition(new TimeReverse(getRandomVector(),
                        this.visitorService.getTimeReverseVisitor(),
                        this.board.getDeadTimeReverseSubject()));
            }
        }
    }
}
