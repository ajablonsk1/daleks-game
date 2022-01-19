package com.example.sr1615shrek.entity.model.powerups;

import com.example.sr1615shrek.collisions.visitors.PowerUpVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.StaticEntity;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.List;
import java.util.Random;

public class Teleport extends StaticEntity implements PowerUp {

    private final PowerUpVisitor powerUpVisitor;

    private final BehaviorSubject<Teleport> deadTeleportSubject;

    private final Board board;

    public Teleport(Vector2d position,
                    PowerUpVisitor powerUpVisitor,
                    BehaviorSubject<Teleport> deadTeleportSubject,
                    Board board) {
        super(position);
        this.powerUpVisitor = powerUpVisitor;
        this.deadTeleportSubject = deadTeleportSubject;
        this.board = board;
    }

    @Override
    public void execute(DynamicEntity doctor){
        Vector2d newPosition = getRandomVector();
        while(isVectorOccupied(newPosition)) {
            newPosition = getRandomVector();
        }
        doctor.moveOnSpecificPosition(newPosition);
    }

    @Override
    public void undo(){
        deadTeleportSubject.onNext(this);
    }


    public Vector2d getRandomVector(){
        Random random = new Random();
        return new Vector2d(random.nextInt(this.board.getWidth()), random.nextInt(this.board.getHeight()));
    }

    private boolean isVectorOccupied(Vector2d vector2d) {
        List<Entity> entityList = board.getEntitiesOnVector(vector2d);
        return entityList != null && !entityList.isEmpty();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.collisionWithTeleport(this);
    }

    @Override
    public void collision(Entity entity) {
        entity.accept(this.powerUpVisitor);
    }
}
